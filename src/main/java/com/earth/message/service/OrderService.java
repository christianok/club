package com.earth.message.service;

import com.earth.message.mapper.FreeCMSOrderMapper;
import com.earth.message.mapper.StudentMapper;
import com.earth.message.model.dao.FreeCMSOrder;
import com.earth.message.model.dao.Student;
import com.earth.message.model.entity.OrderMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class OrderService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private FreeCMSOrderMapper freeCMSOrderMapper;


    public void deleteStuById(int stuId) {
        int i = studentMapper.deleteById(stuId);
    }

    public void insertStu(Student student) {
        Integer insertId = studentMapper.insert(student);
    }

    public Student selectStu(int id) {
        return studentMapper.selectById(id);
    }

    public List<FreeCMSOrder> freeCMSOrderList() {
        List<FreeCMSOrder> freeCMSOrders = this.freeCMSOrderMapper.selectById("0000000056361e460156361e46250000");
        return freeCMSOrders;
    }
}
