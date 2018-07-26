package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BillOpLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillOpLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillOpLog record);

    int insertSelective(BillOpLog record);

    BillOpLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillOpLog record);

    int updateByPrimaryKey(BillOpLog record);

    /**
     * 获取具体工单的操作日志 按时间顺序
     * @param id
     * @return
     */
    List<BillOpLog> selectByBillId(@Param("billId") Long id);
}