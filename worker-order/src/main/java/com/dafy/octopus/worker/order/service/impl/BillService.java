package com.dafy.octopus.worker.order.service.impl;

import com.dafy.cache.factory.CacheFactory;
import com.dafy.common.exception.ExceptionCode;
import com.dafy.common.po.Response;
import com.dafy.common.util.DateTimeUtils;
import com.dafy.common.util.JsonUtils;
import com.dafy.common.util.StringUtils;
import com.dafy.dal.page.po.Page;
import com.dafy.octopus.web.core.context.SystemContext;
import com.dafy.octopus.web.core.domain.Request;
import com.dafy.octopus.web.core.domain.ResponseCode;
import com.dafy.octopus.web.core.dto.Organization;
import com.dafy.octopus.web.core.dto.User;
import com.dafy.octopus.web.core.file.FileFactory;
import com.dafy.octopus.web.core.notify.NotifyFactory;
import com.dafy.octopus.web.core.notify.email.Email;
import com.dafy.octopus.web.core.notify.email.EmailType;
import com.dafy.octopus.web.core.utils.CommonUtils;
import com.dafy.octopus.web.core.utils.SubjectUtils;
import com.dafy.octopus.worker.order.constants.BillOpLogType;
import com.dafy.octopus.worker.order.constants.BillStatus;
import com.dafy.octopus.worker.order.constants.Constants;
import com.dafy.octopus.worker.order.constants.UHMType;
import com.dafy.octopus.worker.order.dto.*;
import com.dafy.octopus.worker.order.mapper.*;
import com.dafy.octopus.worker.order.service.IBillService;
import com.dafy.octopus.worker.order.service.ISysConfigService;
import com.dafy.octopus.worker.order.util.BillNoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 工单处理
 * Created by liaoxudong
 * Date:2018/6/1
 */

@Service
public class BillService implements IBillService {
    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private CustomerMapper customerMapper;// 客户信息

    @Autowired
    private BillMapper billMapper;// 工单信息

    @Autowired
    private BillOpLogMapper billOpLogMapper;//工单操作日志

//    @Autowired
//    private IBillOpLogService billOpLogService;//

    @Autowired
    private BillRemarkMapper billRemarkMapper;// 工单备注
    @Autowired
    private BillApprInfoMapper billApprInfoMapper;// 工单审批信息
    @Autowired
    private BillDealInfoMapper billDealInfoMapper;//工单处理信息
    @Value("${config.file.export.type}")
    private String exportType;

    /**
     * 当前分页查询缓存key
     * @see com.dafy.dal.mybatis.plugin.PageInterceptor
     * {@code cachePageData}
     */
    private static final String cacheKey = "mybatis:cache:com.dafy.octopus.worker.order.mapper.BillMapper.list";

    /**
     * @param request 请求实体
     *                -userId 当前登录用户ID
     *                -createTimeStart 受理时间开始
     *                -createTimeEnd 受理时间结束
     *                -condition 查询条件
     *                -urgent 紧急程度
     *                -billType 工单分类
     *                -bizType 业务分类
     *                -bizDetailType 业务详细分类
     *                -billStatus 工单处理状态 多个传列表
     * @return
     */

    @Override
//    @Transactional(readOnly = true)
    public Response list(Request request) {
        Request oldRequest = new Request(request);
//        request.removes("cookie,accept,connection,user-agent,referer,host,accept-language,accept-encoding");
        User currentUser = SubjectUtils.getCurrentUser();
//        request.put("createOrgId", currentUser.getOrganization().getId());
        Organization organization = currentUser.getOrganization();
        Organization parentOrg = organization.getParentOrg();
        request.put("belongsOrgId", parentOrg == null?organization.getId():parentOrg.getId());
        int pageNum = request.getInt("pageNum");
        int pageSize = request.getInt("pageSize");
        Page<Bill> page = new Page<>(pageNum == 0 ? 1 : pageNum, pageSize == 0 ? 100 : pageSize);
        page.setParams(request);
        page.setCache(true);
        page.setCountSql("select count(1) from T_BILL_INFO b left join T_CUSTOMER_INFO ci on b.CUSTOMER_ID=ci.ID ");
        billMapper.list(page);
        // 设置用户与部门信息
        List<Bill> bills = page.getData();
        bills.forEach(bill -> {
            // 工单创建人
            User createUser = bill.getCreateUser();
//            String s = JsonUtils.toJson(bill);
            createUser = SystemContext.getUserById(createUser.getId());
            bill.setCreateUser(createUser);
            // 工单当前处理人
            User dealUser = bill.getDealUser();
            if(dealUser != null)
                dealUser = SystemContext.getUserById(dealUser.getId());
            bill.setDealUser(dealUser);
            // 工单创建部门
            Organization createOrg = bill.getCreateOrg();
            createOrg = SystemContext.getOrgById(createOrg.getId());
            bill.setCreateOrg(createOrg);
            // 工单当前处理部门
            Organization dealOrg = bill.getDealOrg();
            if(dealOrg != null)
                dealOrg = SystemContext.getOrgById(dealOrg.getId());
            bill.setDealOrg(dealOrg);
            if (oldRequest.containsKey("billId")) {// 获取工单详情时
                // 获取本工单的操作日志
                List<BillOpLog> opLogList = billOpLogMapper.selectByBillId(bill.getId());
                bill.setOpLogList(opLogList);
            }
            //设置工单超时状态
            if (!BillStatus.POST_HANDLER.getId().equals(bill.getBillStatus().getId()) && !BillStatus.CLOSED.getId().equals(bill.getBillStatus().getId())) {// 非已处理订单和已关闭工单

                Long overtime = DateTimeUtils.doubleDateDiffer(bill.getAnswerTime());
                if (overtime > 0) {
                    bill.setOvertime(1);
                }

            }
        });
//        String json = JsonUtils.toJson(page);
//        List list = JsonUtils.fromJson(json, List.class);
        if (oldRequest.containsKey("billId")) {// 获取工单详情时
            if (bills.isEmpty()) {
                return CommonUtils.buildSuccessResp();
            }
            return CommonUtils.buildSuccessResp(bills.get(0));
        }
        return CommonUtils.buildSuccessResp(page);
    }

    @Override
    @Transactional
    public Response create(Request request) {
        CommonUtils.assertHasParams(request, "cusInfo,billInfo");
        Map cusInfo = request.get("cusInfo", Map.class);
        Map billInfo = request.get("billInfo", Map.class);
        String cusInfoJson = JsonUtils.toJson(cusInfo);
        String billInfoJson = JsonUtils.toJson(billInfo);
        Long customerId = addCustomerInfo(JsonUtils.fromJson(cusInfoJson, Request.class));
        addBillInfo(JsonUtils.fromJson(billInfoJson, Request.class), customerId);
        return CommonUtils.buildSuccessResp();
    }

    /**
     * 清除分页查询缓存
     * @see com.dafy.dal.mybatis.plugin.PageInterceptor
     * {@code setPageData}
     */
    private static void clearCurrentPageCache() {
        logger.info("清除缓存：{}",cacheKey);
        CacheFactory.deleteString(cacheKey);
    }

    /**
     * 添加工单信息
     *
     * @param billInfo   工单信息
     * @param customerId 已添加的客户信息
     */
    private void addBillInfo(Request billInfo, Long customerId) {
        logger.info("添加工单信息：{}", billInfo);
        Long billType = billInfo.getLong("billType");
        Long billBizType = billInfo.getLong("billBizType");
        Long billBizDetailType = billInfo.getLong("billBizDetailType");
        Long billSource = billInfo.getLong("billSource");
        Long eLevel = billInfo.getLong("eLevel");
        String parentBillNo = billInfo.getString("parentBillNo");
        String connector = billInfo.getString("connector");
        String connectorPhone = billInfo.getString("connectorPhone");
        String answerTime = billInfo.getString("answerTime");
        String desc = billInfo.getString("desc");
        String attachURL = billInfo.getString("attachURL");
        Bill bill = new Bill();
        bill.setBillTypeId(billType);
        bill.setCustomerId(customerId);
        bill.setBizTypeId(billBizType);
        bill.setBizDetailTypeId(billBizDetailType);
        bill.setBillSourceId(billSource);
        bill.setUrgentLevelId(eLevel);
        bill.setConnector(connector);
        bill.setConnectorPhone(connectorPhone);
        bill.setAnswerTime(StringUtils.isEmpty(answerTime) ? null : DateTimeUtils.strToDate(answerTime));
        bill.setDesc(desc);
        bill.setAttachUrl(attachURL);
        bill.setBillNo(BillNoUtils.generateBillNo(Constants.BILL_NO_PREFIX, Constants.BILL_NO_NUMS));
        bill.setParentBillNo(parentBillNo);
        bill.setBillStatusId(BillStatus.PRE_REVIEW.getId());
        buildUserInfo(bill);
        billMapper.insertSelective(bill);
        //工单操作日志
        addBillOpLog(bill.getId(), BillOpLogType.CREATE, SubjectUtils.getCurrentUser().getOrganization().getName());
    }

    /**
     * 工单操作日志
     *
     * @param billId
     * @param create
     * @param nextDeal 下一处理人
     */
    private void addBillOpLog(Long billId, BillOpLogType create, String nextDeal) {
        logger.info("添加操作日志：{},{},{}", billId, create, nextDeal);
        BillOpLog billOpLog = new BillOpLog();
        billOpLog.setBillId(billId);
        billOpLog.setDealType(create.getId());
        User currentUser = SubjectUtils.getCurrentUser();
        billOpLog.setUserId(currentUser.getId());
        billOpLog.setUserDeptId(currentUser.getOrganization().getId());
        billOpLog.setNextDeal(nextDeal);
        billOpLogMapper.insertSelective(billOpLog);
        //清除分页查询缓存
        clearCurrentPageCache(); 
    }

    /**
     * 工单受理人 所属部门等关键信息
     *
     * @param bill
     */
    private void buildUserInfo(Bill bill) {
        User currentUser = SubjectUtils.getCurrentUser();
        bill.setCreateUserId(currentUser.getId());
        Long orgId = currentUser.getOrganization().getId();
        bill.setCreateOrgId(orgId);// 工单所属部门
        bill.setDealOrgId(orgId);// 工单处理部门 每个创建的工单对整个部门可操作
        Organization organization = currentUser.getOrganization();
        Organization parentOrg = organization.getParentOrg();
        bill.setBelongsOrgId(parentOrg != null?parentOrg.getId():organization.getId());
    }

    /**
     * 添加客户信息
     *
     * @param cusInfo
     * @return
     */
    private Long addCustomerInfo(Request cusInfo) {
        logger.info("添加客户信息：{}", cusInfo);
        String cusName = cusInfo.getString("cusName");
        int cusSex = cusInfo.getInt("cusSex");
        String cusCallInPhone = cusInfo.getString("cusCallInPhone");
        String cusCallInTime = cusInfo.getString("callTime");
        Customer customer = new Customer();
        customer.setName(cusName);
        customer.setSex(cusSex);
        customer.setPhoneno(cusCallInPhone);
        customer.setCallTime(StringUtils.isEmpty(cusCallInTime) ? null : DateTimeUtils.strToDate(cusCallInTime));
        customerMapper.insertSelective(customer);
        logger.info("客户信息添加成功：{}", customer.getId());
        return customer.getId();
    }


    @Override
    public Response updateHandlerMan(Request request) {
        int type = request.getInt("type");
        Bill bill = new Bill();
        long billId = request.getLong("billId");
        bill.setId(billId);
        User currentUser = SubjectUtils.getCurrentUser();
        switch (UHMType.parse(type)) {
            case FREE_REVIEW:// 释放审核中操作 将当前操作人设置为0表示无人正在操作此工单 部门其他人可以继续操作
                if(!SubjectUtils.hasPermissions("WorkOrder:free")){
                    CommonUtils.throwException(ResponseCode.NO_PERMISSION);
                }
                bill.setDealUserId(0L);
                bill.setBillStatusId(BillStatus.PRE_REVIEW.getId());// 状态设置为回到待审核
                bill.setDealOrgId(currentUser.getOrganization().getId());
                billMapper.updateByPrimaryKeySelective(bill);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.FREE, currentUser.getOrganization().getName());
                break;
            case FREE_HANDLER:// 释放处理中操作 将当前操作人设置为0表示无人正在操作此工单 部门其他人可以继续操作
                if(!SubjectUtils.hasPermissions("WorkOrder:free")){
                    CommonUtils.throwException(ResponseCode.NO_PERMISSION);
                }
                bill.setDealUserId(0L);
                bill.setBillStatusId(BillStatus.PRE_HANDLER.getId());// 状态设置为回到待审核
                bill.setDealOrgId(currentUser.getOrganization().getId());
                billMapper.updateByPrimaryKeySelective(bill);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.FREE, currentUser.getOrganization().getName());
                break;
            case DEPUTE:// 委托操作
                if(!SubjectUtils.hasPermissions("WorkOrder:intrust")){
                    CommonUtils.throwException(ResponseCode.NO_PERMISSION);
                }
                long dealUserId = request.getLong("dealUserId");
                bill.setDealUserId(dealUserId);
                billMapper.updateByPrimaryKeySelective(bill);
                User user = SystemContext.getUserById(dealUserId);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.DEPUTE, user.getUserName());
                break;
            case BACK:// 回退操作 将工单处理人更改为创建人
                if(!SubjectUtils.hasPermissions("WorkOrder:back")){
                    CommonUtils.throwException(ResponseCode.NO_PERMISSION);
                }
                billMapper.backBillById(bill.getId());
                User createUser = SubjectUtils.getCurrentUser();
                // 修改工单状态
                bill.setBillStatusId(BillStatus.BACKED.getId());
                billMapper.updateBillStatus(bill);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.BACK, createUser.getUserName());
                break;
            case CLOSE:// 关闭工单 将此工单设置为不可用
                if(!SubjectUtils.hasPermissions("WorkOrder:close")){
                    CommonUtils.throwException(ResponseCode.NO_PERMISSION);
                }
                bill.setBillStatusId(BillStatus.CLOSED.getId());
                billMapper.updateBillStatus(bill);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.CLOSE, "");
                break;
            case PRESS:// 催办
                // 添加操作日志
//                addBillOpLog(billId, BillOpLogType.PRESS, null);
                billMapper.updatePreeTimes(billId, (int)CacheFactory.incrBy("press:bill:times:"+billId,1));// 更新催办次数
                // 删除分页数据缓存
                clearCurrentPageCache();
                //获取当前工单的处理人，如果不存在就不发邮件了
                try {
                    Bill currentBill = billMapper.selectByPrimaryKey(billId);
                    User currentDealUser = SystemContext.getUserById(currentBill.getDealUserId());
                    if (currentDealUser == null) {
                        logger.warn("当前工单没有处理人，暂时不发送提醒邮件");
                    } else {
                        Email email = new Email();
                        email.setContentType(EmailType.SIMPLE);
                        email.setReceiver(currentDealUser.getEmail());
                        email.setSubject("工单催办");
                        email.setContent(String.format("有人催办工单了，请及时处理，工单号:%s", currentBill.getBillNo()));
                        NotifyFactory.sendEmail(email);
                    }
                }catch (Exception e){
                    logger.error("发送催办邮件失败",e);
                }
                break;
        }
        return CommonUtils.buildSuccessResp();
    }

    @Override
    @Transactional
    public Response addRemark(Request request) {
        long billId = request.getLong("billId");
        String remark = request.getString("remark");
        BillRemark billRemark = new BillRemark();
        billRemark.setBillId(billId);
        billRemark.setUserId(SubjectUtils.getCurrentUser().getId());
        billRemark.setRemark(remark);
        billRemarkMapper.insertSelective(billRemark);
        return CommonUtils.buildSuccessResp();
    }

    @Override
    @Transactional
    public Response handler(Request request) {
        int type = request.getInt("type");// 审核 处理
        Long billId = request.getLong("billId");// 审核 处理
        Map<String, Object> data = request.get("data", Map.class);
        User currentUser = SubjectUtils.getCurrentUser();
        Bill bill = new Bill();
        bill.setId(billId);
        // 审核提交/审核暂存
        if (type == Constants.BILL_APPROVAL || type == Constants.BILL_APPROVAL_TEMPSAVE) {
            // 必须有审核工单权限
            if(!SubjectUtils.hasPermissions("WorkOrder:approval")){
                CommonUtils.throwException(ResponseCode.NO_PERMISSION);
            }
            BillApprInfo billApprInfo = new BillApprInfo();
            billApprInfo.setBillId(billId);
            billApprInfo.setResult(data.get("result")+"");// 审核结果 通过1 不通过2
            billApprInfo.setDesc(data.get("desc")+"");
            billApprInfo.setAttachUrl(data.get("url")+"");
            billApprInfo.setUserId(currentUser.getId());
            billApprInfo.setUserDeptId(currentUser.getOrganization().getId());
            if (type == Constants.BILL_APPROVAL_TEMPSAVE) {// 如果是暂存单，设置状态为1
                billApprInfo.setStatus("1");// 1表示暂存
//                bill.setBillStatusId(BillStatus.REVIEW_ING.getId()); //暂存单状态为审核中
            } else{
                bill.setBillStatusId(BillStatus.PRE_HANDLER.getId());
                bill.setDealUserId(0L);// 当前处理人为空
                bill.setDealOrgId(currentUser.getOrganization().getId());// 当前处理机构为处理人所在部门
                String billCreateTime = data.get("billCreateTime")+"";
                // 处理用时 格式为：天 时:分
                String usedTime = DateTimeUtils.daysDiff(DateTimeUtils.strToDate(billCreateTime), new Date(), false);
                billApprInfo.setUsedTime(usedTime);// 审批用时=当前时间减去此单的创建时间
                // 修改工单状态 为待处理
                billMapper.updateBillStatus(bill);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.REVIEW, currentUser.getOrganization().getName());
            }
            // 先删除原来的审核信息
            billApprInfoMapper.deleteByBillId(billId);
            billApprInfoMapper.insertSelective(billApprInfo);
        }
        // 处理提交/处理暂存
        if (type == Constants.BILL_DEAL || type == Constants.BILL_DEAL_TEMPSAVE) {
            // 必须有处理工单权限
            if(!SubjectUtils.hasPermissions("WorkOrder:deal")){
                CommonUtils.throwException(ResponseCode.NO_PERMISSION);
            }
            BillDealInfo dealInfo = new BillDealInfo();
            dealInfo.setBillId(billId);
            Object problem = data.get("problem");
            Object subProblem = data.get("subProblem");
            dealInfo.setProblem(StringUtils.isEmpty(problem) ? 0L : Long.valueOf(problem+""));// 问题定性
            dealInfo.setSubProblem(StringUtils.isEmpty(subProblem) ? 0L : Long.valueOf(subProblem+""));// 问题细分
            dealInfo.setResult(data.get("result")+"");// 处理结果
            dealInfo.setReVisit(data.get("reVisit")+"");//是否回访 1 是 默认0 否
            String reVisitTime = data.get("reVisitTime")+"";
            if (!StringUtils.isEmpty(reVisitTime)) {
                dealInfo.setReVisitTime(DateTimeUtils.strToDate(reVisitTime));// 回访时间
            }
            dealInfo.setDesc(data.get("desc")+"");//处理信息
            dealInfo.setAttachUrl(data.get("url")+"");
            dealInfo.setUserId(currentUser.getId());
            dealInfo.setUserDeptId(currentUser.getOrganization().getId());
            if (type == Constants.BILL_DEAL_TEMPSAVE) {// 如果是暂存单，设置状态为1
                dealInfo.setStatus("1");// 1表示暂存
//                bill.setBillStatusId(BillStatus.HANDLER_ING.getId()); //暂存单状态为处理中
            }else {
                // 工单审核时间
                String billApprTime = data.get("billApprTime")+"";
                // 处理用时 格式为：天 时:分
                String usedTime = DateTimeUtils.daysDiff(DateTimeUtils.strToDate(billApprTime), new Date(), false);
                dealInfo.setUsedTime(usedTime);// 审批用时=当前时间减去此单的创建时间
                // 修改工单状态 为已处理
                bill.setBillStatusId(BillStatus.POST_HANDLER.getId());
                billMapper.updateBillStatus(bill);
                // 添加操作日志
                addBillOpLog(billId, BillOpLogType.DEAL, "");
            }
            billDealInfoMapper.deleteByBillId(billId);
            billDealInfoMapper.insertSelective(dealInfo);
        }
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response export(Request request) {
        request.put("pagenum", 1);
        request.put("pagesize", 100000);
        Response response = list(request);
        Page<Bill> page = (Page) response.getData();
        List<Bill> billList = page.getData();
        if (billList == null || billList.isEmpty()) {
            CommonUtils.throwException("error","当前选定条件没有查询到数据");
        }
        // 从SpringMVC绑定的reqest上下文获取request、response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = requestAttributes.getRequest();
        HttpServletResponse servletResponse = requestAttributes.getResponse();
        // 导出excel
        FileFactory exportFactory = FileFactory.getInstance(exportType);
        Map<String,Object> dataMap = new HashMap<>();
        // 组装数据
        List<String> headers = new ArrayList<>(23);
        List<List<Object>> contents = new ArrayList<>();
        dataMap.put("headers",headers);
        dataMap.put("contents",contents);
        // 列标题数据
        buildHeaders(headers);
        // 文档内容数据
        buildContents(contents,billList);
        exportFactory.export(servletResponse,"工单列表",dataMap);

        /*servletResponse.addHeader("Content-Disposition", "attachment;filename="+ new String("工单列表".getBytes()));
        try {
           byte[] content = new ByteArrayOutputStream(servletResponse.getOutputStream()).toByteArray();
               InputStream is = new ByteArrayInputStream(content);
               // 设置response参数，可以打开下载页面
               response.reset();
               response.setContentType("application/vnd.ms-excel;charset=utf-8");
               response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes(), "iso-8859-1"));
               ServletOutputStream out = response.getOutputStream();
               BufferedInputStream bis = null;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return CommonUtils.buildSuccessResp();
    }

    /**
     * 组建文档内容数据
     * @param contents
     * @param billList
     */
    private void buildContents(List<List<Object>> contents, List<Bill> billList) {
        billList.forEach(bill -> {
            List<Object> line = new ArrayList<>(23);
            line.add(DateTimeUtils.formatDatetime1(bill.getCustomerInfo().getCallTime()));// 来电时间
            line.add(DateTimeUtils.formatDatetime1(bill.getCreateTime()));// 受理时间
            line.add(bill.getCreateUser().getUserName());// 受理人
            line.add(bill.getUrgentLevel().getName());//紧急程度
            line.add(bill.getBillSource().getName());// 工单来源
            line.add(bill.getCustomerInfo().getName());// 客户姓名
            line.add(bill.getCustomerInfo().getPhoneno());// 来电号码
            line.add(bill.getConnector());//联系人
            line.add(bill.getConnectorPhone());//联系电话
            line.add(bill.getDesc());// 受理信息
            line.add(bill.getBillType().getName());//产品类型
            line.add(bill.getBizType().getName());//业务大类
            line.add(bill.getBizDetailType().getName());//业务细分
            line.add(bill.getBillNo());//工单号
            BillApprInfo apprInfo = bill.getApprInfo();
            if (apprInfo != null) {// 当存在审核信息时
                line.add(SystemContext.getUserById(apprInfo.getUserId()).getUserName());//审批人
                line.add(DateTimeUtils.formatDatetime1(apprInfo.getCreateTime()));//审批时间
                line.add(apprInfo.getDesc());
            }else{
                line.add("/");
                line.add("/");
                line.add("/");//备注
            }
            BillDealInfo dealInfo = bill.getDealInfo();
            if(dealInfo != null){// 当存在处理信息时
                SysConfig sysConfigById = sysConfigService.getSysConfigById(ISysConfigService.PROBLEM_PROP, dealInfo.getProblem());
                line.add(sysConfigById != null?sysConfigById.getName():"/");//问题定性
                /*StringBuilder stringBuilder = new StringBuilder();
                Long dealOrgId = bill.getDealOrgId();
                Long dealUserId = bill.getDealUserId();
                if(dealOrgId != null && 0L !=dealOrgId){
                    stringBuilder.append(SystemContext.getOrgById(dealOrgId).getName()).append("/");
                }
                if(dealUserId != null && 0L !=dealUserId){
                    stringBuilder.append(SystemContext.getUserById(dealUserId).getUserName());
                }*/
                User user = SystemContext.getUserById(dealInfo.getUserId());
                line.add(DateTimeUtils.formatDatetime1(dealInfo.getCreateTime()));// 处理时间
                line.add(user.getUserName());// 对接部门/人
                line.add(dealInfo.getDesc());//处理意见
                line.add(dealInfo.getUsedTime());//"处理时效"
            }else{
                line.add("/");
                line.add("/");
                line.add("/");
                line.add("/");
                line.add("/");
            }

            // 处理信息和审核信息都存在 并且处理状态不是暂存
            if (apprInfo != null && dealInfo != null && "0".equals(dealInfo.getStatus())) {
                String solveTimes = DateTimeUtils.daysDiff(apprInfo.getCreateTime(), dealInfo.getCreateTime(), false);
                line.add(solveTimes);// 解决时效 = 审批完成到处理完成的时间差
            } else {
                line.add("/");
            }
            contents.add(line);
        });
    }

    /**
     * 根据模板组件导出文件标题
     * @param headers
     */
    private void buildHeaders(List<String> headers) {
        headers.add("来电时间");
        headers.add("受理时间");
        headers.add("受理人\n");
        headers.add("紧急程度\n");
        headers.add("工单来源\n");
        headers.add("客户姓名\n");
        headers.add("来电号码\n");
        headers.add("联系人\n");
        headers.add("联系电话\n");
        headers.add("受理信息\n");
        headers.add("产品类型\n");
        headers.add("业务大类\n");
        headers.add("业务细分\n");
        headers.add("工单号\n");
        headers.add("审批人\n");
        headers.add("审批时间\n");
        headers.add("审批备注\n");
        headers.add("问题定性\n");
        headers.add("处理时间\n");
        headers.add("处理人\n");
        headers.add("处理意见\n");
        headers.add("处理时效\n");
        headers.add("解决时效\n");
    }

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public Response detail(Long billId) {
//        Request request = new Request();
//        request.put("billId", billId);
        Bill detail = billMapper.findDetailById(billId);
        if (detail == null) {
            CommonUtils.throwException("error","工单不存在");
        }
        // 处理数据格式问题
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", detail.getId());
        resultMap.put("createTime", DateTimeUtils.formatDatetime1(detail.getCreateTime()));

        resultMap.put("createUserId", detail.getCreateUserId());
        resultMap.put("billNo", detail.getBillNo());// 工单号
        resultMap.put("parentBillNo", detail.getParentBillNo());// 父级工单号
        SysConfig billStatus = sysConfigService.getSysConfigById(ISysConfigService.BILL_STATUS, detail.getBillStatusId());
        resultMap.put("billStatusId", detail.getBillStatusId());// 工单状态
        resultMap.put("billStatus", billStatus == null?"":billStatus.getName());// 工单状态
        //客户信息
        Map<String, Object> customerInfo = new HashMap<>();
        resultMap.put("customerInfo",customerInfo);
        Customer customer = detail.getCustomerInfo();
        customerInfo.put("name", customer.getName());
        customerInfo.put("sex", customer.getSex() == 1?"男":"女");
        customerInfo.put("phone", customer.getPhoneno());
        customerInfo.put("connector", detail.getConnector());
        customerInfo.put("connectorPhone", detail.getConnectorPhone());
        customerInfo.put("callTime", detail.getCustomerInfo().getCallTime());
        // 工单信息
        Map<String, Object> billInfo = new HashMap<>();
        resultMap.put("billInfo", billInfo);
        BillType billType = detail.getBillType();
        billInfo.put("billType",billType == null?"":billType.getName());
        BizType bizType = detail.getBizType();
        billInfo.put("bizType",bizType == null?"":bizType.getName());
        BizDetailType bizDetailType = detail.getBizDetailType();
        billInfo.put("bizDetailType",bizDetailType == null?"":bizDetailType.getName());
        SysConfig billSource = sysConfigService.getSysConfigById(ISysConfigService.BILL_SOURCE, detail.getBillSourceId());
        billInfo.put("billSource", billSource == null?"":billSource.getName());
        billInfo.put("status", "0".equals(detail.getStatus())?"有效":"无效");
        // 受理信息
        Map<String, Object> receiveInfo = new HashMap<>();
        resultMap.put("receiveInfo", receiveInfo);
        receiveInfo.put("createUser", SystemContext.getUserById(detail.getCreateUserId()).getUserName());
        receiveInfo.put("createOrg", SystemContext.getOrgById(detail.getCreateOrgId()).getName());
        SysConfig urgentLevel = sysConfigService.getSysConfigById(ISysConfigService.URGENT_LEVEL, detail.getUrgentLevelId());
        receiveInfo.put("urgentLevel", urgentLevel == null?"":urgentLevel.getName());
        receiveInfo.put("createTime", DateTimeUtils.formatDatetime1(detail.getCreateTime()));
        receiveInfo.put("answerTime", DateTimeUtils.formatDatetime1(detail.getAnswerTime()));
        receiveInfo.put("desc", detail.getDesc());
        receiveInfo.put("attachUrl", detail.getAttachUrl());
        // 审批信息
        Map<String, Object> apprInfo = new HashMap<>();
        BillApprInfo billApprInfo = detail.getApprInfo();
        resultMap.put("apprInfo", apprInfo);
        if (billApprInfo != null) {
            apprInfo.put("createTime", DateTimeUtils.formatDatetime1(billApprInfo.getCreateTime()));
            apprInfo.put("usedTime",billApprInfo.getUsedTime());
            apprInfo.put("result", "1".equals(billApprInfo.getResult())?"通过":"不通过");
            apprInfo.put("createUser",SystemContext.getUserById(billApprInfo.getUserId()).getUserName());
            apprInfo.put("createOrg",SystemContext.getOrgById(billApprInfo.getUserDeptId()).getName());
            apprInfo.put("desc",billApprInfo.getDesc());
            apprInfo.put("attachUrl",billApprInfo.getAttachUrl());
            apprInfo.put("status",billApprInfo.getStatus());
        }
        // 处理信息
        Map<String, Object> dealInfo = new HashMap<>();
        BillDealInfo billDealInfo = detail.getDealInfo();
        resultMap.put("dealInfo", dealInfo);
        if (billDealInfo != null) {
            dealInfo.put("createTime", DateTimeUtils.formatDatetime1(billDealInfo.getCreateTime()));
            dealInfo.put("usedTime",billDealInfo.getUsedTime());
            SysConfig problem = sysConfigService.getSysConfigById(ISysConfigService.PROBLEM_PROP, billDealInfo.getProblem());
            dealInfo.put("problem", problem == null?"":problem.getName());
            SysConfig subProblem = sysConfigService.getSysConfigById(ISysConfigService.PROBLEM_PROP, billDealInfo.getSubProblem());
            dealInfo.put("subProblem", subProblem == null?"":subProblem.getName());
            dealInfo.put("reVisit", "1".equals(billDealInfo.getReVisit())?"是":"否");
            dealInfo.put("reVisitTime", DateTimeUtils.formatDatetime1(billDealInfo.getReVisitTime()));
            dealInfo.put("result", "1".equals(billDealInfo.getResult())?"通过":"不通过");
            dealInfo.put("createUser",SystemContext.getUserById(billDealInfo.getUserId()).getUserName());
            dealInfo.put("createOrg",SystemContext.getOrgById(billDealInfo.getUserDeptId()).getName());
            dealInfo.put("desc",billDealInfo.getDesc());
            dealInfo.put("attachUrl",billDealInfo.getAttachUrl());
            dealInfo.put("status", billDealInfo.getStatus());
        }
        // 备注信息
        List<Map<String,Object>> remarkList = new ArrayList<>();
        resultMap.put("remarkList", remarkList);
        List<BillRemark> remarks = detail.getRemarkList();
        if (remarks != null && !remarks.isEmpty()) {
            remarks.forEach(remark -> {
                Map<String,Object> remarkInfo = new HashMap<>();
                remarkInfo.put("content", remark.getRemark());
                remarkInfo.put("createTime", DateTimeUtils.formatDatetime1(remark.getCreateTime()));
                remarkList.add(remarkInfo);
            });
        }
        // 操作日志信息
        List<Map<String,Object>> opLogList = new ArrayList<>();
        resultMap.put("opLogList", opLogList);
        List<BillOpLog> opLogs = detail.getOpLogList();
        if (opLogs != null && !opLogs.isEmpty()) {
            opLogs.forEach(opLog -> {
                Map<String,Object> opLogInfo = new HashMap<>();
                opLogInfo.put("createTime", opLog.getCreateTime().getTime());
                opLogInfo.put("opUser", SystemContext.getUserById(opLog.getUserId()).getUserName());
                SysConfig config = sysConfigService.getSysConfigById(ISysConfigService.DEAL_TYPE, opLog.getDealType());
                opLogInfo.put("opContent", config.getName());
                opLogInfo.put("nextDeal", opLog.getNextDeal());
                opLogList.add(opLogInfo);
            });
        }
        return CommonUtils.buildSuccessResp(resultMap);
    }

    @Override
    public Response count(String type) {
        if (StringUtils.isEmpty(type)) {
            CommonUtils.throwException(ExceptionCode.PARAM_ERROR);
        }
        Request request = new Request();
        User currentUser = SubjectUtils.getCurrentUser();
        request.put("userId", currentUser.getId());
//        request.put("createOrgId", currentUser.getOrganization().getId());
        Map<String,Integer> result = new HashMap<>(2);
        if ("upcoming".equals(type)) {
            request.put("billStatus", "2,4");
            int count = billMapper.selectUpcomingCount(request);
            result.put("count", count);
        }
        return CommonUtils.buildSuccessResp(result);
    }

    @Override
    @Transactional
    public Response changeStatus(Request request) {
        //1. 如果当前工单是在审核中，那么操作人必须是当前处理人
        //2. 如果当前工单为待审核，那么更改当前工单状态为审核中，当前处理人为操作人，FIXME 处理部门为空
        //处理操作与审核操作步骤一致
        //校验流程需要保证阻塞同步
        // 必须有审核工单权限
        if(!SubjectUtils.hasPermissions("WorkOrder:approval")){
            CommonUtils.throwException(ResponseCode.NO_PERMISSION);
        }
        int type = request.getInt("type");
        long billId = request.getLong("billId");
        User currentUser = SubjectUtils.getCurrentUser();
        synchronized (BillService.class) {
            Bill bill = billMapper.selectByPrimaryKey(billId);
            if (type == 1) {// 更改为审核中状态
                // 如果工单当前状态为审核中，需要检查工单当前处理人是否是当前操作人
                if (BillStatus.REVIEW_ING.getId().equals(bill.getBillStatusId())) {
                    if(!currentUser.getId().equals(bill.getDealUserId())){
                        logger.warn("工单：{} 已处于审核中状态，处理人id:{}，与当前操作人:{} 不符，禁止操作",bill.getBillNo(),bill.getDealUserId(),currentUser.getId());
                        CommonUtils.throwException("error","当前工单已处于审核中，您无法操作");
                    }
                    // 如果工单当前状态为审核中，并且处理人就是当前操作人，那么拉取审核暂存信息返回
                    BillApprInfo tempBillApprInfo = billApprInfoMapper.selectReviewingByBillId(billId);
                    if(tempBillApprInfo != null){// 存在暂存信息，返回
                        return CommonUtils.buildSuccessResp(tempBillApprInfo);
                    }
                }

                // 如果当前工单状态是待审核，则更改状态为审核中，当前处理人为操作人，处理部门为空
                Bill preReview = new Bill();
                preReview.setId(billId);
                preReview.setBillStatusId(BillStatus.REVIEW_ING.getId());
                preReview.setDealUserId(currentUser.getId());
                preReview.setDealOrgId(0L);
                billMapper.updateByPrimaryKeySelective(preReview);
            }
            if (type == 2) {// 更改为处理中状态
                // 如果工单当前状态为处理中，需要检查工单当前处理人是否是当前操作人
                if (BillStatus.HANDLER_ING.getId().equals(bill.getBillStatusId())) {
                    if(!currentUser.getId().equals(bill.getDealUserId())){
                        logger.warn("工单：{} 已处于处理中状态，处理人id:{}，与当前操作人:{} 不符，禁止操作",bill.getBillNo(),bill.getDealUserId(),currentUser.getId());
                        CommonUtils.throwException("error","当前工单已处于处理中，您无法操作");
                    }
                    // 如果工单当前状态为审核中，并且处理人就是当前操作人，那么拉取审核暂存信息返回
                    BillDealInfo tempBillDealInfo = billDealInfoMapper.selectHandleingByBillId(billId);
                    if(tempBillDealInfo != null){// 存在暂存信息，返回
                        return CommonUtils.buildSuccessResp(tempBillDealInfo);
                    }
                }

                // 如果当前工单状态是待处理，则更改状态为审核中，当前处理人为操作人，处理部门为空
                Bill preHandler = new Bill();
                preHandler.setId(billId);
                preHandler.setBillStatusId(BillStatus.HANDLER_ING.getId());
                preHandler.setDealUserId(currentUser.getId());
                preHandler.setDealOrgId(0L);
                billMapper.updateByPrimaryKeySelective(preHandler);
            }
        }
        clearCurrentPageCache();
        return CommonUtils.buildSuccessResp();
    }

    @Override
    public Response updateAttach(Request request) {
        // type=1表示工单附件 2表示审核附件 3表示处理附件
        int type = request.getInt("type");
        Map<String,Object> data = request.get("data", Map.class);
        if (type == 0 || data == null) {
            CommonUtils.throwIllegalParamsException("must contains type&data params");
        }
        Long id = Long.valueOf(data.get("id")+"");
        String newAttachUrl = data.get("url")+"";
        if (type == 1) {
            billMapper.updateAttachUrlById(id,newAttachUrl);
        }
        if (type == 2) {
            billApprInfoMapper.updateAttachUrlById(id,newAttachUrl);
        }
        if (type == 3) {
            billDealInfoMapper.updateAttachUrlById(id,newAttachUrl);
        }
        return CommonUtils.buildSuccessResp();
    }
}
