package com.dafy.octopus.worker.order.constants;

/**
 * Created by liaoxudong
 * Date:2018/6/4
 */

public class Constants {
    // 工单号前缀
    public static final String BILL_NO_PREFIX = "DAFY";

    // 工单号后面的递增位数
    public static final int BILL_NO_NUMS = 5;

    // 工单审核
    public static final int BILL_APPROVAL = 1;
    // 工单审核暂存
    public static final int BILL_APPROVAL_TEMPSAVE = 3;

    // 工单处理
    public static final int BILL_DEAL = 2;
    // 工单处理暂存
    public static final int BILL_DEAL_TEMPSAVE = 4;
}
