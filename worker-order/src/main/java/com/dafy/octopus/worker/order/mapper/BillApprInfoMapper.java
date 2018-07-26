package com.dafy.octopus.worker.order.mapper;

import com.dafy.octopus.worker.order.dto.BillApprInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface BillApprInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillApprInfo record);

    int insertSelective(BillApprInfo record);

    BillApprInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillApprInfo record);

    int updateByPrimaryKey(BillApprInfo record);

    /**
     * 获取暂存信息
     * @param billId
     * @return
     */
//    @Select("select * from T_BILL_APPROVAL_INFO where bill_id=#{billId} and `status`='1'")
    BillApprInfo selectReviewingByBillId(@Param("billId") long billId);

    /**
     * 删除原来存在的审核信息(暂存的信息)
     * @param billId
     */
    @Delete("delete from T_BILL_APPROVAL_INFO where bill_id=#{billId}")
    void deleteByBillId(@Param("billId") Long billId);

    @Update("update T_BILL_APPROVAL_INFO set ATTACH_URL = #{url} where id=#{id}")
    void updateAttachUrlById(@Param("id") Long id, @Param("url") String newAttachUrl);
}