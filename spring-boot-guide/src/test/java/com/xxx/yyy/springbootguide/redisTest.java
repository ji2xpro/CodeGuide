package com.xxx.yyy.springbootguide;

import com.xxx.yyy.springbootguide.model.User;
import com.xxx.yyy.springbootguide.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: maoyan
 * @date: 2020/2/14 15:42:38
 * @description:
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class redisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setRedisUtil() {
        User user = new User();
        user.setUsername("hello");
        user.setPassword("word");
        user.setAge(12);

        String key = "user";
        redisUtil.set(key,user);
        log.info("[字符缓存结果] - [{}]", key);
        // TODO 对应 String（字符串）
        final User user1 = (User) redisUtil.get(key);
        log.info("[对象缓存结果] - [{}]", user1);
    }
}
