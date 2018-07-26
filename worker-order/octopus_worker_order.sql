/*
Navicat MySQL Data Transfer

Source Server         : xjyc-svr1
Source Server Version : 50722
Source Host           : xjyc-svr1:3306
Source Database       : octopus_worker_order

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-26 14:38:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T_BILL_APPROVAL_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_BILL_APPROVAL_INFO`;
CREATE TABLE `T_BILL_APPROVAL_INFO` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILL_ID` bigint(20) DEFAULT NULL COMMENT '工单ID',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '审批人',
  `USER_DEPT_ID` bigint(20) DEFAULT NULL COMMENT '审批人所在部门',
  `DESC` varchar(500) DEFAULT NULL COMMENT '审批备注信息',
  `RESULT` char(1) DEFAULT '0' COMMENT '审批结果：1 通过 2 不通过 默认0',
  `USED_TIME` varchar(32) DEFAULT NULL COMMENT '审批用时 距离单据创建结束时间',
  `ATTACH_URL` varchar(1000) DEFAULT NULL COMMENT '附件URL',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `STATUS` char(1) DEFAULT '0' COMMENT '条目状态 默认0 1表示暂存',
  PRIMARY KEY (`ID`),
  KEY `IDX_BILL_APPR_ONE` (`BILL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单审批信息';

-- ----------------------------
-- Records of T_BILL_APPROVAL_INFO
-- ----------------------------

-- ----------------------------
-- Table structure for T_BILL_DEAL_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_BILL_DEAL_INFO`;
CREATE TABLE `T_BILL_DEAL_INFO` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILL_ID` bigint(20) DEFAULT NULL COMMENT '工单ID',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '处理人',
  `USER_DEPT_ID` bigint(20) DEFAULT NULL COMMENT '处理人所在部门',
  `DESC` varchar(500) DEFAULT NULL COMMENT '处理备注信息',
  `RESULT` char(1) DEFAULT '1' COMMENT '处理结果 默认通过1',
  `PROBLEM` bigint(20) DEFAULT '0' COMMENT '问题定性 对应sysConfig配置 小问题  中问题  大问题',
  `SUBPROBLEM` bigint(20) DEFAULT '0' COMMENT '问题细分',
  `RE_VISIT` char(1) DEFAULT '0' COMMENT '是否回访 1 是 默认0 否',
  `RE_VISIT_TIME` timestamp NULL DEFAULT NULL COMMENT '回访时间',
  `USED_TIME` varchar(32) DEFAULT NULL COMMENT '处理用时 距离单据审批结束的时间',
  `ATTACH_URL` varchar(1000) DEFAULT NULL COMMENT '附件URL',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '处理时间',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `STATUS` char(1) DEFAULT '0' COMMENT '条目状态 默认0 1表示暂存',
  PRIMARY KEY (`ID`),
  KEY `IDX_BILL_DEAL_ONE` (`BILL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单处理信息';

-- ----------------------------
-- Records of T_BILL_DEAL_INFO
-- ----------------------------

-- ----------------------------
-- Table structure for T_BILL_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_BILL_INFO`;
CREATE TABLE `T_BILL_INFO` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_ORG` bigint(20) DEFAULT NULL COMMENT '工单所属部门，部门间工单隔离',
  `PARENT_BILL_NO` varchar(32) DEFAULT NULL COMMENT '父级工单号',
  `BILL_NO` varchar(32) DEFAULT NULL COMMENT '工单编号，唯一性',
  `BILL_STATUS` bigint(20) DEFAULT NULL COMMENT '工单状态，关联系统配置表的工单状态配置项',
  `URGENT_LEVEL` bigint(20) DEFAULT NULL COMMENT '紧急程度，关联系统配置表的紧急程度配置项',
  `BILL_TYPE` bigint(20) DEFAULT NULL COMMENT '工单分类，关联工单分类表配置',
  `BIZ_TYPE` bigint(20) DEFAULT NULL COMMENT '业务分类，关联业务分类表配置',
  `BIZ_DETAIL_TYPE` bigint(20) DEFAULT NULL COMMENT '业务细分，关联业务细分表配置项',
  `BILL_SOURCE` bigint(20) DEFAULT NULL COMMENT '下单来源，关联系统配置表的工单来源',
  `ANSWER_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '应回复时间',
  `CUSTOMER_ID` bigint(20) DEFAULT NULL COMMENT '对应客户，关联客户表ID',
  `CONNECTOR` varchar(32) DEFAULT NULL COMMENT '本工单联系人',
  `CONNECTOR_PHONE` varchar(32) DEFAULT NULL COMMENT '联系人电话',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述信息（受理信息）',
  `ATTACH_URL` varchar(1000) DEFAULT NULL COMMENT '附件URL',
  `STATUS` char(1) DEFAULT '0' COMMENT '是否有效 默认0有效 1无效',
  `CREATE_USER` bigint(20) DEFAULT NULL COMMENT '创建人，工单受理人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '工单创建时间，即前台显示的受理时间',
  `DEAL_ORG` bigint(20) DEFAULT NULL COMMENT '当前处理机构(部门)，需要随流程更改而及时更新，只有当用户在创建工单的部门或当前处理所在部门时才可见',
  `DEAL_USER` bigint(20) DEFAULT NULL COMMENT '当前处理人，需要随流程更改而及时更新',
  `PRESS_TIMES` int(5) DEFAULT '0' COMMENT '催办次数',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '工单更新时间，当前流程启动时间',
  `BELONGS_ORG` bigint(20) DEFAULT '0' COMMENT '所属部门 只有当前部门与下属子部门可见',
  PRIMARY KEY (`ID`),
  KEY `IDX_BILLINFO_ONE` (`BILL_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单表';

-- ----------------------------
-- Records of T_BILL_INFO
-- ----------------------------

-- ----------------------------
-- Table structure for T_BILL_OP_LOG
-- ----------------------------
DROP TABLE IF EXISTS `T_BILL_OP_LOG`;
CREATE TABLE `T_BILL_OP_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILL_ID` bigint(20) DEFAULT NULL COMMENT '工单ID',
  `DEAL_TYPE` bigint(20) DEFAULT NULL COMMENT '处理类型 可用于催办、委托次数统计',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '操作人',
  `USER_DEPT_ID` bigint(20) DEFAULT NULL COMMENT '处理人所在部门',
  `NEXT_DEAL` varchar(32) DEFAULT NULL COMMENT '下一步处理人 可能是具体的人也可能是一个部门',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_BILL_OP_LOG_ONE` (`BILL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单操作日志';

-- ----------------------------
-- Records of T_BILL_OP_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for T_BILL_REMARK
-- ----------------------------
DROP TABLE IF EXISTS `T_BILL_REMARK`;
CREATE TABLE `T_BILL_REMARK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILL_ID` bigint(20) DEFAULT '0' COMMENT '工单号',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '添加人',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '备注时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_BILL_REMARK_ONE` (`BILL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单表';

-- ----------------------------
-- Records of T_BILL_REMARK
-- ----------------------------

-- ----------------------------
-- Table structure for T_BILL_TYPE
-- ----------------------------
DROP TABLE IF EXISTS `T_BILL_TYPE`;
CREATE TABLE `T_BILL_TYPE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(32) DEFAULT NULL COMMENT '分类编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '分类名',
  `STATUS` char(1) DEFAULT '0' COMMENT '是否启用 默认0启用',
  `ORDER` int(3) DEFAULT '0' COMMENT '显示顺序',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_BT_NAME` (`NAME`),
  UNIQUE KEY `IDX_BT_ORDER` (`ORDER`) USING BTREE COMMENT '名字和顺序唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单分类';

-- ----------------------------
-- Records of T_BILL_TYPE
-- ----------------------------

-- ----------------------------
-- Table structure for T_BIZ_DETAIL_TYPE
-- ----------------------------
DROP TABLE IF EXISTS `T_BIZ_DETAIL_TYPE`;
CREATE TABLE `T_BIZ_DETAIL_TYPE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BIZ_TYPE_ID` bigint(20) DEFAULT NULL COMMENT '所属业务分类ID',
  `CODE` varchar(32) DEFAULT NULL COMMENT '分类编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '分类名',
  `STATUS` char(1) DEFAULT '0' COMMENT '是否启用 默认0启用',
  `ORDER` int(3) DEFAULT '0' COMMENT '显示顺序',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_BZDT_NAME` (`BIZ_TYPE_ID`,`NAME`),
  UNIQUE KEY `IDX_BZDT_ORDER` (`BIZ_TYPE_ID`,`ORDER`) COMMENT '顺序唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务细分 - 从属于业务分类 三级联动';

-- ----------------------------
-- Records of T_BIZ_DETAIL_TYPE
-- ----------------------------

-- ----------------------------
-- Table structure for T_BIZ_TYPE
-- ----------------------------
DROP TABLE IF EXISTS `T_BIZ_TYPE`;
CREATE TABLE `T_BIZ_TYPE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BILL_TYPE_ID` bigint(20) DEFAULT NULL COMMENT '所属工单分类ID',
  `CODE` varchar(32) DEFAULT NULL COMMENT '分类编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '分类名',
  `STATUS` char(1) DEFAULT '0' COMMENT '是否启用 默认0启用',
  `ORDER` int(3) DEFAULT '0' COMMENT '显示顺序',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_BZT_NAME` (`BILL_TYPE_ID`,`NAME`),
  UNIQUE KEY `IDX_BZT_ORDER` (`BILL_TYPE_ID`,`ORDER`) COMMENT '顺序唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务分类 - 从属于工单分类 二级联动';

-- ----------------------------
-- Records of T_BIZ_TYPE
-- ----------------------------

-- ----------------------------
-- Table structure for T_CUSTOMER_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_CUSTOMER_INFO`;
CREATE TABLE `T_CUSTOMER_INFO` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) NOT NULL COMMENT '客户姓名',
  `SEX` char(1) DEFAULT '1' COMMENT '性别 1男 0女 默认男',
  `PHONENO` varchar(32) NOT NULL COMMENT '来电号码',
  `CONNECT_USER` varchar(32) DEFAULT NULL COMMENT '联系人',
  `CONNECT_USER_PHONE` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `CALL_TIME` timestamp NULL DEFAULT NULL COMMENT '来电时间',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_CUSINFO_ONE` (`NAME`,`PHONENO`,`CONNECT_USER`,`CONNECT_USER_PHONE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息表';

-- ----------------------------
-- Records of T_CUSTOMER_INFO
-- ----------------------------

-- ----------------------------
-- Table structure for T_SYS_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `T_SYS_CONFIG`;
CREATE TABLE `T_SYS_CONFIG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PID` bigint(20) DEFAULT '0' COMMENT '父级id',
  `TYPE` varchar(32) DEFAULT NULL COMMENT '配置类型',
  `CODE` varchar(32) DEFAULT NULL COMMENT '配置编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '配置名',
  `ORDER` int(3) DEFAULT NULL COMMENT '显示顺序',
  `STATUS` char(1) DEFAULT '0' COMMENT '是否启用 默认0启用',
  `DESC` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_SC_TYPENAME` (`TYPE`,`NAME`),
  UNIQUE KEY `IDX_SYSCONF_ORDER` (`TYPE`,`ORDER`) COMMENT '顺序唯一'
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of T_SYS_CONFIG
-- ----------------------------
INSERT INTO `T_SYS_CONFIG` VALUES ('1', '0', 'bill-status', 'pre-review', '待审核', '1', '0', '工单状态', '2018-06-01 15:24:15');
INSERT INTO `T_SYS_CONFIG` VALUES ('2', '0', 'bill-status', 'review-ing', '审核中', '2', '0', null, '2018-06-01 15:24:24');
INSERT INTO `T_SYS_CONFIG` VALUES ('3', '0', 'bill-status', 'pre-handler', '待处理', '3', '0', null, '2018-06-01 15:24:24');
INSERT INTO `T_SYS_CONFIG` VALUES ('4', '0', 'bill-status', 'handler-ing', '处理中', '4', '0', null, '2018-06-01 15:24:25');
INSERT INTO `T_SYS_CONFIG` VALUES ('5', '0', 'bill-status', 'post-handler', '已处理', '5', '0', null, '2018-06-01 15:24:25');
INSERT INTO `T_SYS_CONFIG` VALUES ('6', '0', 'bill-status', 'closed', '已关闭', '6', '0', null, '2018-06-01 15:24:26');
INSERT INTO `T_SYS_CONFIG` VALUES ('8', '0', 'bill-status', 'backed', '已退回', '8', '0', null, '2018-06-01 15:24:27');
INSERT INTO `T_SYS_CONFIG` VALUES ('10', '0', 'bill-source', 'phone', '电话', '1', '0', '工单来源', '2018-06-01 15:26:33');
INSERT INTO `T_SYS_CONFIG` VALUES ('11', '0', 'bill-source', 'online', '在线客服', '2', '0', null, '2018-06-01 15:26:37');
INSERT INTO `T_SYS_CONFIG` VALUES ('12', '0', 'bill-source', 'email', '邮箱', '3', '0', null, '2018-06-01 15:26:45');
INSERT INTO `T_SYS_CONFIG` VALUES ('13', '0', 'bill-source', 'wechat', '微信', '4', '0', null, '2018-06-01 15:26:47');
INSERT INTO `T_SYS_CONFIG` VALUES ('14', '0', 'bill-source', 'other', '其它', '5', '0', null, '2018-06-01 15:26:50');
INSERT INTO `T_SYS_CONFIG` VALUES ('15', '0', 'urgent-level', 'one', '一般', '1', '0', '紧急程度', '2018-06-01 15:30:25');
INSERT INTO `T_SYS_CONFIG` VALUES ('16', '0', 'urgent-level', 'two', '紧急', '2', '0', null, '2018-06-01 15:30:29');
INSERT INTO `T_SYS_CONFIG` VALUES ('17', '0', 'urgent-level', 'three', '非常紧急', '3', '0', null, '2018-06-01 15:30:31');
INSERT INTO `T_SYS_CONFIG` VALUES ('19', '0', 'problem-prop', 'one', '大问题', '1', '0', '工单问题定性', '2018-06-01 16:06:30');
INSERT INTO `T_SYS_CONFIG` VALUES ('20', '0', 'problem-prop', 'two', '中问题', '2', '0', null, '2018-06-01 16:06:33');
INSERT INTO `T_SYS_CONFIG` VALUES ('21', '0', 'problem-prop', 'three', '小问题', '3', '0', null, '2018-06-01 16:06:45');
INSERT INTO `T_SYS_CONFIG` VALUES ('22', '0', 'approval-result', 'pass', '通过', '1', '0', '审批结果', '2018-06-01 16:24:38');
INSERT INTO `T_SYS_CONFIG` VALUES ('23', '0', 'approval-result', 'no-pass', '不通过', '2', '0', null, '2018-06-01 16:24:41');
INSERT INTO `T_SYS_CONFIG` VALUES ('24', '0', 'bill-deal-type', 'create', '创建', '1', '0', '工单操作内容', '2018-06-01 17:13:13');
INSERT INTO `T_SYS_CONFIG` VALUES ('25', '0', 'bill-deal-type', 'approval', '审批', '2', '0', null, '2018-06-01 17:13:16');
INSERT INTO `T_SYS_CONFIG` VALUES ('26', '0', 'bill-deal-type', 'weituo', '委托', '3', '0', null, '2018-06-01 17:13:18');
INSERT INTO `T_SYS_CONFIG` VALUES ('27', '0', 'bill-deal-type', 'deal', '处理', '4', '0', null, '2018-06-01 17:13:23');
INSERT INTO `T_SYS_CONFIG` VALUES ('28', '0', 'bill-deal-type', 'save', '暂存', '5', '0', null, '2018-06-01 17:13:31');
INSERT INTO `T_SYS_CONFIG` VALUES ('29', '0', 'bill-deal-type', 'close', '关闭', '6', '0', null, '2018-06-01 17:13:35');
INSERT INTO `T_SYS_CONFIG` VALUES ('30', '0', 'bill-deal-type', 'free', '释放', '7', '0', null, '2018-06-08 11:42:50');
INSERT INTO `T_SYS_CONFIG` VALUES ('31', '0', 'bill-deal-type', 'back', '回退', '8', '0', null, '2018-06-08 19:24:24');
INSERT INTO `T_SYS_CONFIG` VALUES ('32', '0', 'bill-deal-type', 'press', '催办', '9', '0', null, '2018-06-08 19:24:42');
INSERT INTO `T_SYS_CONFIG` VALUES ('35', '19', 'problem-prop19', null, '严重性问题', '1', '0', null, '2018-06-26 18:30:26');
