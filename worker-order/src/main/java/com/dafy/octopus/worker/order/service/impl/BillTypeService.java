package com.dafy.octopus.worker.order.service.impl;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.worker.order.dto.BillType;
import com.dafy.octopus.worker.order.mapper.BillTypeMapper;
import com.dafy.octopus.worker.order.service.IBillTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单分类的增删查改
 * Created by liaoxudong
 * Date:2018/6/4
 */

@Service
public class BillTypeService implements IBillTypeService{
    private static final Logger logger = LoggerFactory.getLogger(BillTypeService.class);

    @Autowired
    private BillTypeMapper billTypeMapper;


    @Override
    public Response list(boolean isAll) {
        List<BillType> all = billTypeMapper.findAll(isAll);
        return CommonUtils.buildSuccessResp(all);
    }

    @Override
    @Transactional
    public Response add(Request request) {
        BillType billType = catchDto(request);
        dataProcess("add",billType,"");
        return CommonUtils.buildSuccessResp();
    }

    private BillType catchDto(Request request) {
        long id = request.getLong("id");
        String typeName = request.getString("typeName");
        String order = request.getString("order");
        if (Integer.valueOf(order) <= 0) {
            CommonUtils.throwIllegalParamsException("order不能小于0");
        }
        String status = request.getString("status");
        BillType billType = new BillType();
        billType.setId(id == 0 ? null : id);
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
    private void dataProcess(String type, BillType billType,String oldOrder) {
        boolean isUpdate = false;
        try {
            switch (type) {
                case "add":
                    billTypeMapper.insertSelective(billType);
                    break;
                case "edit":
                    isUpdate = true;
                    billTypeMapper.updateByPrimaryKey(billType);
                    break;
                case "delete":
                    billTypeMapper.deleteByPrimaryKey(billType.getId());
                    break;
            }
        } catch (DuplicateKeyException e) {
            if (isUpdate && e.getMessage().contains("ORDER")) {
                logger.info("排序字段互换");
                synchronized (this){
                    // 先将需要改的新值改成-1 以免唯一索引限制
                    billTypeMapper.updateOrderToZero(billType.getId());
                    // 先把旧值改成新值
                    billTypeMapper.updateOrderExists(oldOrder,billType.getOrder());
                    // 再更新本条数据
                    billTypeMapper.updateByPrimaryKeySelective(billType);
                }
            }else{
                CommonUtils.throwException("warn","字段不允许重复");
            }
        }
    }

    public static List<Integer> test2(int[] nums){
        if(nums == null || nums.length == 0){
            return null;
        }
        int pre = nums[0];
        List<Integer> result = new ArrayList<Integer>();
        result.add(pre);
        for(int i=1;i<nums.length;i++){
            if(pre == nums[i]){
                continue;
            }
            pre = nums[i];
            result.add(pre);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] sums = {1,2,3,4,5,6,6,6,7};
        List<Integer> integers = test2(sums);
        System.out.println(integers);

    }

    /*public static void main(String[] args) {
        listFiles("D:\\workspace");
    }*/

    @Override
    public Response edit(Request request) {
        BillType billType = catchDto(request);
        String oldOrder = request.getString("oldOrder");
        dataProcess("edit",billType,oldOrder);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response delete(Long request) {
        billTypeMapper.deleteByPrimaryKey(request);
        return CommonUtils.buildSuccessResp();
    }
}
