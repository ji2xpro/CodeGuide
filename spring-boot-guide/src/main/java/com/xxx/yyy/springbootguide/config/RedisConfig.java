package com.xxx.yyy.springbootguide.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;

import static java.util.Collections.singletonMap;

/**
 * redis配置类
 *
 * @author: maoyan
 * @date: 2020/2/13 14:16:32
 * @description:
 */
@Slf4j
@Configuration
@EnableCaching //redis启用开关
//@PropertySource("classpath:redis.properties")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate redisCacheTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate template = new RedisTemplate<>();

        // 配置连接工厂
        template.setConnectionFactory(lettuceConnectionFactory);

        /**
         * 任选其一
         */
        // 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = jackson2JsonRedisSerializer();

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = genericJackson2JsonRedisSerializer();

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);

        // 设置 HashKey 和 HashValue 序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 开启事务
        template.setEnableTransactionSupport(true);

        template.afterPropertiesSet();

        return template;
    }

    /**************** 注解缓存失效时间配置 *******************/
    /**
     * springboot2.x 设置redis缓存失效时间(注解)：
     *      @CacheConfig(cacheNames = "SsoCache")
     *      @Cacheable(value = "BasicDataCache",keyGenerator = "wiselyKeyGenerator")
     * @param redisConnectionFactory
     */
    @Bean
    CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        /**
         * 任选其一
         */
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = jackson2JsonRedisSerializer();

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = genericJackson2JsonRedisSerializer();

        // 配置序列化（解决乱码的问题)
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存的默认超时时间：30 minutes
                .entryTtl(Duration.ofMinutes(30L))
                // 禁用缓存前缀
                .disableKeyPrefix()
                // key序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 值序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer))
                // 如果是空值，不缓存
                .disableCachingNullValues();

        // 通过连接工厂构建缓存管理器
        /* 配置test的超时时间为120s*/
        RedisCacheManager cacheManager = RedisCacheManager
                .builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory))
                // 注入配置
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(singletonMap("test", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(120L)).disableCachingNullValues()))
                .transactionAware()
                .build();

        return cacheManager;
    }


    @Override
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                // 采取拼接的方式生成key
                StringBuilder sb = new StringBuilder();
                // 目标类的类名
                sb.append(target.getClass().getName());
                sb.append(":");
                // 目标方法名
                sb.append(method.getName());
                // 参数
//                for (Object obj : params) {
//                    if (obj != null){
//                        if(!BaseUtil.isBaseType(obj)) {
//                            try {
//                                Field id = obj.getClass().getDeclaredField("id");
//                                id.setAccessible(true);
//                                sb.append(id.get(obj));
//                            } catch (NoSuchFieldException | IllegalAccessException e) {
//                                logger.error(e.getMessage());
//                            }
//                        } else{
//                            sb.append(obj);
//                        }
//                    }
//                }
                for(Object object : params){
                    sb.append(":"+String.valueOf(object));
                }

                log.info("redis cache key str: " + sb.toString());
                log.info("redis cache key sha256Hex: " + DigestUtils.sha1DigestAsHex(sb.toString()));
                return DigestUtils.sha1DigestAsHex(sb.toString());
            }
        };
    }

    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JdkSerializationRedisSerializer的序列化方式）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(MyObjectMapper());

        return jackson2JsonRedisSerializer;
    }

    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(MyObjectMapper());

        return genericJackson2JsonRedisSerializer;
    }

    private ObjectMapper MyObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        /**设置存储到redis中的日期格式*/
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

        return objectMapper;
    }
}