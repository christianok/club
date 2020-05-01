package com.earth.message.service;

import com.earth.message.mapper.FreeCMSOrderMapper;
import com.earth.message.model.dao.FreeCMSOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyBatisService {
    static class TestQueue{
        private Integer initSize;
    }

    @Autowired
    private FreeCMSOrderMapper freeCMSOrderMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public List<FreeCMSOrder> selectOrder(String id) {

        return freeCMSOrderMapper.selectById(id);
    }
}
