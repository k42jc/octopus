package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BillDealInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDealInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillDealInfo record);

    int insertSelective(BillDealInfo record);

    BillDealInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillDealInfo record);

    int updateByPrimaryKey(BillDealInfo record);

    /**
     * 获取暂存的工单处理信息
     * @param billId
     * @return
     */
//    @Select("select * from T_BILL_DEAL_INFO where bill_id=#{billId} and `status`='1'")
    BillDealInfo selectHandleingByBillId(@Param("billId") long billId);

    /**
     * 用于删除暂存工单
     * @param billId
     */
    @Delete("delete from T_BILL_DEAL_INFO where bill_id=#{billId}")
    void deleteByBillId(@Param("billId") Long billId);

    @Update("update T_BILL_DEAL_INFO set ATTACH_URL = #{url} where id=#{id}")
    void updateAttachUrlById(@Param("id") Long id, @Param("url") String newAttachUrl);
}