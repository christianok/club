package com.earth.message.mapper;

import com.earth.message.model.dao.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface StudentMapper {
    Integer insert(Student student);
    Integer updateIgnoreNullById(@Param("student") Student student);
    Student selectById(@Param("id") Integer id);
    Integer deleteById(@Param("id") Integer id);
}