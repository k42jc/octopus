package com.dafy.octopus.auth.service.impl;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.po.Response;
import com.dafy.common.util.DateTimeUtils;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.auth.mapper.OrgMapper;
import com.dafy.octopus.auth.mapper.UserOrgMapper;
import com.dafy.octopus.auth.service.IOrgService;
import com.dafy.octopus.web.core.domain.Constants;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.dto.Organization;
import com.dafy.octopus.web.core.dto.UserOrg;
import com.dafy.octopus.web.core.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by liaoxudong
 * Date:2018/4/23
 */
@Service
public class OrgService implements IOrgService {
    private static final Logger logger = LoggerFactory.getLogger(OrgService.class);

    @Autowired
    private OrgMapper orgMapper;
    @Autowired
    private UserOrgMapper userOrgMapper;

    @Override
    public Response list(int pagenum, int pageSize) {
        Page<Organization> page = new Page<>(pagenum, pageSize);
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
        orgMapper.findOrgs(page);
        page.getData().forEach(d -> {// 格式化日期显示
            d.setCreateTimeStr(DateTimeUtils.formatDatetime1(d.getCreateTime()));
            if (d.getUpdateTime() != null) {
                d.setUpdateTimeStr(DateTimeUtils.formatDatetime1(d.getUpdateTime()));
            }
        });
        return CommonUtils.buildSuccessResp(page);
    }

    @Override
    public Response add(Request request) {
        String name = request.getString("name");
        String desc = request.getString("desc");
        long parentOrgId = request.getLong("parentOrgId");
        Organization org = new Organization();
        org.setParentId(parentOrgId);
        org.setName(name);
        org.setDesc(desc);
        orgMapper.insertSelective(org);
        // 更新部门数据缓存
        updateCachedOrgData();
        return CommonUtils.buildSuccessResp();
    }

    private void updateCachedOrgData() {
        List<Organization> orgs = orgMapper.findOrgs(new Page<>(1,10000));
        logger.info("更新部门数据到中央缓存");
        CacheFactory.putObject(Constants.OCTOPUS_ALL_ORG_KEY, orgs);
    }

    @Override
    public Response delete(long orgId) {
        // 先检查当前部门是否存在用户
        List<UserOrg> userOrgs = userOrgMapper.selectByOrgId(orgId);
        if (userOrgs != null && !userOrgs.isEmpty()) {
            CommonUtils.throwException(ResponseCode.ORG_EXIST_USER);
        }
        // 删除部门
        orgMapper.deleteByPrimaryKey(orgId);
        // 更新部门数据缓存
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response edit(Request request) {
        String name = request.getString("name");
        String desc = request.getString("desc");
        long parentOrgId = request.getLong("parentOrgId");
        long id = request.getLong("id");
        Organization org = new Organization();
        org.setName(name);
        org.setDesc(desc);
        org.setUpdateTime(new Date());
        org.setParentId(parentOrgId);
        org.setId(id);
        orgMapper.updateByPrimaryKeySelective(org);
        // 更新部门数据缓存
        return CommonUtils.buildSuccessResp();
    }
}
