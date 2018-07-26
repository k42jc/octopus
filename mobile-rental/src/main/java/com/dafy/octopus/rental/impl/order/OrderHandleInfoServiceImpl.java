package com.dafy.octopus.rental.impl.order;

import com.dafy.common.util.StringUtils;
import com.dafy.octopus.rental.impl.BaseService;
import com.dafy.octopus.rental.dto.DO.order.OrderHandleInfoDO;
import com.dafy.octopus.rental.dto.VO.order.OrderHandleInfoVO;
import com.dafy.octopus.rental.mapper.order.OrderHandleInfoMapper;
import com.dafy.octopus.rental.service.order.OrderHandleInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderHandleInfoServiceImpl extends BaseService implements OrderHandleInfoService {
    @Resource
    private OrderHandleInfoMapper orderHandleInfoMapper;
    /**
     * 添加订单/客户修改信息
     *
     * @param orderHandleInfo
     */
    @Override
    public Long addOrderHandleInfo(OrderHandleInfoDO orderHandleInfo) {
        add(orderHandleInfoMapper, orderHandleInfo);

        return orderHandleInfo.getId();
    }

    /**
     * @param userId
     * @param key       姓名/手机号
     * @param startDate
     * @param endDate
     */
    @Override
    public List<OrderHandleInfoVO> queryOrderHandleInfoList(String userId, String key, Date startDate, Date endDate) {
        Map<String, Object> params = new HashMap<>(4);
        if (!StringUtils.isEmpty(key)) params.put("key", key);
        if (!StringUtils.isEmpty(userId)) params.put("userId", userId);
        if (startDate != null) params.put("startDate", startDate);
        if (endDate != null) params.put("endDate", endDate);

        return orderHandleInfoMapper.query(params);
    }
}
