package com.dafy.octopus.worker.order.mapper;

import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.worker.order.dto.Bill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bill record);

    Long insertSelective(Bill record);

    Bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    List<Bill> list(Page<Bill> page);

    @Update("update T_BILL_INFO set DEAL_USER=CREATE_USER where ID=#{billId}")
    void backBillById(@Param("billId") Long id);

    @Update("update T_BILL_INFO set BILL_STATUS=#{bill.billStatusId},DEAL_USER='0',DEAL_ORG=#{bill.dealOrgId} where id=#{bill.id}")
    void updateBillStatus(@Param("bill") Bill bill);
    @Update("update T_BILL_INFO set PRESS_TIMES=#{pressTimes} where ID=#{billId}")
    void updatePreeTimes(@Param("billId") long billId, @Param("pressTimes") int l);

    @Select("select count(1) from T_BILL_INFO b where b.BILL_STATUS in (2,4) and b.DEAL_USER=#{request.userId}")// and b.CREATE_ORG=#{request.createOrgId}
    int selectUpcomingCount(@Param("request") Request request);

    /**
     * 获取工单详情
     * @param id
     * @return
     */
    Bill findDetailById(@Param("billId") Long id);

    @Update("update T_BILL_INFO set ATTACH_URL = #{url} where id=#{id}")
    void updateAttachUrlById(@Param("id") Long id, @Param("url") String newAttachUrl);
}