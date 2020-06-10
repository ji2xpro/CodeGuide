package com.xxx.yyy.springbootguide.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout扇形交换机配置
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
public class FanoutRabbitConfig {

    /**
     * 创建三个队列: fanout.A, fanout.B, fanout.C
     * 将三个队列都绑定在交换机 fanoutExchange 上
     * 因为是扇型交换机, 路由键无需配置,配置也不起作用
     */

    public final static String FANOUT_A = "fanout.A";
    public final static String FANOUT_B = "fanout.B";
    public final static String FANOUT_C = "fanout.C";
    public final static String FANOUT_EXCHANGE = "fanout_exchange";

    /**
     * 创建第一个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueA() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(FANOUT_A);
    }

    /**
     * 创建第二个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueB() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(FANOUT_B);
    }

    /**
     * 创建第三个消费队列
     *
     * @return
     */
    @Bean
    public Queue queueC() {
        /**
         * name: 队列的名称
         * durable: 是否支持持久化, 默认是false, 持久化队列: 会被存储在磁盘上, 当消息代理重启时仍然存在, 暂存队列: 当前连接有效
         * exclusive: 默认也是false, 只能被当前创建的连接使用, 而且当连接关闭后队列即被删除. 此参考优先级高于durable
         * autoDelete: 是否自动删除, 当没有生产者或者消费者使用此队列, 该队列会自动删除
         * return new Queue("DirectQueue",true,false,false);
         * 一般设置队列的持久化就好, 其余两个就是默认false
         */
        return new Queue(FANOUT_C);
    }

    /**
     * 创建一个Topic交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        /**
         * 共有三种构造方法
         * 1. 只传exchange的名字.
         * 2. 传exchange名字，是否支持持久化，是否可以自动删除.
         * 3. 第2种参数上增加Map，Map中可以存放自定义exchange中的参数
         */
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 将 queueA() 消费队列和Fanout交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    /**
     * 将 queueB() 消费队列和Topic交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    /**
     * 将 queueC() 消费队列和Fanout交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }
}

