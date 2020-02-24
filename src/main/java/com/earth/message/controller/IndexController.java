package com.earth.message.controller;

import com.earth.message.constant.MessageTopic;
import com.earth.message.model.entity.OrderMessage;
import com.earth.message.model.entity.Student;
import com.earth.message.rpc.ConsumerFeignService;
import com.earth.message.service.ElasticSearchService;
import com.earth.message.service.MessageProviderService;
import com.earth.message.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class IndexController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private MessageProviderService messageProviderService;

    @Autowired
    private TransportClient client;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConsumerFeignService consumerFeignService;



    @RequestMapping("/template-search")
    public List<Student> findDoc() {
        return elasticSearchService.findDoc();
    }

    @RequestMapping("/client-search")
    public GetResponse find() {
        // 查询一条
        GetResponse result = client.prepareGet("student", "student", "null").get();
        return result;
    }

    @RequestMapping(value = "/match", params = {"value", "age"})
    public List<Student> save(String value, Integer age) {
        com.earth.message.model.dao.Student student = new com.earth.message.model.dao.Student();
        student.setAge(age);
        student.setName(value);
        student.setNumber("2009051448");
        student.setClazzId("shz19910108");
        orderService.insertStu(student);
        return elasticSearchService.singleMatch(value);
    }

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String order = consumerFeignService.getOrder();
        log.debug("feign order {}",order);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("createTime", createTime);
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setConsignee("test");
        orderMessage.setDataId("1000");
        orderMessage.setOrderAnnex("dfawefawe");
        orderMessage.setOrderCheck("yes");
        ObjectMapper mapper=new ObjectMapper();
        try {
            String message=mapper.writeValueAsString(orderMessage);
            manMap.put("messageData", message);
            rabbitTemplate.convertAndSend("topicExchange", MessageTopic.NOTICE, manMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", MessageTopic.FEEDBACK, womanMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }
}
