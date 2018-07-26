package com.dafy.octopus.rental.service.reportForm;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface ReportFormService {
    /**
     *获取还款列表 （“还款到期提醒”报表）
     */

    JSONObject queryRepaymentList(Date startDate, Date endDate);

    /**
     * 导出还款账单列表 （“还款到期提醒”报表）
     * @param startDate
     * @param endDate
     */
    void exportRepaymentList(Date startDate, Date endDate);

    /**
     * 获取用户信息、订单信息列表
     * @param startDate
     * @param endDate
     * @return
     */
    JSONObject queryUserOrderInfoList(Date startDate, Date endDate);

    /**
     * 导出用户信息、订单信息列表
     * @param startDate
     * @param endDate
     * @return
     */
    JSONObject exportUserOrderInfoList(Date startDate, Date endDate);
}
