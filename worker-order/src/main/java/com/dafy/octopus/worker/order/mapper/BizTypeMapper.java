package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BizType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BizTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BizType record);

    int insertSelective(BizType record);

    BizType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BizType record);

    int updateByPrimaryKey(BizType record);

    List<BizType> selectByBillType(@Param("billTypeId") Long billTypeId,@Param("all") Boolean all);
    @Update("update T_BIZ_TYPE set `ORDER`=-1 where id=#{id}")
    void updateOrderToZero(@Param("id") Long id);

    @Update("update T_BIZ_TYPE set `ORDER`=#{oldOrder} where BILL_TYPE_ID=#{billTypeId} and `ORDER`=#{order}")
    void updateOrderExists(@Param("billTypeId") Long billTypeId,@Param("oldOrder") String oldOrder, @Param("order") String order);
}