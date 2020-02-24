package com.earth.message.consumer;

import com.earth.message.constant.MessageTopic;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = MessageTopic.FEEDBACK)
public class TopicFeedbackConsumer {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TopicFeedbackConsumer消费者收到消息  : " + testMessage.toString());
    }
}