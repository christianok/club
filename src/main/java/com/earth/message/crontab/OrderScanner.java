package com.earth.message.crontab;

import com.earth.message.constant.MessageTopic;
import com.earth.message.model.dao.FreeCMSOrder;
import com.earth.message.service.MessageProviderService;
import com.earth.message.service.OrderService;
import com.earth.message.model.entity.OrderMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class OrderScanner {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageProviderService messageProviderService;

    //@Scheduled(cron = "0/5 * * * * *")
    public void scanOrder() {
        List<FreeCMSOrder> rawOrders = orderService.freeCMSOrderList();
        for (FreeCMSOrder order : rawOrders) {
            String messageId = String.valueOf(UUID.randomUUID());
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String, Object> manMap = new HashMap<>();
            manMap.put("messageId", messageId);
            manMap.put("createTime", createTime);
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setConsignee(order.getConsignee());
            orderMessage.setDataId(order.getDataId());
            orderMessage.setOrderAnnex(order.getOrderAnnex());
            orderMessage.setOrderCheck(order.getOrderCheck());
            ObjectMapper mapper = new ObjectMapper();
            try {
                String message = mapper.writeValueAsString(orderMessage);
                manMap.put("messageData", message);
                this.messageProviderService.sendTopic(MessageTopic.ORDER, manMap);
            } catch (JsonProcessingException e) {
                log.error("message error: {}", e);
            }
        }

    }
}
