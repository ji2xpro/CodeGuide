package com.xxx.yyy.springbootguide.controller;

import com.xxx.yyy.springbootguide.config.rabbitmq.DirectRabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.FanoutRabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.HeadersRabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.RabbitConfig;
import com.xxx.yyy.springbootguide.config.rabbitmq.TopicRabbitConfig;
import com.xxx.yyy.springbootguide.handler.RabbitReceiver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: maoyan
 * @date: 2020/4/27 14:01:35
 * @description:
 */
@Log4j2
@RestController
@RequestMapping(value = "/rabbit")
@Api(tags = "RabbitController", description = "rabbitmq请求模拟", value = "rabbitmq")
public class RabbitController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用Direct交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendDirectMessage")
    @ApiOperation(value = "使用Direct交换机发送信息")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        // 将消息携带绑定键值：RabbitConfig.ROUTINGKEY 发送到交换机 RabbitConfig.EXCHANGE
        rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_ROUTING_KEY, map);
        return "ok";
    }

    /**
     * 使用Topic交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendTopicMessage1")
    @ApiOperation(value = "使用Topic交换机发送信息")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message: ONE ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, TopicRabbitConfig.TOPIC_ONE, map);
        return "ok";
    }

    /**
     * 使用Topic交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendTopicMessage2")
    @ApiOperation(value = "使用Topic交换机发送信息")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message: TWO is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, TopicRabbitConfig.TOPIC_TWO, map);
        return "ok";
    }

    /**
     * 使用Fanout交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendFanoutMessage")
    @ApiOperation(value = "使用Fanout交换机发送信息")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE, null, map);
        return "ok";
    }

    /**
     * 使用Headers交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendHeadersMessage1")
    @ApiOperation(value = "使用Headers交换机发送信息")
    public String sendHeadersMessage1() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        Message message = getMessage(head,"测试(部分匹配)");
        rabbitTemplate.convertAndSend(HeadersRabbitConfig.HEADERS_EXCHANGE_ONE, HeadersRabbitConfig.HEADERS_ONE, message);
        return "ok";
    }

    /**
     * 使用Headers交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendHeadersMessage2")
    @ApiOperation(value = "使用Headers交换机发送信息")
    public String sendHeadersMessage2() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        Message message = getMessage(head,"测试(全部匹配)");
        rabbitTemplate.convertAndSend(HeadersRabbitConfig.HEADERS_EXCHANGE_ONE, HeadersRabbitConfig.HEADERS_ONE, message);
        return "ok";
    }

    /**
     * 使用Headers交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendHeadersMessage3")
    @ApiOperation(value = "使用Headers交换机发送信息")
    public String sendHeadersMessage3() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        Message message = getMessage(head,"测试(部分匹配)");
        rabbitTemplate.convertAndSend(HeadersRabbitConfig.HEADERS_EXCHANGE_TWO, HeadersRabbitConfig.HEADERS_TWO, message);
        return "ok";
    }

    /**
     * 使用Headers交换机发送信息
     *
     * @return
     */
    @GetMapping("/sendHeadersMessage4")
    @ApiOperation(value = "使用Headers交换机发送信息")
    public String sendHeadersMessage4() {
        Map<String,Object> head = new HashMap<>();
        head.put("type", "cash");
        head.put("aging", "fast");
        Message message = getMessage(head,"测试(全部匹配)");
        rabbitTemplate.convertAndSend(HeadersRabbitConfig.HEADERS_EXCHANGE_TWO, HeadersRabbitConfig.HEADERS_TWO, message);
        return "ok";
    }


    private Message getMessage(Map<String, Object> head, Object msg){
        MessageProperties messageProperties = new MessageProperties();
        for (Map.Entry<String, Object> entry : head.entrySet()) {
            messageProperties.setHeader(entry.getKey(), entry.getValue());
        }
        MessageConverter messageConverter = new SimpleMessageConverter();
        return messageConverter.toMessage(msg, messageProperties);
    }


    /**
     * this.rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, map); 对应 {@link RabbitReceiver#listenerDelayQueue}
     */
    @GetMapping
    @ApiOperation(value = "使用延迟队列发送信息")
    public void defaultMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        // 添加延时队列
        this.rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, map, message -> {
            // TODO 第一句是可要可不要,根据自己需要自行处理
//            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Book.class.getName());
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(5 * 1000 + "");
            return message;
        });
        log.info("[发送时间] - [{}]", LocalDateTime.now());
    }
}