package com.xxx.yyy.springbootguide.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic主题交换机配置
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
public class TopicRabbitConfig {

    /**
     * 主题交换机，这个交换机其实跟直连交换机流程差不多，但是它的特点就是在它的路由键和绑定键之间是有规则的。
     * 简单地介绍下规则：
     * "*" (星号) 用来表示一个单词 (必须出现的)
     * "#" (井号) 用来表示任意数量（零个或多个）单词
     * 通配的绑定键是跟队列进行绑定的，举个小例子
     * 队列 Q1 绑定键为 *.TT.* ; 队列Q2绑定键为 TT.# ;
     * 如果一条消息携带的路由键为 A.TT.B, 那么队列Q1将会收到；
     * 如果一条消息携带的路由键为TT.AA.BB, 那么队列Q2将会收到；
     * <p>
     * 主题交换机是非常强大的，为啥这么膨胀？
     * 当一个队列的绑定键为 "#" (井号) 的时候，这个队列将会无视消息的路由键，接收所有的消息。
     * 当 "*" (星号) 和 "#" (井号) 这两个特殊字符都未在绑定键中出现的时候，此时主题交换机就拥有的直连交换机的行为。
     */

    public final static String TOPIC_ONE = "topic.one";
    public final static String TOPIC_TWO = "topic.two";
    public final static String TOPIC_EXCHANGE = "topic_exchange";
    public final static String TOPIC_KEY = "topic.#";

    /**
     * 创建第一个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueOne() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(TOPIC_ONE);
    }

    /**
     * 创建第二个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueTwo() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(TOPIC_TWO);
    }

    /**
     * 创建一个Topic交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        /**
         * 共有三种构造方法
         * 1. 只传exchange的名字.
         * 2. 传exchange名字，是否支持持久化，是否可以自动删除.
         * 3. 第2种参数上增加Map，Map中可以存放自定义exchange中的参数
         */
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 将 queueOne() 消费队列和Topic交换机绑定, 并设置匹配键为 TOPIC_ONE, 这样只要是消息携带的路由键是 TOPIC_ONE 才会分发到该队列
     *
     * @return
     */
    @Bean
    public Binding bindingOne() {
        return BindingBuilder.bind(queueOne()).to(topicExchange()).with(TOPIC_ONE);
    }

    /**
     * 将 queueTwo() 消费队列和Topic交换机绑定, 并设置匹配键为用上通配路由键规则 topic.#, 这样只要是消息携带的路由键是 topic.# 才会分发到该队列
     *
     * @return
     */
    @Bean
    public Binding bindingTwo() {
        return BindingBuilder.bind(queueTwo()).to(topicExchange()).with(TOPIC_KEY);
    }
}

