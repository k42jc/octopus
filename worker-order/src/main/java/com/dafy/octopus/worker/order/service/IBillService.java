package com.dafy.octopus.worker.order.service;

import com.dafy.common.po.Response;
import com.dafy.octopus.web.core.domain.Request;

/**
 * 工单相关处理 <br>
 *
 * 云贷客服只有云贷工单的权限
 * 呼叫中心客服只有呼叫中心工单权限
 * Created by liaoxudong
 * Date:2018/6/1
 */

public interface IBillService {

    /**
     * 获取工单列表
     * @param request 请求实体
     *        -userId 当前登录用户ID
     *        -createTimeStart 受理时间开始
     *        -createTimeEnd 受理时间结束
     *        -condition 查询条件
     *        -urgent 紧急程度
     *        -billType 工单分类
     *        -bizType 业务分类
     *        -bizDetailType 业务详细分类
     *        -billStatus 工单处理状态 多个传列表
     *
     * @return 符合当前用户权限的工单信息
     *
     */
    Response list(Request request);


    /**
     * 创建工单
     * @param request
     *        -cusInfo 客户信息
     *          -cusName 客户姓名
     *          -cusSex 客户性别 0 男 1 女
     *          -cusCallInPhone 客户来电号码
     *          -cusCallInTime 客户来电时间
     *        -billInfo 工单类型信息
     *          -parentBillNo 父级工单号 没有传空
     *          -billType 工单类型
     *          -billBizType 工单业务分类
     *          -billBizDetailType 工单业务细分类
     *          -billSource 工单来源
     *          -connector 联系人
     *          -connectorPhone 联系电话
     *          -eLevel 紧急程度
     *          -answerTime 应回复时间
     *          -desc 受理信息(描述)
     *          -attachURL 附件url
     * @return
     */
    Response create(Request request);


    /**
     * 工单状态处理
     * @param request
     *          -billId 工单ID
     *          -status 工单状态
     *          -nextHandler 下一处理人/部门
     * @return
     */
    Response handler(Request request);

    /**
     * 导出选中
     * @param request
     * @return 返回二进制流让浏览器弹出下载
     */
    Response export(Request request);


    /**
     * 释放、委托、回退操作
     * @param request
     * @return
     */
    Response updateHandlerMan(Request request);

    /**
     * 添加备注
     * @param request
     * @return
     */
    Response addRemark(Request request);

    /**
     * 获取工单详情
     * @param billId
     * @return
     */
    Response detail(Long billId);

    /**
     * 获取数量
     * @param type
     * @return
     */
    Response count(String type);

    /**
     * 工单状态更改
     *
     * //1. 如果当前工单是在审核中，那么操作人必须是当前处理人
     //2. 如果当前工单为待审核，那么更改当前工单状态为审核中，当前处理人为操作人，处理部门为空
     //处理操作与审核操作步骤一致
     //校验流程需要保证阻塞同步
     * @param request
     * @return
     */
    Response changeStatus(Request request);

    /**
     * 更新附件信息
     * @param request
     * @return
     */
    Response updateAttach(Request request);
}
