package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BizDetailType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BizDetailTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BizDetailType record);

    int insertSelective(BizDetailType record);

    BizDetailType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BizDetailType record);

    int updateByPrimaryKey(BizDetailType record);

    List<BizDetailType> selectByBizType(@Param("bizTypeId") Long bizTypeId,@Param("all") Boolean all);

    @Update("update T_BIZ_DETAIL_TYPE set `ORDER`=-1 where id=#{id}")
    void updateOrderToZero(@Param("id") Long id);

    @Update("update T_BIZ_DETAIL_TYPE set `ORDER`=#{oldOrder} where BIZ_TYPE_ID=#{bizTypeId} and `ORDER`=#{order}")
    void updateOrderExists(@Param("bizTypeId") Long bizTypeId,@Param("oldOrder") String oldOrder, @Param("order") String order);
}