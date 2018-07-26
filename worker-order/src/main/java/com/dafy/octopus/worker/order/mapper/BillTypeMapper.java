package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BillType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillType record);

    int insertSelective(BillType record);

    BillType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillType record);

    int updateByPrimaryKey(BillType record);

    /**
     * 查询所有
     * @param all 是否过滤不显示项
     * @return
     */
    List<BillType> findAll(@Param("all") Boolean all);

    @Update("update T_BILL_TYPE set `ORDER`=-1 where id=#{id}")
    void updateOrderToZero(@Param("id") Long id);

    @Update("update T_BILL_TYPE set `ORDER`=#{oldOrder} where `ORDER`=#{order}")
    void updateOrderExists(@Param("oldOrder") String oldOrder, @Param("order") String order);
}