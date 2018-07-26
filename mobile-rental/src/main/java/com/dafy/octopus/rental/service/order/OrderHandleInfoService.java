package com.dafy.octopus.rental.service.order;

import com.dafy.octopus.rental.dto.VO.order.OrderHandleInfoVO;
import com.dafy.octopus.rental.dto.DO.order.OrderHandleInfoDO;

import java.util.Date;
import java.util.List;

public interface OrderHandleInfoService {
    /**
     * 添加订单/客户修改信息
     * @param orderHandleInfo
     */
    Long addOrderHandleInfo(OrderHandleInfoDO orderHandleInfo);

    /**
     *
     * @param key 姓名/手机号
     * @param startDate
     * @param endDate
     */
    List<OrderHandleInfoVO> queryOrderHandleInfoList(String userID, String key, Date startDate, Date endDate);
}
