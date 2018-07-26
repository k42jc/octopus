package com.dafy.octopus.rental.service.order;

import com.alibaba.fastjson.JSONObject;
import com.dafy.octopus.rental.dto.DO.order.OrderHandleInfoDO;
import com.dafy.octopus.web.core.domain.Request;

public interface OrderInfoService {

    /**
     * 获取订单详情
     * @return
     */
    JSONObject getOrderDetailInfo(String orderNo) throws Exception;

    /**
     * 获取客户的订单号列表
     * @param userId
     * @return
     */
    JSONObject getOrderNoList(String userId) throws Exception;

    /**
     * 获取订单费用信息
     * @param orderNo
     * @param feeNo
     * @return
     */
    JSONObject getOrderFeeInfo(String orderNo,String feeNo) throws Exception;

    /**
     * 获取订单物流/快递信息
     * @param deliveryOrderNo
     * @return
     */
    JSONObject getOrderExpressInfo(String deliveryOrderNo) throws Exception;

    /**
     * 取消订单接口
     * @param orderHandleInfo
     * @return
     */
    JSONObject cancelOrder(OrderHandleInfoDO orderHandleInfo) throws Exception;

    /**
     * 订单延期接口
     * @param request
     * @return
     */
    JSONObject delayOrder(Request request) throws Exception;
}
