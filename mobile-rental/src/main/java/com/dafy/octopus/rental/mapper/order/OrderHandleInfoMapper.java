package com.dafy.octopus.rental.mapper.order;

import com.dafy.octopus.rental.dto.VO.order.OrderHandleInfoVO;
import com.dafy.octopus.rental.mapper.IBaseMapper;
import com.dafy.octopus.rental.dto.DO.order.OrderHandleInfoDO;

import java.util.List;
import java.util.Map;

public interface OrderHandleInfoMapper extends IBaseMapper<OrderHandleInfoDO, OrderHandleInfoVO, Long> {
    List<OrderHandleInfoVO> query(Map<String,Object> params);
}
