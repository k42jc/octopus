package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BillRemark;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRemarkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillRemark record);

    int insertSelective(BillRemark record);

    BillRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillRemark record);

    int updateByPrimaryKey(BillRemark record);

    List<BillRemark> selectByBillId(@Param("billId") Long billId);
}