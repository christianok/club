package com.earth.message.config;

import com.earth.message.constant.MessageTopic;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue noticeQueue() {

        return new Queue(MessageTopic.NOTICE);
    }

    @Bean
    public Queue feedbackQueue() {
        return new Queue(MessageTopic.FEEDBACK);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(MessageTopic.ORDER);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(MessageTopic.RESULT);
    }

    @Bean
    public Queue internalMessageQueue() {
        return new Queue(MessageTopic.INTERNAL_MESSAGE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessageNotice() {
        return BindingBuilder.bind(noticeQueue()).to(exchange()).with(MessageTopic.NOTICE);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessageFeedback() {
        return BindingBuilder.bind(feedbackQueue()).to(exchange()).with(MessageTopic.FEEDBACK);
    }

    @Bean
    Binding bindingExchangeMessageOrder() {
        return BindingBuilder.bind(orderQueue()).to(exchange()).with(MessageTopic.ORDER);
    }

    @Bean
    Binding bindingExchangeMessageResult() {
        return BindingBuilder.bind(orderQueue()).to(exchange()).with(MessageTopic.RESULT);
    }

    @Bean
    Binding bindingExchangeMessageInternalMessage() {
        return BindingBuilder.bind(orderQueue()).to(exchange()).with(MessageTopic.INTERNAL_MESSAGE);
    }
}
