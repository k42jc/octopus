package com.dafy.octopus.worker.order.service.impl;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.worker.order.dto.SysConfig;
import com.dafy.octopus.worker.order.mapper.SysConfigMapper;
import com.dafy.octopus.worker.order.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统启动加载数据库配置到缓存
 *
 * TODO 修改配置项后后刷新缓存
 *
 * Created by liaoxudong
 * Date:2018/6/4
 */

@Service
public class SysConfigService implements ISysConfigService{
    private static final Logger logger = LoggerFactory.getLogger(SysConfigService.class);

    @Autowired
    private SysConfigMapper sysConfigMapper;
    @Override
    public void init() {
        List<SysConfig> sysConfigs = sysConfigMapper.selectAll();
        List<SysConfig> billStatus = new ArrayList<>();
        List<SysConfig> billSource = new ArrayList<>();
        List<SysConfig> urgentLevel = new ArrayList<>(5);
        List<SysConfig> problemProp = new ArrayList<>(5);
        List<SysConfig> approvalResult = new ArrayList<>(5);
        List<SysConfig> billDealType = new ArrayList<>();
        sysConfigs.forEach(sysConfig -> {
            switch (sysConfig.getType()) {
                case BILL_STATUS://工单状态
                    billStatus.add(sysConfig);
                    break;
                case BILL_SOURCE://工单来源
                    billSource.add(sysConfig);
                    break;
                case URGENT_LEVEL://紧急程度
                    urgentLevel.add(sysConfig);
                    break;
                case PROBLEM_PROP://问题定性
                    problemProp.add(sysConfig);
                    break;
                case APPR_RESULT://审批结果
                    approvalResult.add(sysConfig);
                    break;
                case DEAL_TYPE://操作内容
                    billDealType.add(sysConfig);
                    break;
            }
        });
        logger.info("系统配置数据缓存预热：{}",sysConfigs);
        CacheFactory.putObject(BILL_STATUS,billStatus);
        CacheFactory.putObject(BILL_SOURCE,billSource);
        CacheFactory.putObject(URGENT_LEVEL,urgentLevel);
        CacheFactory.putObject(PROBLEM_PROP,problemProp);
        CacheFactory.putObject(APPR_RESULT,approvalResult);
        CacheFactory.putObject(DEAL_TYPE,billDealType);
    }

    @Override
    public List<SysConfig> getConfigsByType(String type,boolean filter) {
        List<SysConfig> resultList = CacheFactory.getObject(type, List.class);
        if(resultList == null){
            resultList = sysConfigMapper.selectByType(type);
            CacheFactory.putObject(type,resultList);
        }
        if (filter) {// 过滤禁用项
            return resultList.parallelStream()
                    .filter(sysConfig -> "0".equals(sysConfig.getStatus()))
//                    .filter(sysConfig -> sysConfig.getPid() == 0L)
                    .collect(Collectors.toList());
        }
        List<SysConfig> sysConfigs = resultList.parallelStream().filter(sysConfig -> sysConfig.getPid() == 0L).collect(Collectors.toList());
//        sysConfigs.sort((s1,s2) -> s1.getOrder().compareTo(s2.getOrder()));
        return sysConfigs;
    }

    @Override
    public List<SysConfig> getBillSource() {
        return getConfigsByType("bill-status",true);
    }

    @Override
    public List<SysConfig> getBillDealType() {
        return getConfigsByType("bill-deal-type",true);
    }

    @Override
    public SysConfig getSysConfigById(String type,Long id) {
        return sysConfigMapper.selectByPrimaryKey(id);
        /*List<SysConfig> configsByTypes = getConfigsByType(type,true);
        for (SysConfig sysConfig : configsByTypes) {
            if(sysConfig.getId().equals(id)){
                return sysConfig;
            }
        }
        return null;*/
    }


    @Transactional
    public Response update(Request request) {
        long id = request.getLong("id");
        String type = request.getString("type");
        // 先将缓存失效
        CacheFactory.deleteObject(type);
        String name = request.getString("name");
        String order = request.getString("order");
        String oldOrder = request.getString("oldOrder");
        if (Integer.valueOf(order) <= 0) {
            CommonUtils.throwIllegalParamsException("order不能小于0");
        }
        String status = request.getString("status");
        SysConfig sysConfig = new SysConfig(id,type,name,order,status);
        try {
            sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
        } catch (DuplicateKeyException e) {// 更新时发生序号冲突时，将序号互换
            if (e.getMessage().contains("ORDER")) {
                logger.info("排序字段互换");
                synchronized (this){
                    // 先将需要改的新值改成-1 以免唯一索引限制
                    sysConfigMapper.updateOrderToZero(id);
                    // 先把旧值改成新值
                    sysConfigMapper.updateOrderExists(type,oldOrder,order);
                    // 再更新本条数据
                    sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
                }
            }else{
                CommonUtils.throwException("warn","字段不允许重复");
            }
        }
        return CommonUtils.buildSuccessResp();
    }

    @Transactional
    public Response add(Request request) {
        // 先将缓存失效
        String type = request.getString("type");
        CacheFactory.deleteObject(type);
        String name = request.getString("name");
        String order = request.getString("order");
        if (Integer.valueOf(order) < 0) {
            CommonUtils.throwIllegalParamsException("order不能小于0");
        }
        String status = request.getString("status");
        Long pid = request.getLong("pid");
        SysConfig sysConfig = new SysConfig(type,name,order,status);
        sysConfig.setPid(pid);
        try {
            sysConfigMapper.insertSelective(sysConfig);
        } catch (DuplicateKeyException e) {
            logger.warn("数据操作异常：{}",e.getCause());
            CommonUtils.throwException("warn","字段不允许重复");
        }
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public List<SysConfig> getChildrenConfigByPid(Long pid,boolean filter) {
        if (pid == null || pid.equals(0L)) {
            CommonUtils.throwIllegalParamsException("pid");
        }
        if (filter) {// 过滤
            return sysConfigMapper.selectAvalibleChildrenByPId(pid);
        }
        return sysConfigMapper.selectChildrenByPId(pid);
    }
}
