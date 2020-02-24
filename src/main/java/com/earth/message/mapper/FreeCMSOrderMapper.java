package com.earth.message.mapper;

import com.earth.message.model.dao.FreeCMSOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FreeCMSOrderMapper {
    List<FreeCMSOrder> selectById(@Param("id") String id);
}
