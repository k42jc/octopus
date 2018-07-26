package com.dafy.octopus.worker.order.service.impl;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.worker.order.dto.BizDetailType;
import com.dafy.octopus.worker.order.mapper.BizDetailTypeMapper;
import com.dafy.octopus.worker.order.service.IBizDetailTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务细分
 * Created by liaoxudong
 * Date:2018/6/5
 */
@Service
public class BizzDetailTypeService implements IBizDetailTypeService {

    private static final Logger logger = LoggerFactory.getLogger(BizzDetailTypeService.class);

    @Autowired
    private BizDetailTypeMapper bizDetailTypeMapper;

    @Override
    public Response listByBizType(Long request, boolean isAll) {
        return CommonUtils.buildSuccessResp(bizDetailTypeMapper.selectByBizType(request, isAll));
    }

    @Override
    @Transactional
    public Response add(Request request) {
        BizDetailType billType = catchDto(request);
        dataProcess("add", billType,"");
        return CommonUtils.buildSuccessResp();
    }

    private BizDetailType catchDto(Request request) {
        long id = request.getLong("id");
        String typeName = request.getString("typeName");
        String order = request.getString("order");
        if (Integer.valueOf(order) <= 0) {
            CommonUtils.throwIllegalParamsException("order不能小于0");
        }
        String status = request.getString("status");
        Long bizTypeId = request.getLong("bizTypeId");
        BizDetailType billType = new BizDetailType();
        billType.setId(id == 0 ? null : id);
        billType.setOrder(order);
        billType.setBizTypeId(bizTypeId == 0 ? null : bizTypeId);
        billType.setName(typeName);
        billType.setStatus(status);// 是否显示
        return billType;
    }

    /**
     * 数据库操作
     *
     * @param type
     * @param billType
     */
    @Transactional
    void dataProcess(String type, BizDetailType billType,String oldOrder) {
        boolean isUpdate = false;
        try {
            switch (type) {
                case "add":
                    bizDetailTypeMapper.insertSelective(billType);
                    break;
                case "edit":
                    isUpdate = true;
                    bizDetailTypeMapper.updateByPrimaryKeySelective(billType);
                    break;
                case "delete":
                    bizDetailTypeMapper.deleteByPrimaryKey(billType.getId());
                    break;
            }
        } catch (DuplicateKeyException e) {
            if (isUpdate && e.getMessage().contains("ORDER")) {
                logger.info("排序字段互换");
                synchronized (this){
                    // 先将需要改的新值改成-1 以免唯一索引限制
                    bizDetailTypeMapper.updateOrderToZero(billType.getId());
                    // 先把旧值改成新值
                    bizDetailTypeMapper.updateOrderExists(billType.getBizTypeId(),oldOrder,billType.getOrder());
                    // 再更新本条数据
                    bizDetailTypeMapper.updateByPrimaryKeySelective(billType);
                }
            }else{
                CommonUtils.throwException("warn","字段不允许重复");
            }
        }
    }

    @Override
    public Response edit(Request request) {
        BizDetailType billType = catchDto(request);
        dataProcess("edit", billType,request.getString("oldOrder"));
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response delete(Long request) {
        bizDetailTypeMapper.deleteByPrimaryKey(request);
        return CommonUtils.buildSuccessResp();
    }
}
