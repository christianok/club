package com.earth.message.model.entity;

import lombok.Data;

@Data
public class OrderMessage {
    private Integer id;
    private String dataId;
    private String consignee;
    private String orderAnnex;
    private String orderCheck;

}
