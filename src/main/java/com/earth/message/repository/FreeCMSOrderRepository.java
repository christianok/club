package com.earth.message.repository;

import com.earth.message.model.dao.FreeCMSOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeCMSOrderRepository extends JpaRepository<FreeCMSOrder, String> {

}
