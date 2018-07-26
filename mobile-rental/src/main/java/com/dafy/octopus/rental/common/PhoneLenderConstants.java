package com.dafy.octopus.rental.common;

/**
 * 常量接口
 */
public interface PhoneLenderConstants {
    //获取首页的用户列表
    public static final String GET_USER_LIST_URL = "/wuzhu/customerSer/getCustomerSerList";
    public static final String GET_USER_DETAIL_INFO_URL = "/wuzhu/customerSer/getSerCustomerDetailInfo";
    public static final String GET_USER_BRIEF_INFO_URL = "/wuzhu/customerSer/getSerCustomerInfo";
    public static final String QUERY_USER_LIST_INFO_URL = "/wuzhu/customerSer/getSerCustomerSelect";
    //获取订单信息
    public static final String GET_ORDER_NO_LIST_URL = "/wuzhu/customerSer/getOrderList";
    public static final String GET_ORDER_DETAIL_URL = "/wuzhu/customerSer/getOrderDetail";
    public static final String GET_ORDER_FEE_DETAIL_URL = "/wuzhu/customerSer/getFeeInfo";
    public static final String GET_ORDER_EXPRESS_URL = "/wuzhu/customerSer/getRoutInfo";
    public static final String GET_CANCEL_ORDER_URL = "/wuzhu/customerSer/cancelOrder";
    String ORDER_DELAY_PATH = "/wuzhu/customerSer/delay";

}
