package com.earth.message.model.entity;

import lombok.Data;

@Data
public class Message {
    private String messageId;
    private String messageDate;
    private String messageType;
    private String messageData;
}
