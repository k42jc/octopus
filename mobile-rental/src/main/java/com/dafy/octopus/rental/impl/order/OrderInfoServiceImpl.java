package com.dafy.octopus.rental.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dafy.common.util.DateTimeUtils;
import com.dafy.octopus.rental.common.PhoneLenderConstants;
import com.dafy.octopus.rental.common.PhoneLenderHttpUtil;
import com.dafy.octopus.rental.dto.DO.order.OrderHandleInfoDO;
import com.dafy.octopus.rental.service.order.OrderHandleInfoService;
import com.dafy.octopus.rental.service.order.OrderInfoService;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.utils.RequestUtils;
import com.dafy.octopus.web.core.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Value("${config.phone.lender.ase.key}")
    private String aseKey;
    @Value("${config.phone.lender.http.url}")
    private String httpUrl;

    @Autowired
    private OrderHandleInfoService orderHandleInfoService;

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public JSONObject getOrderDetailInfo(String orderNo) throws Exception {
        JSONObject data = new JSONObject();
        data.put("orderNo", orderNo);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_ORDER_DETAIL_URL);
        return response;
    }

    /**
     * 获取客户的订单号列表
     *
     * @param userId
     * @return
     */
    @Override
    public JSONObject getOrderNoList(String userId) throws Exception {
        JSONObject data = new JSONObject();
        data.put("customerId", userId);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_ORDER_NO_LIST_URL);
        return response;
    }

    /**
     * 获取订单费用信息
     *
     * @param orderNo
     * @param feeNo
     * @return
     */
    @Override
    public JSONObject getOrderFeeInfo(String orderNo, String feeNo) throws Exception {
        JSONObject data = new JSONObject();
        data.put("orderNo", orderNo);
        data.put("feeNo", feeNo);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_ORDER_FEE_DETAIL_URL);
        return response;
    }

    /**
     * 获取订单物流/快递信息
     *
     * @param deliveryOrderNo
     * @return
     */
    @Override
    public JSONObject getOrderExpressInfo(String deliveryOrderNo) throws Exception {
        JSONObject data = new JSONObject();
        data.put("deliveryOrderNo", deliveryOrderNo);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_ORDER_EXPRESS_URL);
        return response;
    }

    /**
     * 取消订单接口
     *
     * @param orderHandleInfo
     * @return
     */
    @Override
    @Transactional
    public JSONObject cancelOrder(OrderHandleInfoDO orderHandleInfo) throws Exception {
        JSONObject data = new JSONObject();
        data.put("orderNo", orderHandleInfo.getOrderId());
        data.put("reason", orderHandleInfo.getHandleReason());
        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.GET_CANCEL_ORDER_URL);
        if ("0000".equals(response.getString("code"))){
            orderHandleInfo.setHandleTypeName("取消订单");
            orderHandleInfoService.addOrderHandleInfo(orderHandleInfo);
        }

        return response;
    }

    @Override
    public JSONObject delayOrder(Request request) throws Exception {
        String orderNo = request.getString("orderNo");
        int type = request.getInt("type");
        int days = request.getInt("days");
        String remark = request.getString("remark");
        String userName = SubjectUtils.getCurrentUser().getUserName();
        String createTime = DateTimeUtils.formatDatetime1(new Date());

        Map<String,Object> data = new HashMap<>();
        data.put("createUser", userName);
        data.put("createTime", createTime);
        data.put("type", type);
        data.put("orderNo", orderNo);
        data.put("days", days);
        data.put("remark", remark);

        JSONObject response = PhoneLenderHttpUtil.doPost(JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue),
                PhoneLenderConstants.ORDER_DELAY_PATH);
        if ("0000".equals(response.getString("code"))){
            OrderHandleInfoDO orderHandleInfoDO = new OrderHandleInfoDO();
            orderHandleInfoDO.setOrderId(orderNo);
            ServletRequestAttributes requestContext = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            User currentUser = SubjectUtils.getCurrentUser();
            orderHandleInfoDO.setHandleIpAddress(RequestUtils.getIpAddress(requestContext.getRequest()));
            orderHandleInfoDO.setUserName(currentUser.getUserName());
            orderHandleInfoDO.setUserId(currentUser.getId()+"");
            orderHandleInfoDO.setHandlerId(type+"");
            orderHandleInfoDO.setUserPhone(currentUser.getPhoneNo());
            orderHandleInfoDO.setWorkerCode(currentUser.getUserCode());
            orderHandleInfoDO.setHandleTypeName(String.format("延迟订单：%d天",days));
            orderHandleInfoDO.setHandleReason(remark);
            orderHandleInfoService.addOrderHandleInfo(orderHandleInfoDO);
        }
        return response;
    }
}
