package com.dafy.octopus.worker.order.util;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 用于生成唯一工单号
 * Created by liaoxudong
 * Date:2018/6/4
 */

public class BillNoUtils {

    /**
     * 生成系统唯一订单号
     * @param prefix 前缀固定值
     * @param nums 数值位数
     * @return
     */
    public synchronized static String generateBillNo(String prefix,int nums){
        String today = DateTimeUtils.formatDatetime4(new Date());
        long incrBy = CacheFactory.incrBy(today, 1L);
        return prefix + today + StringUtils.leftPad(incrBy + "", nums, "0");
    }
}
