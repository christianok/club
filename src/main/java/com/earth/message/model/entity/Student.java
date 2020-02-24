package com.earth.message.model.entity;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "student", type = "student", shards = 1, replicas = 1, refreshInterval = "-1")
public class Student {
    @Id
    private Integer id;
    private String name;
    private String clazzId;
    private Integer age;
    private String number;
    private DateTime updateTime;
}
