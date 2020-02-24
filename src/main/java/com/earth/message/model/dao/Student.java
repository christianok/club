package com.earth.message.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Student {
    @Id
    private Integer id;
    private String name;
    private String clazzId;
    private Integer age;
    private String number;
}
