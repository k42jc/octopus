package com.dafy.octopus.rental.impl.reportForm;

import com.alibaba.fastjson.JSONObject;
import com.dafy.octopus.rental.service.reportForm.ReportFormService;

import java.util.Date;

public class ReportFormServiceImpl implements ReportFormService {
    /**
     * 获取还款列表 （“还款到期提醒”报表）
     *
     * @param startDate
     * @param endDate
     */
    @Override
    public JSONObject queryRepaymentList(Date startDate, Date endDate) {
        return null;
    }

    /**
     * 导出还款账单列表 （“还款到期提醒”报表）
     *
     * @param startDate
     * @param endDate
     */
    @Override
    public void exportRepaymentList(Date startDate, Date endDate) {

    }

    /**
     * 获取用户信息、订单信息列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public JSONObject queryUserOrderInfoList(Date startDate, Date endDate) {
        return null;
    }

    /**
     * 导出用户信息、订单信息列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public JSONObject exportUserOrderInfoList(Date startDate, Date endDate) {
        return null;
    }
}
