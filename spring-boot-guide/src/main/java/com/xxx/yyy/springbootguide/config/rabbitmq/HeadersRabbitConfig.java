package com.xxx.yyy.springbootguide.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Headers主题交换机配置
 *
 * @author: maoyan
 * @date: 2020/4/27 16:20:58
 * @description: Broker: 它提供一种传输服务, 它的角色就是维护一条从生产者到消费者的路线, 保证数据能按照指定的方式进行传输.
 * Exchange: 消息交换机, 它指定消息按什么规则, 路由到哪个队列.
 * Queue: 消息的载体, 每个消息都会被投到一个或多个队列.
 * Binding: 绑定, 它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key: 路由关键字, exchange根据这个关键字进行消息投递.
 * vhost: 虚拟主机, 一个broker里可以有多个vhost, 用作不同用户的权限分离.
 * Producer: 消息生产者, 就是投递消息的程序.
 * Consumer: 消息消费者, 就是接受消息的程序.
 * Channel: 消息通道, 在客户端的每个连接里, 可建立多个channel.
 */
@Configuration
public class HeadersRabbitConfig {

    public final static String HEADERS_ONE = "headers.one";
    public final static String HEADERS_TWO = "headers.two";
    public final static String HEADERS_EXCHANGE_ONE = "headers_exchange_one";
    public final static String HEADERS_EXCHANGE_TWO = "headers_exchange_two";

    /**
     * 创建第一个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueFirst() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(HEADERS_ONE);
    }

    /**
     * 创建第二个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueSecond() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(HEADERS_TWO);
    }

    /**
     * 创建第一个Headers交换机
     *
     * @return
     */
    @Bean
    public HeadersExchange headersExchangeOne() {
        /**
         * 共有三种构造方法
         * 1. 只传exchange的名字.
         * 2. 传exchange名字，是否支持持久化，是否可以自动删除.
         * 3. 第2种参数上增加Map，Map中可以存放自定义exchange中的参数
         */
        return new HeadersExchange(HEADERS_EXCHANGE_ONE);
    }

    /**
     * 创建第二个Headers交换机
     *
     * @return
     */
    @Bean
    public HeadersExchange headersExchangeTwo() {
        /**
         * 共有三种构造方法
         * 1. 只传exchange的名字.
         * 2. 传exchange名字，是否支持持久化，是否可以自动删除.
         * 3. 第2种参数上增加Map，Map中可以存放自定义exchange中的参数
         */
        return new HeadersExchange(HEADERS_EXCHANGE_TWO);
    }

    /**
     * 将 queueOne() 消费队列和Headers交换机绑定, 并设置匹配键为 headerValues, 这样只要是消息携带的路由键是 headerValues 才会分发到该队列
     *
     * @return
     */
    @Bean
    public Binding bindingFirst() {
        Map<String,Object> headerValues = new HashMap<>();
        headerValues.put("type","cash");
        headerValues.put("aging","fast");
        return BindingBuilder.bind(queueFirst()).to(headersExchangeOne()).whereAll(headerValues).match();
    }

    /**
     * 将 queueTwo() 消费队列和Headers交换机绑定, 并设置匹配键为用上通配路由键规则 headerValues, 这样只要是消息携带的路由键是 headerValues 才会分发到该队列
     *
     * @return
     */
    @Bean
    public Binding bindingSecond() {
        Map<String,Object> headerValues = new HashMap<>();
        headerValues.put("type", "cash");
        headerValues.put("aging", "fast");
        return BindingBuilder.bind(queueSecond()).to(headersExchangeTwo()).whereAny(headerValues).match();
    }
}

