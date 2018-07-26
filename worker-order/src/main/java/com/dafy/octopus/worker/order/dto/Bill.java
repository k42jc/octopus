package com.dafy.octopus.worker.order.dto;

import com.dafy.octopus.web.core.dto.Organization;
import com.dafy.octopus.web.core.dto.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工单对象
 */
public class Bill implements Serializable{
    private static final long serialVersionUID = -1689508238078992962L;
    private Long id;
    private Long belongsOrgId;//工单所属部门
    private Long dealOrgId;// 当前处理部门id
    private Long dealUserId;//当前处理人id
    // 是否超时 默认未超时 1表示超时
    private int overtime = 0;
    // 审批信息
    private BillApprInfo apprInfo;
    // 处理信息
    private BillDealInfo dealInfo;
    // 备注列表
    private List<BillRemark> remarkList;
    // 本工单操作日志列表
    private List<BillOpLog> opLogList;
    private int pressTimes;//催办次数
    private int intrustTimes;//委托次数
    private Organization dealOrg;
    private User dealUser;

    private String parentBillNo;

    private String billNo;

//    private Long billStatus = 0L;

    private SysConfig billStatus;

    private SysConfig urgentLevel;

    private BillType billType;

    private BizType bizType;

    private BizDetailType bizDetailType;

    private SysConfig billSource;

    private Date answerTime;

    private Customer customerInfo;

    private String connector;

    private String connectorPhone;

    private String desc;

    private String attachUrl;

    private String status;

    private User createUser;
    private Organization createOrg;// 工单所属部门  只有本部门职工才有权限查看操作

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public Long getBelongsOrgId() {
        return belongsOrgId;
    }

    public void setBelongsOrgId(Long belongsOrgId) {
        this.belongsOrgId = belongsOrgId;
    }

    public List<BillRemark> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<BillRemark> remarkList) {
        this.remarkList = remarkList;
    }

    public int getPressTimes() {
        return pressTimes;
    }

    public int getIntrustTimes() {
        return intrustTimes;
    }

    public void setIntrustTimes(int intrustTimes) {
        this.intrustTimes = intrustTimes;
    }

    public void setPressTimes(int pressTimes) {
        this.pressTimes = pressTimes;
    }

    private Date createTime;//工单创建时间
    private Date updateTime;// 工单更新时间 当前流程启动时间

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDealOrgId() {
        return dealOrgId;
    }

    public void setDealOrgId(Long dealOrgId) {
        this.dealOrgId = dealOrgId;
    }

    public Long getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(Long dealUserId) {
        this.dealUserId = dealUserId;
    }

    public BillApprInfo getApprInfo() {
        return apprInfo;
    }

    public void setApprInfo(BillApprInfo apprInfo) {
        this.apprInfo = apprInfo;
    }

    public BillDealInfo getDealInfo() {
        return dealInfo;
    }

    public void setDealInfo(BillDealInfo dealInfo) {
        this.dealInfo = dealInfo;
    }

    public Organization getDealOrg() {
        return dealOrg;
    }

    public void setDealOrg(Organization dealOrg) {
        this.dealOrg = dealOrg;
    }

    public User getDealUser() {
        return dealUser;
    }

    public void setDealUser(User dealUser) {
        this.dealUser = dealUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organization getCreateOrg() {
        return createOrg;
    }

    public void setCreateOrg(Organization createOrg) {
        this.createOrg = createOrg;
    }

    public String getParentBillNo() {
        return parentBillNo;
    }

    public void setParentBillNo(String parentBillNo) {
        this.parentBillNo = parentBillNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public SysConfig getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(SysConfig billStatus) {
        this.billStatus = billStatus;
    }

    public SysConfig getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(SysConfig urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public BizType getBizType() {
        return bizType;
    }

    public void setBizType(BizType bizType) {
        this.bizType = bizType;
    }

    public BizDetailType getBizDetailType() {
        return bizDetailType;
    }

    public void setBizDetailType(BizDetailType bizDetailType) {
        this.bizDetailType = bizDetailType;
    }

    public SysConfig getBillSource() {
        return billSource;
    }

    public void setBillSource(SysConfig billSource) {
        this.billSource = billSource;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorPhone() {
        return connectorPhone;
    }

    public void setConnectorPhone(String connectorPhone) {
        this.connectorPhone = connectorPhone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    private Long createUserId;
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    private Long createOrgId;

    public Long getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(Long createOrgId) {
        this.createOrgId = createOrgId;
    }

    private Long billStatusId;// 工单状态
    public void setBillStatusId(Long billStatusId) {
        this.billStatusId = billStatusId;
    }
    private Long urgentLevelId;//紧急程度
    public void setUrgentLevelId(Long urgentLevelId) {
        this.urgentLevelId = urgentLevelId;
    }
    private Long billSourceId;// 工单来源
    public void setBillSourceId(Long billSourceId) {
        this.billSourceId = billSourceId;
    }
    private Long bizDetailTypeId;//业务细分
    public void setBizDetailTypeId(Long bizDetailTypeId) {
        this.bizDetailTypeId = bizDetailTypeId;
    }
    private Long bizTypeId;// 业务分类
    public void setBizTypeId(Long bizTypeId) {
        this.bizTypeId = bizTypeId;
    }
    private Long customerId;
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    private Long billTypeId;// 工单类型
    public void setBillTypeId(Long billTypeId) {
        this.billTypeId = billTypeId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public Long getBillStatusId() {
        return billStatusId;
    }

    public Long getUrgentLevelId() {
        return urgentLevelId;
    }

    public Long getBillSourceId() {
        return billSourceId;
    }

    public Long getBizDetailTypeId() {
        return bizDetailTypeId;
    }

    public Long getBizTypeId() {
        return bizTypeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getBillTypeId() {
        return billTypeId;
    }

    public List<BillOpLog> getOpLogList() {
        return opLogList;
    }

    public void setOpLogList(List<BillOpLog> opLogList) {
        this.opLogList = opLogList;
    }
}