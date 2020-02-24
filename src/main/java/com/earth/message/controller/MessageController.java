package com.earth.message.controller;

import com.earth.message.constant.MessageTopic;
import com.earth.message.model.entity.Message;
import com.earth.message.service.MessageProviderService;
import com.earth.message.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class MessageController {
    @Autowired
    private MessageProviderService messageProviderService;

    @PostMapping(value = "/message/entrance", consumes = "application/json", produces = "application/json")
    public ApiResult<String> entrance(@RequestBody Message message){
        log.info("message entrance: {}", message);
        Map<String, Object> messageBuilder = messageProviderService.messageBuilder(message);
        messageProviderService.sendTopic(MessageTopic.ORDER, messageBuilder);
        return ApiResult.ok("").build();
    }
}
