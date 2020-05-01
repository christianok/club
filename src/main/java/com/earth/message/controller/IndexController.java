package com.earth.message.controller;

import com.earth.message.constant.MessageTopic;
import com.earth.message.model.entity.OrderMessage;
import com.earth.message.model.entity.Student;
import com.earth.message.rpc.ConsumerFeignService;
import com.earth.message.service.*;
import com.earth.message.utils.ApiResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
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

    @Autowired
    private ZooKeeperService zooKeeperService;

    @Autowired
    private HttpServer httpServer;



    @RequestMapping(value = "/template-search", params = {"name", "number","age"})
    public List<Student> searchEntry(String name, String number, Integer age, @PageableDefault(sort = "age", direction = Sort.Direction.DESC) Pageable pageable) {
        return elasticSearchService.findDoc(name, number, age, pageable);
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

    @GetMapping(value = "/algo")
    public void test() {
        String str = "abcc";
        int i = httpServer.lengthOfLongestSubstring(str);
        log.info("algo result is {}", i);
    }

    @GetMapping(value = "/lock", params = {"name"})
    public void TestLock(String name) {
        this.zooKeeperService.distributedLock(name);
    }

    @PostMapping("/upload")
    public ApiResult upload(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String folder = "/Users/zhuoli/Downloads/test/";
        String[] fileArr = StringUtils.split(file.getOriginalFilename(), ".");
        if (fileArr != null) {
            String suffix = fileArr[1];
            String fileName = LocalDateTime.now().toString();
            String encodeFileName = MD5Encoder.encode(fileName.getBytes());
            String totalNmae = encodeFileName + "." + suffix;
            File localFile = new File(folder,  totalNmae);

            file.transferTo(localFile);
            return ApiResult.ok(totalNmae).build();
        }
        return null;
    }
}
