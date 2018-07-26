package com.dafy.octopus.worker.order.service.impl;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.worker.order.dto.BizType;
import com.dafy.octopus.worker.order.mapper.BizTypeMapper;
import com.dafy.octopus.worker.order.service.IBizTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务分类
 * Created by liaoxudong
 * Date:2018/6/5
 */
@Service
public class BizzTypeService implements IBizTypeService {

    private static final Logger logger = LoggerFactory.getLogger(BizzTypeService.class);

    @Autowired
    private BizTypeMapper bizTypeMapper;

    @Override
    public Response listByBillType(Long request,boolean isAll) {
        return CommonUtils.buildSuccessResp(bizTypeMapper.selectByBillType(request,isAll));
    }

    @Override
    @Transactional
    public Response add(Request request) {
        BizType billType = catchDto(request);
        dataProcess("add",billType,"");
        return CommonUtils.buildSuccessResp();
    }

    private BizType catchDto(Request request) {
        long id = request.getLong("id");
        String typeName = request.getString("typeName");
        String order = request.getString("order");
        if (Integer.valueOf(order) <= 0) {
            CommonUtils.throwIllegalParamsException("order不能小于0");
        }
        String status = request.getString("status");
        Long billTypeId = request.getLong("billTypeId");
        BizType billType = new BizType();
        billType.setId(id == 0 ? null : id);
        billType.setBillTypeId(billTypeId == 0?null:billTypeId);// 更新时不需要
        billType.setOrder(order);
        billType.setName(typeName);
        billType.setStatus(status);// 是否显示
        return billType;
    }

    /**
     * 数据库操作
     * @param type
     * @param billType
     */
    @Transactional
    void dataProcess(String type, BizType billType,String oldOrder) {
        boolean isUpdate = false;
        try{
            switch (type) {
                case "add":
                    bizTypeMapper.insertSelective(billType);
                    break;
                case "edit":
                    isUpdate = true;
                    bizTypeMapper.updateByPrimaryKeySelective(billType);
                    break;
                case "delete":
                    bizTypeMapper.deleteByPrimaryKey(billType.getId());
                    break;
            }
        } catch (DuplicateKeyException e) {
            if (isUpdate && e.getMessage().contains("ORDER")) {
                logger.info("排序字段互换");
                synchronized (this){
                    // 先将需要改的新值改成-1 以免唯一索引限制
                    bizTypeMapper.updateOrderToZero(billType.getId());
                    // 先把旧值改成新值
                    bizTypeMapper.updateOrderExists(billType.getBillTypeId(),oldOrder,billType.getOrder());
                    // 再更新本条数据
                    bizTypeMapper.updateByPrimaryKeySelective(billType);
                }
            }else{
                CommonUtils.throwException("warn","字段不允许重复");
            }
        }
    }

    @Override
    public Response edit(Request request) {
        BizType billType = catchDto(request);
        dataProcess("edit",billType,request.getString("oldOrder"));
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response delete(Long request) {
        bizTypeMapper.deleteByPrimaryKey(request);
        return CommonUtils.buildSuccessResp();
    }
}
