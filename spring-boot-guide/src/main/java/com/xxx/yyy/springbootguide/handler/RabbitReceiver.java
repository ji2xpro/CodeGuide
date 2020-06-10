package com.xxx.yyy.springbootguide.handler;

import com.rabbitmq.client.Channel;
import com.xxx.yyy.springbootguide.config.rabbitmq.DirectRabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.FanoutRabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.HeadersRabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.RabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.TopicRabbitConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Rabbit消费者
 *
 * @author: maoyan
 * @date: 2020/4/27 14:19:44
 * @description:
 */
@Component
@Log4j2
public class RabbitReceiver {

//    @RabbitHandler
//    @RabbitListener(queues = DirectRabbitConfig.DIRECT_QUEUE)
//    public void directProcess(Map map, Message message, Channel channel) {
//        log.info("DirectReceiver消费者收到消息  : " + map.toString());
//        try {
//            // TODO 通知 MQ 消息已被成功消费,可以ACK了
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
//        }
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = TopicRabbitConfig.TOPIC_ONE)
//    public void topicProcess1(Map message) {
//        System.out.println("TopicReceiver消费者 one 收到消息  : " + message.toString());
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = TopicRabbitConfig.TOPIC_TWO)
//    public void topicProcess2(Map message) {
//        System.out.println("TopicReceiver消费者 all 收到消息  : " + message.toString());
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = FanoutRabbitConfig.FANOUT_A)
//    public void fanoutProcess1(Map message) {
//        System.out.println("FanoutReceiver消费者 A 收到消息  : " + message.toString());
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = FanoutRabbitConfig.FANOUT_B)
//    public void fanoutProcess2(Map message) {
//        System.out.println("FanoutReceiver消费者 B 收到消息  : " + message.toString());
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = FanoutRabbitConfig.FANOUT_C)
//    public void fanoutProcess3(Map message) {
//        System.out.println("FanoutReceiver消费者 C 收到消息  : " + message.toString());
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = HeadersRabbitConfig.HEADERS_ONE)
//    public void headersProcess1(String message) {
//        System.out.println("HeadersReceiver消费者 one 收到消息  : " + message);
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = HeadersRabbitConfig.HEADERS_TWO)
//    public void headersProcess2(String message) {
//        System.out.println("HeadersReceiver消费者 two 收到消息  : " + message);
//    }
//
//    @RabbitHandler
//    @RabbitListener(queues = {RabbitConfig.REGISTER_QUEUE_NAME})
//    public void listenerDelayQueue(Map map, Message message, Channel channel) {
//        log.info("[listenerDelayQueue 监听的消息] - [消费时间] - [{}] - [{}]", LocalDateTime.now(), map.toString());
//        try {
//            // TODO 通知 MQ 消息已被成功消费,可以ACK了
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
//        }
//    }
}
