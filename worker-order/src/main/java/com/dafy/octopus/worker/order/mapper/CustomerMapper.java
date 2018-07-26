package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Long insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}