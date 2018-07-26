/*
Navicat MySQL Data Transfer

Source Server         : xjyc-svr1
Source Server Version : 50722
Source Host           : xjyc-svr1:3306
Source Database       : octopus_base

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-26 14:38:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T_APP
-- ----------------------------
DROP TABLE IF EXISTS `T_APP`;
CREATE TABLE `T_APP` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_key` varchar(64) DEFAULT NULL COMMENT '应用唯一标志',
  `name` varchar(32) DEFAULT NULL COMMENT '应用名',
  `app_secret` varchar(512) DEFAULT NULL COMMENT '应用密钥',
  `status` char(1) DEFAULT '0' COMMENT '启用状态',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统应用表';

-- ----------------------------
-- Records of T_APP
-- ----------------------------
INSERT INTO `T_APP` VALUES ('1', 'd794108c-abfb-4940-920d-7256946022e5', '八爪鱼客服系统-认证中心', null, '0', '基础服务', '2018-04-23 15:34:12', null);
INSERT INTO `T_APP` VALUES ('2', 'a25csd1c-abfb-4940-920d-7256946022e6', '手机租赁', null, '0', '扩展客户端', '2018-04-23 15:34:50', null);

-- ----------------------------
-- Table structure for T_MENU
-- ----------------------------
DROP TABLE IF EXISTS `T_MENU`;
CREATE TABLE `T_MENU` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_code` varchar(32) DEFAULT NULL,
  `m_name` varchar(32) DEFAULT NULL,
  `m_icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标',
  `m_url` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `m_order` int(11) DEFAULT NULL COMMENT '菜单顺序',
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of T_MENU
-- ----------------------------
INSERT INTO `T_MENU` VALUES ('1', 'CustomerMgr', '客户管理', 'tree', '', '18', '1', '', '2018-01-30 14:58:26', null);
INSERT INTO `T_MENU` VALUES ('2', 'CustomerList', '客户列表', 'money', 'CustomerList', '1', '1', null, '2018-01-30 15:06:29', null);
INSERT INTO `T_MENU` VALUES ('3', 'CustomInfo', '客户信息查询', 'table', 'CustomInfo', '1', '2', '', '2018-01-30 15:08:22', null);
INSERT INTO `T_MENU` VALUES ('4', 'CustomerOpLog', '客户信息操作记录', 'user', 'CustomerOpLog', '1', '3', null, '2018-01-30 15:08:59', null);
INSERT INTO `T_MENU` VALUES ('11', 'SysSetting', '系统配置', 'password', '', '17', '3', '', '2018-02-07 14:33:30', null);
INSERT INTO `T_MENU` VALUES ('12', 'CommunicationSummerySetting', '客服记录选项设置', 'table', 'CommunicationSummerySetting', '1', '1', '', '2018-03-05 13:48:12', null);
INSERT INTO `T_MENU` VALUES ('13', 'DepartmentManage', '部门管理', 'role', 'DepartmentManage', '11', '2', '', '2018-04-02 17:40:38', null);
INSERT INTO `T_MENU` VALUES ('14', 'CharManage', '权限管理', 'user', 'CharManage', '11', '3', '', '2018-04-03 17:35:10', null);
INSERT INTO `T_MENU` VALUES ('15', 'UserManage', '成员管理', 'role', 'UserManage', '11', '4', '', '2018-04-03 17:37:37', null);
INSERT INTO `T_MENU` VALUES ('16', 'OpLog', '系统操作日志', 'user', 'OpLog', '11', '5', ' ', '2018-04-26 14:41:15', null);
INSERT INTO `T_MENU` VALUES ('17', 'BaseConfig', '基础配置', 'static/img/nav6.png', '', null, '3', '系统配置', '2018-06-13 11:19:02', null);
INSERT INTO `T_MENU` VALUES ('18', 'MobileRental', '手机租赁', 'static/img/nav0.png', '', null, '2', '手机租赁系统', '2018-06-13 11:19:23', null);
INSERT INTO `T_MENU` VALUES ('19', 'WorkOrder', '工单系统', 'static/img/nav2.png', '', null, '1', '工单系统', '2018-06-13 11:19:58', null);
INSERT INTO `T_MENU` VALUES ('20', 'WoManager', '工单管理', null, null, '19', '1', null, '2018-06-13 11:20:49', null);
INSERT INTO `T_MENU` VALUES ('21', 'WoConfig', '工单设置', null, null, '19', '2', null, '2018-06-13 11:20:53', null);
INSERT INTO `T_MENU` VALUES ('22', 'WorkOrderList', '工单列表', null, null, '20', '1', null, '2018-06-13 11:21:07', null);
INSERT INTO `T_MENU` VALUES ('23', 'MyWorkOrder', '我的工单', null, null, '20', '2', null, '2018-06-13 11:21:11', null);
INSERT INTO `T_MENU` VALUES ('24', 'ClassifySet', '分类设置', null, null, '21', '1', null, '2018-06-13 11:21:18', null);
INSERT INTO `T_MENU` VALUES ('25', 'Sysconfig', '配置项设置', null, null, '21', '2', null, '2018-06-13 11:21:32', null);

-- ----------------------------
-- Table structure for T_OP_LOG
-- ----------------------------
DROP TABLE IF EXISTS `T_OP_LOG`;
CREATE TABLE `T_OP_LOG` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `op_user_id` bigint(20) DEFAULT '0' COMMENT '操作人',
  `op_no` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作编号',
  `op_type` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作类型',
  `op_ip` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作ip',
  `desc` text COLLATE utf8_unicode_ci COMMENT '描述信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1982 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of T_OP_LOG
-- ----------------------------
INSERT INTO `T_OP_LOG` VALUES ('1041', '45', '390', '2', '10.34.2.12', '退出登录成功', '2018-05-16 17:09:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1042', '1', '391', '1', '10.34.2.12', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-16 17:09:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1043', '1', '392', '1', '10.34.2.30', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-16 17:16:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1044', '1', '393', '11', '10.34.2.30', '编辑用户成功【{\"userId\":1,\"userCode\":\"admin\",\"username\":\"系统管理员\"}】', '2018-05-16 17:17:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1045', '1', '394', '1', '10.34.2.28', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-16 17:46:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1046', '1', '395', '1', '10.34.2.12', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-16 17:59:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1047', '1', '396', '14', '10.34.2.12', '启用/禁用用户成功【id=3】', '2018-05-16 18:00:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1048', '1', '397', '14', '10.34.2.12', '启用/禁用用户成功【id=3】', '2018-05-16 18:00:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1049', '3', '398', '1', '10.34.2.30', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-17 11:14:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1050', '3', '399', '1', '10.34.2.30', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-17 12:19:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1051', '52', '400', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 13:26:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1052', '47', '401', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 13:47:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1053', '1', '402', '1', '10.34.2.30', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-17 14:18:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1054', '3', '403', '1', '10.34.2.28', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-17 16:31:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1055', '3', '404', '1', '10.34.2.4', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-17 16:36:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1056', '1', '405', '1', '10.34.2.12', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-17 16:38:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1057', '3', '406', '1', '10.34.2.3', '登录【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberMe\":true}】', '2018-05-17 16:41:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1058', '3', '407', '1', '10.34.2.3', '登录【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberMe\":true}】', '2018-05-17 16:41:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1059', '1', '408', '2', '10.34.2.12', '退出登录成功', '2018-05-17 16:43:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1060', '1', '409', '1', '10.34.2.12', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-17 16:43:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1061', '1', '410', '2', '10.34.2.12', '退出登录成功', '2018-05-17 16:44:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1062', '3', '411', '1', '10.34.2.30', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-17 17:17:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1063', '47', '412', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 17:35:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1064', '52', '413', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 17:35:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1065', '47', '414', '2', '10.33.2.37', '退出登录成功', '2018-05-17 17:35:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1066', '47', '415', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 17:35:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1067', '0', '416', '1', '10.33.2.96', '60091:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-05-17 17:36:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1068', '51', '417', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 17:37:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1069', '3', '418', '2', '10.34.2.30', '退出登录成功', '2018-05-17 17:37:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1070', '51', '419', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 17:37:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1071', '1', '420', '1', '10.34.2.30', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-17 17:38:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1072', '51', '421', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-17 17:40:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1073', '52', '422', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-18 08:55:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1074', '51', '423', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-18 08:58:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1075', '53', '424', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-18 13:02:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1076', '53', '425', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-18 14:44:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1077', '53', '426', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-18 15:30:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1078', '51', '427', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-18 17:06:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1079', '52', '428', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-19 09:02:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1080', '51', '429', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-19 09:02:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1081', '52', '430', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-20 09:05:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1082', '47', '431', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-20 09:30:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1083', '47', '432', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-20 11:31:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1084', '47', '433', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-20 13:42:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1085', '53', '434', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-20 14:04:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1086', '52', '435', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-21 09:04:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1087', '0', '436', '1', '10.8.2.63', 'admin:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-05-21 10:15:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1088', '0', '437', '1', '10.8.2.63', 'admin:登录【13:用户名或密码错误，剩余重试次数：3】', '2018-05-21 10:15:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1089', '0', '438', '1', '10.8.2.63', 'admin:登录【13:用户名或密码错误，剩余重试次数：2】', '2018-05-21 10:15:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1090', '1', '439', '1', '10.8.2.63', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 10:16:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1091', '47', '440', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-21 11:10:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1092', '1', '441', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 11:32:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1093', '1', '442', '1', '10.34.2.24', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 11:43:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1094', '1', '443', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 11:51:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1095', '1', '444', '14', '10.34.2.5', '启用/禁用用户成功【id=3】', '2018-05-21 11:58:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1096', '1', '445', '14', '10.34.2.5', '启用/禁用用户成功【id=3】', '2018-05-21 11:58:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1097', '1', '446', '2', '10.34.2.5', '退出登录成功', '2018-05-21 12:09:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1098', '1', '447', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 12:09:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1099', '1', '448', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 12:09:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1100', '51', '449', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-21 13:29:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1101', '47', '450', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-21 13:32:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1102', '52', '451', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-21 13:34:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1103', '1', '452', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 13:46:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1104', '1', '453', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 13:53:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1105', '1', '454', '1', '10.34.2.24', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 14:38:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1106', '1', '455', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 16:18:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1107', '1', '456', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 17:01:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1108', '1', '457', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-21 18:27:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1109', '1', '458', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-22 10:18:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1110', '47', '459', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 10:48:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1111', '47', '460', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 11:49:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1112', '1', '461', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-22 13:52:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1113', '1', '462', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-22 14:32:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1114', '47', '463', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 14:42:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1115', '0', '464', '1', '10.33.13.9', 'test:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-05-22 15:11:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1116', '0', '465', '1', '10.33.13.9', 'test:登录【13:用户名或密码错误，剩余重试次数：3】', '2018-05-22 15:11:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1117', '0', '466', '1', '10.33.13.9', 'test:登录【13:用户名或密码错误，剩余重试次数：2】', '2018-05-22 15:11:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1118', '0', '467', '1', '10.33.13.9', '60030:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-05-22 15:13:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1119', '1', '468', '1', '10.33.13.9', '登录【{\"username\":\"admin\",\"password\":\"15fa1b9ab069546bb8d32f0efcecaaf8\",\"rememberMe\":true}】', '2018-05-22 15:20:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1120', '1', '469', '1', '10.33.13.9', '登录【{\"username\":\"admin\",\"password\":\"15fa1b9ab069546bb8d32f0efcecaaf8\",\"rememberMe\":true}】', '2018-05-22 15:21:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1121', '1', '470', '1', '10.33.13.9', '登录【{\"username\":\"test\",\"password\":\"15fa1b9ab069546bb8d32f0efcecaaf8\",\"rememberMe\":true}】', '2018-05-22 15:21:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1122', '53', '471', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 15:22:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1123', '1', '472', '1', '10.33.13.9', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 15:22:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1124', '1', '473', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-22 15:24:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1125', '0', '474', '1', '10.33.13.9', 'test:登录【14:账户已被锁定，请20分钟后重试】', '2018-05-22 15:34:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1126', '0', '475', '1', '10.33.13.9', 'test:登录【14:账户已被锁定，请20分钟后重试】', '2018-05-22 15:34:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1127', '46', '476', '1', '10.33.13.9', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 15:35:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1128', '53', '477', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-22 15:59:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1129', '1', '478', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-22 16:45:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1130', '52', '479', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-23 09:01:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1131', '1', '480', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 10:22:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1132', '1', '481', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 14:46:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1133', '53', '482', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-23 14:54:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1134', '1', '483', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 14:57:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1135', '1', '484', '1', '10.34.2.33', '登录【{\"username\":\"admin\",\"password\":\"bbf41fa1076a380998e478962b004d62\",\"rememberMe\":true}】', '2018-05-23 15:04:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1136', '1', '485', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 15:04:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1137', '1', '486', '1', '10.34.2.33', '登录【{\"username\":\"admin\",\"password\":\"bbf41fa1076a380998e478962b004d62\",\"rememberMe\":true}】', '2018-05-23 15:06:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1138', '1', '487', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 15:06:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1139', '0', '488', '1', '10.34.2.33', 'in:登录【15:用户不存在】', '2018-05-23 15:07:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1140', '1', '489', '1', '10.34.2.33', '登录【{\"username\":\"admin\",\"password\":\"bbf41fa1076a380998e478962b004d62\",\"rememberMe\":true}】', '2018-05-23 15:07:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1141', '1', '490', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 15:07:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1142', '1', '491', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 15:43:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1143', '1', '492', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 16:29:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1144', '1', '493', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-23 17:05:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1145', '3', '494', '1', '10.34.2.33', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-24 10:51:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1146', '3', '495', '2', '10.34.2.33', '退出登录成功', '2018-05-24 11:08:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1147', '1', '496', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-24 11:08:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1148', '1', '497', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-24 11:26:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1149', '52', '498', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-24 13:45:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1150', '1', '499', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-24 14:50:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1151', '1', '500', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-24 16:24:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1152', '1', '501', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-24 16:56:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1153', '3', '502', '1', '10.9.29.29', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-24 17:31:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1154', '3', '503', '2', '10.9.29.29', '退出登录成功', '2018-05-24 17:32:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1155', '3', '504', '1', '10.9.29.29', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-24 17:32:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1156', '3', '505', '2', '10.9.29.29', '退出登录成功', '2018-05-24 17:32:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1157', '1', '506', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-24 17:56:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1158', '52', '507', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-25 09:02:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1159', '1', '508', '1', '10.34.3.62', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-25 10:00:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1160', '53', '509', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-25 11:24:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1161', '53', '510', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-25 14:34:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1162', '52', '511', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-26 09:07:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1163', '46', '512', '1', '10.33.13.9', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-28 09:37:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1164', '1', '513', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-28 14:48:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1165', '1', '514', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-28 15:02:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1166', '53', '515', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-28 15:47:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1167', '53', '516', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-28 15:58:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1168', '3', '517', '1', '10.9.28.62', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-28 16:48:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1169', '53', '518', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-29 09:49:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1170', '47', '519', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-29 09:50:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1171', '1', '520', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-29 11:05:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1172', '47', '521', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-29 11:13:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1173', '53', '522', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-29 11:52:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1174', '53', '523', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-29 12:43:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1175', '1', '524', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-29 15:04:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1176', '1', '525', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-29 16:10:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1177', '1', '526', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-29 16:15:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1178', '3', '527', '1', '10.9.28.43', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-29 16:16:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1179', '3', '528', '1', '10.9.29.29', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-05-29 16:16:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1180', '47', '529', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-29 16:44:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1181', '0', '530', '1', '10.34.2.33', 'test:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-05-29 17:04:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1182', '1', '531', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-29 17:04:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1183', '53', '532', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-30 09:45:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1184', '47', '533', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-30 10:21:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1185', '1', '534', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 11:23:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1186', '1', '535', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 14:07:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1187', '1', '536', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 14:47:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1188', '1', '537', '2', '10.9.28.53', '退出登录成功', '2018-05-30 14:47:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1189', '1', '538', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 14:47:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1190', '1', '539', '2', '10.9.28.53', '退出登录成功', '2018-05-30 14:50:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1191', '1', '540', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 14:50:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1192', '1', '541', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 15:18:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1193', '1', '542', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-30 17:25:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1194', '53', '543', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-30 17:39:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1195', '52', '544', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-30 18:07:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1196', '1', '545', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-05-31 12:11:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1197', '47', '546', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-05-31 13:26:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1198', '1', '547', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-01 10:14:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1199', '1', '548', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-01 12:13:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1200', '53', '549', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-01 14:17:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1201', '1', '550', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-01 15:48:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1202', '1', '551', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-01 18:14:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1203', '1', '552', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-01 19:10:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1204', '53', '553', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:21:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1205', '1', '554', '1', '10.34.2.3', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-02 13:39:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1206', '53', '555', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:40:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1207', '46', '556', '1', '10.33.2.100', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:54:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1208', '53', '557', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:55:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1209', '51', '558', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:55:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1210', '51', '559', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:56:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1211', '51', '560', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 13:56:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1212', '46', '561', '1', '10.33.2.100', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 14:49:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1213', '53', '562', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 14:50:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1214', '53', '563', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 16:19:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1215', '51', '564', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 16:21:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1216', '53', '565', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 17:12:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1217', '51', '566', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 17:14:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1218', '53', '567', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-02 18:08:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1219', '52', '568', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-03 10:42:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1220', '53', '569', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-03 16:27:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1221', '52', '570', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-04 10:26:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1222', '53', '571', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-04 13:24:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1223', '1', '572', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 13:33:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1224', '53', '573', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-04 15:53:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1225', '1', '574', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 17:22:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1226', '1', '575', '2', '10.9.28.53', '退出登录成功', '2018-06-04 17:22:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1227', '1', '576', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 17:22:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1228', '1', '577', '2', '10.9.28.53', '退出登录成功', '2018-06-04 17:23:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1229', '0', '578', '1', '10.9.28.53', 'test:登录【13:用户名或密码错误，剩余重试次数：3】', '2018-06-04 17:23:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1230', '0', '579', '1', '10.9.28.53', 'test:登录【13:用户名或密码错误，剩余重试次数：2】', '2018-06-04 17:23:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1231', '0', '580', '1', '10.9.28.53', 'test:登录【13:用户名或密码错误，剩余重试次数：1】', '2018-06-04 17:23:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1232', '3', '581', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-06-04 17:23:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1233', '3', '582', '2', '10.9.28.53', '退出登录成功', '2018-06-04 17:27:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1234', '0', '583', '1', '10.9.28.53', 'admin:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-06-04 17:27:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1235', '0', '584', '1', '10.9.28.53', 'admin:登录【13:用户名或密码错误，剩余重试次数：3】', '2018-06-04 17:28:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1236', '0', '585', '1', '10.9.28.53', 'admin:登录【13:用户名或密码错误，剩余重试次数：2】', '2018-06-04 17:28:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1237', '0', '586', '1', '10.9.28.53', 'admin:登录【13:用户名或密码错误，剩余重试次数：1】', '2018-06-04 17:28:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1238', '1', '587', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 17:28:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1239', '1', '588', '2', '10.9.28.53', '退出登录成功', '2018-06-04 17:29:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1240', '1', '589', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 17:29:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1241', '53', '590', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-04 18:24:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1242', '1', '591', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 19:35:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1243', '1', '592', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-04 20:30:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1244', '51', '593', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-05 10:41:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1245', '1', '594', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-05 10:41:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1246', '51', '595', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-05 14:07:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1247', '1', '596', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-05 17:59:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1248', '51', '597', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-06 08:48:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1249', '52', '598', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-06 08:59:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1250', '1', '599', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-06 11:26:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1251', '51', '600', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-06 11:52:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1252', '1', '601', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-06 14:23:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1253', '51', '602', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-06 14:30:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1254', '1', '603', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-06 14:53:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1255', '52', '604', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-06 15:25:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1256', '52', '605', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-06 16:32:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1257', '1', '606', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-06 16:50:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1258', '51', '607', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 09:03:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1259', '53', '608', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 09:10:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1260', '51', '609', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 10:39:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1261', '51', '610', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 10:50:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1262', '53', '611', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 11:44:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1263', '53', '612', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 13:39:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1264', '53', '613', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 14:21:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1265', '51', '614', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-07 14:37:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1266', '52', '615', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-08 09:13:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1267', '53', '616', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-08 09:21:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1268', '53', '617', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-08 16:05:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1269', '1', '657', '2', '10.34.2.33', '退出登录成功', '2018-06-11 16:39:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1270', '1', '658', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-11 16:39:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1271', '46', '659', '1', '10.33.2.100', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-11 17:33:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1272', '46', '660', '1', '10.9.66.178', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-11 17:40:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1273', '46', '661', '2', '10.9.66.178', '退出登录成功', '2018-06-11 18:01:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1274', '53', '662', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 09:37:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1275', '47', '663', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 09:46:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1276', '47', '664', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 10:54:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1277', '1', '665', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-12 11:28:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1278', '47', '666', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 11:32:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1279', '1', '667', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-12 13:56:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1280', '53', '668', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 14:33:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1281', '1', '669', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-12 14:40:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1282', '51', '670', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 14:41:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1283', '47', '671', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 15:25:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1284', '51', '672', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 15:26:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1285', '47', '673', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 16:51:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1286', '47', '674', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 17:00:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1287', '53', '675', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 17:14:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1288', '47', '676', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 17:39:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1289', '53', '677', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 17:56:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1290', '1', '678', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-12 18:07:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1291', '46', '679', '1', '10.9.66.178', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-12 18:56:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1292', '1', '680', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-13 10:28:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1293', '53', '681', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 12:57:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1294', '53', '682', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 14:37:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1295', '0', '683', '1', '10.9.29.29', 'admin:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-06-13 14:47:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1296', '0', '684', '1', '10.9.29.29', 'admin:登录【13:用户名或密码错误，剩余重试次数：3】', '2018-06-13 14:47:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1297', '3', '685', '1', '10.9.29.29', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-06-13 14:47:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1298', '1', '686', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-13 14:47:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1299', '1', '687', '2', '10.9.28.53', '退出登录成功', '2018-06-13 14:48:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1300', '3', '688', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-06-13 14:48:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1301', '53', '689', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 15:11:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1302', '46', '690', '1', '10.9.66.178', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 15:31:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1303', '1', '691', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-13 15:33:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1304', '46', '692', '1', '10.33.2.100', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 17:03:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1305', '47', '693', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 17:08:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1306', '46', '694', '1', '10.9.66.178', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 17:58:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1307', '51', '695', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 18:32:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1308', '51', '696', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-13 19:59:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1309', '53', '697', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 09:17:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1310', '52', '698', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 10:02:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1311', '53', '699', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 11:03:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1312', '53', '700', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 11:36:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1313', '47', '701', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 13:14:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1314', '47', '702', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 15:58:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1315', '47', '703', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 19:30:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1316', '47', '704', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-14 20:01:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1317', '52', '705', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 09:02:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1318', '51', '706', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 09:34:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1319', '52', '707', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 09:35:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1320', '52', '708', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 14:40:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1321', '53', '709', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 15:14:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1322', '51', '710', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 15:16:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1323', '53', '711', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-15 21:56:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1324', '52', '712', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-16 14:59:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1325', '51', '713', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 11:06:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1326', '51', '714', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 11:39:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1327', '51', '715', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 11:49:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1328', '51', '716', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 12:34:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1329', '47', '717', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 13:08:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1330', '51', '718', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 14:05:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1331', '47', '719', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 16:00:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1332', '51', '720', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 16:06:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1333', '51', '721', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 16:26:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1334', '0', '722', '1', '10.33.2.37', ':登录【15:用户不存在】', '2018-06-17 20:09:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1335', '0', '723', '1', '10.33.2.37', ':登录【15:用户不存在】', '2018-06-17 20:09:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1336', '47', '724', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-17 20:09:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1337', '51', '725', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-18 12:51:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1338', '47', '726', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-18 21:07:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1339', '53', '727', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 09:55:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1340', '52', '728', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 10:44:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1341', '47', '729', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 14:44:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1342', '53', '730', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 14:45:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1343', '47', '731', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 15:35:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1344', '0', '732', '1', '10.33.2.18', ':登录【15:用户不存在】', '2018-06-19 16:46:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1345', '53', '733', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 16:46:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1346', '47', '734', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-19 18:49:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1347', '1', '735', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-20 10:35:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1348', '52', '736', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 10:36:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1349', '1', '737', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-20 11:16:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1350', '46', '738', '1', '10.33.13.50', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 13:34:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1351', '46', '739', '1', '10.33.13.50', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 13:35:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1352', '53', '740', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 14:43:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1353', '47', '741', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 15:11:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1354', '52', '742', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 15:54:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1355', '1', '743', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-20 16:14:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1356', '53', '744', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 16:14:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1357', '47', '745', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-20 16:26:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1358', '46', '746', '1', '10.33.13.50', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 14:21:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1359', '52', '747', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 14:38:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1360', '53', '748', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 14:41:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1361', '52', '749', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 15:50:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1362', '51', '750', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 16:02:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1363', '1', '751', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-21 16:37:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1364', '1', '752', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-21 17:23:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1365', '51', '753', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 19:08:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1366', '51', '754', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-21 20:34:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1367', '46', '755', '1', '10.9.66.218', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-22 09:28:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1368', '1', '756', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-22 11:29:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1369', '3', '757', '1', '10.9.28.43', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-06-22 11:48:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1370', '53', '758', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-22 14:17:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1371', '53', '759', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-22 15:20:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1372', '52', '760', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-22 16:01:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1373', '51', '761', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-22 16:07:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1374', '53', '762', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-22 16:39:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1375', '47', '763', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-23 09:52:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1376', '47', '764', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-23 11:01:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1377', '47', '765', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-23 12:33:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1378', '51', '766', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-23 13:38:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1379', '51', '767', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-23 21:48:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1380', '53', '768', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-24 13:08:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1381', '51', '769', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-25 09:13:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1382', '51', '770', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-25 10:27:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1383', '51', '771', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-25 10:44:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1384', '47', '772', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-25 11:42:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1385', '52', '773', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 08:56:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1386', '51', '774', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 09:20:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1387', '51', '775', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 10:30:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1388', '52', '776', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 11:27:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1389', '51', '777', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 15:30:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1390', '52', '778', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 15:49:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1391', '51', '779', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 17:21:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1392', '51', '780', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 17:54:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1393', '52', '781', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 18:02:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1394', '52', '782', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-26 18:12:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1395', '51', '783', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 09:11:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1396', '47', '784', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 09:12:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1397', '51', '785', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 11:03:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1398', '0', '786', '1', '10.33.2.96', ':登录【15:用户不存在】', '2018-06-27 12:13:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1399', '0', '787', '1', '10.33.2.96', ':登录【15:用户不存在】', '2018-06-27 12:13:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1400', '51', '788', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 12:13:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1401', '0', '789', '1', '10.33.2.37', ':登录【15:用户不存在】', '2018-06-27 12:38:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1402', '0', '790', '1', '10.33.2.37', ':登录【15:用户不存在】', '2018-06-27 12:38:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1403', '52', '791', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 12:55:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1404', '47', '792', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 13:08:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1405', '0', '793', '1', '10.33.2.37', ':登录【15:用户不存在】', '2018-06-27 14:43:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1406', '52', '794', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 14:55:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1407', '51', '795', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 15:25:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1408', '47', '796', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 15:42:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1409', '51', '797', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 17:42:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1410', '52', '798', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 18:42:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1411', '52', '799', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-27 21:09:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1412', '51', '800', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 09:39:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1413', '51', '801', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 10:21:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1414', '51', '802', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 10:52:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1415', '51', '803', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 12:00:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1416', '52', '804', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 13:09:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1417', '51', '805', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 14:35:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1418', '51', '806', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 15:41:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1419', '52', '807', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 19:19:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1420', '52', '808', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-28 21:17:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1421', '53', '809', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 09:11:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1422', '53', '810', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 10:20:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1423', '53', '811', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 11:36:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1424', '53', '812', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 12:30:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1425', '52', '813', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 13:36:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1426', '52', '814', '2', '10.33.2.12', '退出登录成功', '2018-06-29 13:37:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1427', '52', '815', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 13:37:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1428', '52', '816', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 14:25:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1429', '46', '817', '1', '10.35.40.227', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 14:37:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1430', '46', '818', '1', '10.35.40.227', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 16:34:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1431', '1', '819', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-06-29 17:51:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1432', '52', '820', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 18:04:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1433', '0', '821', '1', '10.33.2.18', ':登录【15:用户不存在】', '2018-06-29 18:06:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1434', '53', '822', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 18:06:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1435', '52', '823', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-29 21:38:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1436', '53', '824', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 10:19:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1437', '53', '825', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 11:31:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1438', '53', '826', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 12:48:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1439', '51', '827', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 14:22:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1440', '53', '828', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 14:49:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1441', '51', '829', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 15:27:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1442', '53', '830', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 15:29:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1443', '53', '831', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 16:38:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1444', '51', '832', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 16:40:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1445', '51', '833', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 16:52:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1446', '51', '834', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-06-30 20:38:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1447', '53', '835', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 09:30:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1448', '52', '836', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 09:31:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1449', '52', '837', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 10:23:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1450', '52', '838', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 11:50:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1451', '51', '839', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 13:25:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1452', '52', '840', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 14:03:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1453', '51', '841', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 18:35:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1454', '51', '842', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 18:39:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1455', '51', '843', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-01 19:08:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1456', '52', '844', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 09:21:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1457', '52', '845', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 10:04:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1458', '52', '846', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 11:07:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1459', '52', '847', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 11:53:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1460', '51', '848', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 13:15:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1461', '1', '849', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-07-02 13:42:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1462', '52', '850', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 15:43:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1463', '52', '851', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-02 17:10:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1464', '1', '852', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-07-02 17:44:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1465', '53', '853', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 10:35:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1466', '53', '854', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 11:08:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1467', '52', '855', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 13:28:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1468', '53', '856', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 14:13:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1469', '52', '857', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 14:42:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1470', '1', '858', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-07-03 15:43:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1471', '52', '859', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 15:55:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1472', '53', '860', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 15:57:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1473', '1', '861', '1', '10.34.2.33', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-07-03 16:34:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1474', '52', '862', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-03 21:37:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1475', '51', '863', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 09:26:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1476', '51', '864', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 10:24:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1477', '51', '865', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 10:25:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1478', '52', '866', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 11:26:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1479', '51', '867', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 11:33:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1480', '51', '868', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 12:51:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1481', '53', '869', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 13:06:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1482', '51', '870', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 13:22:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1483', '53', '871', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 14:07:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1484', '51', '872', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 15:15:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1485', '53', '873', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 15:19:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1486', '51', '874', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 16:11:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1487', '53', '875', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 16:37:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1488', '51', '876', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 17:47:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1489', '53', '877', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 19:41:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1490', '53', '878', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 22:02:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1491', '46', '879', '1', '10.35.40.24', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-04 22:46:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1492', '52', '880', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 09:16:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1493', '51', '881', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 10:49:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1494', '52', '882', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 11:00:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1495', '52', '883', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 11:42:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1496', '51', '884', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 12:58:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1497', '52', '885', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 13:17:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1498', '52', '886', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 14:16:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1499', '51', '887', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 14:17:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1500', '51', '888', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 14:27:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1501', '51', '889', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 14:38:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1502', '51', '890', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-05 16:43:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1503', '52', '891', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 09:05:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1504', '52', '892', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 09:55:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1505', '52', '893', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 10:52:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1506', '1', '894', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberMe\":true}】', '2018-07-06 13:54:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1507', '53', '895', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 14:42:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1508', '52', '896', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 14:45:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1509', '3', '897', '1', '10.9.29.29', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-07-06 16:48:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1510', '3', '898', '1', '10.9.28.43', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberMe\":true}】', '2018-07-06 16:49:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1511', '52', '899', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 16:56:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1512', '53', '900', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberMe\":true}】', '2018-07-06 17:34:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1513', '1', '901', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 17:57:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1514', '3', '902', '1', '10.9.28.43', '登录成功【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberme\":true}】', '2018-07-06 18:00:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1515', '3', '903', '2', '10.9.28.43', '退出登录成功', '2018-07-06 18:00:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1516', '1', '904', '1', '10.9.28.43', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 18:00:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1517', '1', '905', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 18:01:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1518', '1', '906', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 18:11:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1519', '1', '907', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 18:16:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1520', '1', '908', '11', '10.34.2.5', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 18:20:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1521', '1', '909', '11', '10.34.2.5', '编辑用户成功【{\"userid\":46,\"usercode\":\"60030\",\"username\":\"董娟\"}】', '2018-07-06 18:20:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1522', '1', '910', '11', '10.34.2.5', '编辑用户成功【{\"userid\":47,\"usercode\":\"60102\",\"username\":\"史梦迪\"}】', '2018-07-06 18:21:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1523', '1', '911', '11', '10.34.2.5', '编辑用户成功【{\"userid\":47,\"usercode\":\"60102\",\"username\":\"史梦迪\"}】', '2018-07-06 18:21:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1524', '1', '912', '11', '10.34.2.5', '编辑用户成功【{\"userid\":48,\"usercode\":\"60066\",\"username\":\"冯霞\"}】', '2018-07-06 18:21:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1525', '1', '913', '11', '10.34.2.5', '编辑用户成功【{\"userid\":48,\"usercode\":\"60066\",\"username\":\"冯霞\"}】', '2018-07-06 18:21:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1526', '1', '914', '11', '10.34.2.5', '编辑用户成功【{\"userid\":51,\"usercode\":\"60091\",\"username\":\"罗园园\"}】', '2018-07-06 18:22:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1527', '53', '915', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-06 18:30:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1528', '1', '916', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 18:55:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1529', '1', '917', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 18:56:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1530', '1', '918', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 18:57:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1531', '1', '919', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 18:58:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1532', '1', '920', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 18:59:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1533', '1', '921', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 18:59:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1534', '1', '922', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:01:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1535', '1', '923', '21', '10.9.29.29', '增加部门【{\"name\":\"客服中心\",\"desc\":\"物主项目客服\",\"parentorgid\":\"物主项目\"}】', '2018-07-06 19:09:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1536', '1', '924', '21', '10.9.29.29', '增加部门【{\"name\":\"客服中心\",\"desc\":\"物主项目客服\",\"parentorgid\":\"物主项目\"}】', '2018-07-06 19:09:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1537', '1', '925', '21', '10.9.28.53', '增加部门成功【{\"name\":\"物主项目\",\"desc\":\"物主项目\",\"parentorgid\":\"\"}】', '2018-07-06 19:16:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1538', '1', '926', '21', '10.9.28.53', '增加部门成功【{\"name\":\"客服部\",\"desc\":\"客服部\",\"parentorgid\":\"53\"}】', '2018-07-06 19:17:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1539', '1', '927', '32', '10.9.29.29', '编辑角色成功【{\"id\":3,\"rolename\":\"客服专员(物主)\",\"desc\":\"客服专员（物主）\"}】', '2018-07-06 19:17:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1540', '1', '928', '11', '10.9.29.29', '编辑用户成功【{\"userid\":46,\"usercode\":\"60030\",\"username\":\"董娟\"}】', '2018-07-06 19:18:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1541', '1', '929', '32', '10.9.29.29', '编辑角色成功【{\"id\":3,\"rolename\":\"客服专员(物主)\",\"desc\":\"客服专员（物主）\"}】', '2018-07-06 19:19:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1542', '1', '930', '2', '10.9.28.53', '退出登录成功', '2018-07-06 19:19:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1543', '1', '931', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:20:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1544', '1', '932', '11', '10.9.29.29', '编辑用户成功【{\"userid\":46,\"usercode\":\"60030\",\"username\":\"董娟\"}】', '2018-07-06 19:20:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1545', '1', '933', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:25:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1546', '0', '934', '1', '10.9.29.29', 'test:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-07-06 19:26:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1547', '1', '935', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:26:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1548', '1', '936', '11', '10.9.29.29', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:26:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1549', '1', '937', '2', '10.9.28.53', '退出登录成功', '2018-07-06 19:27:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1550', '3', '938', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:27:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1551', '1', '939', '32', '10.9.29.29', '编辑角色成功【{\"id\":4,\"rolename\":\"测试人员\",\"desc\":\"测试\"}】', '2018-07-06 19:27:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1552', '1', '940', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:27:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1553', '3', '941', '1', '10.9.29.29', '登录成功【{\"username\":\"test\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:27:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1554', '3', '942', '2', '10.9.28.53', '退出登录成功', '2018-07-06 19:28:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1555', '1', '943', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:29:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1556', '3', '944', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:30:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1557', '1', '945', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:30:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1558', '1', '946', '11', '10.9.29.29', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:38:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1559', '1', '947', '11', '10.9.29.29', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:38:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1560', '1', '948', '11', '10.9.29.29', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:38:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1561', '1', '949', '31', '10.9.29.29', '增加角色成功【{\"rolename\":\"test\",\"desc\":\"test\",\"selectedpermissions\":[6,8,9,10,11,12,13,14,2,1,3,4,5,7,18,20,21,24,25,26,27,28,29,30,31,32,19,22,23,33,34,35,36,15,16,17]}】', '2018-07-06 19:39:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1562', '1', '950', '10', '10.9.29.29', '增加用户成功【{\"usercode\":\"test2\",\"username\":\"test2\",\"email\":\"test\"}】', '2018-07-06 19:40:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1563', '1', '951', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:40:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1564', '0', '952', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-06 19:40:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1565', '54', '953', '1', '10.9.29.29', '登录成功【{\"username\":\"test2\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:40:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1566', '54', '954', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:41:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1567', '1', '955', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:41:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1568', '1', '956', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:42:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1569', '54', '957', '1', '10.9.29.29', '登录成功【{\"username\":\"test2\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:42:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1570', '54', '958', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:43:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1571', '0', '959', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-06 19:43:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1572', '0', '960', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-06 19:43:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1573', '1', '961', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:43:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1574', '1', '962', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:44:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1575', '0', '963', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-06 19:44:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1576', '1', '964', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:44:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1577', '1', '965', '32', '10.9.29.29', '编辑角色成功【{\"id\":81,\"rolename\":\"test测试\",\"desc\":\"test测试\"}】', '2018-07-06 19:45:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1578', '1', '966', '11', '10.9.29.29', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:45:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1579', '1', '967', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:45:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1580', '0', '968', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-06 19:45:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1581', '1', '969', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:45:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1582', '1', '970', '11', '10.9.29.29', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-06 19:46:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1583', '1', '971', '2', '10.9.29.29', '退出登录成功', '2018-07-06 19:47:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1584', '0', '972', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-06 19:47:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1585', '54', '973', '1', '10.9.29.29', '登录成功【{\"username\":\"test2\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-06 19:47:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1586', '51', '974', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 09:24:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1587', '51', '975', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 11:59:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1588', '51', '976', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 12:22:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1589', '52', '977', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 14:15:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1590', '51', '978', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 14:55:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1591', '51', '979', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 15:06:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1592', '51', '980', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-07 17:16:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1593', '51', '981', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 09:38:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1594', '51', '982', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 11:54:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1595', '51', '983', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 12:35:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1596', '53', '984', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 13:50:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1597', '53', '985', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 15:44:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1598', '51', '986', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 17:06:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1599', '51', '987', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 17:06:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1600', '53', '988', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-08 21:51:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1601', '1', '989', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:10:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1602', '1', '990', '2', '10.9.29.29', '退出登录成功', '2018-07-09 10:13:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1603', '0', '991', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:13:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1604', '0', '992', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:13:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1605', '0', '993', '1', '10.9.29.29', 'test2:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-07-09 10:13:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1606', '54', '994', '1', '10.9.29.29', '登录成功【{\"username\":\"test2\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:13:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1607', '1', '995', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:13:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1608', '1', '996', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:14:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1609', '54', '997', '1', '10.9.28.53', '登录成功【{\"username\":\"test2\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:14:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1610', '54', '998', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:20:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1611', '0', '999', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:20:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1612', '0', '1000', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1613', '0', '1001', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1614', '1', '1002', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:21:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1615', '1', '1003', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:21:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1616', '0', '1004', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1617', '0', '1005', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1618', '0', '1006', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1619', '0', '1007', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1620', '0', '1008', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:21:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1621', '0', '1009', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:22:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1622', '0', '1010', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:22:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1623', '1', '1011', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:22:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1624', '1', '1012', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-09 10:23:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1625', '1', '1013', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:23:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1626', '0', '1014', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:23:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1627', '0', '1015', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:23:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1628', '1', '1016', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:23:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1629', '1', '1017', '14', '10.9.28.53', '启用/禁用用户成功【id=3】', '2018-07-09 10:23:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1630', '1', '1018', '14', '10.9.28.53', '启用/禁用用户成功【id=3】', '2018-07-09 10:23:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1631', '1', '1019', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-09 10:24:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1632', '1', '1020', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:24:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1633', '0', '1021', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:24:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1634', '0', '1022', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:24:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1635', '0', '1023', '1', '10.9.28.53', 'admin:登录【13:用户名或密码错误，剩余重试次数：4】', '2018-07-09 10:25:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1636', '1', '1024', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:26:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1637', '1', '1025', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:30:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1638', '1', '1026', '11', '10.34.2.5', '编辑用户成功【{\"userid\":46,\"usercode\":\"60030\",\"username\":\"董娟\"}】', '2018-07-09 10:32:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1639', '54', '1027', '2', '10.9.29.29', '退出登录成功', '2018-07-09 10:38:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1640', '51', '1028', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 10:38:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1641', '0', '1029', '1', '10.9.29.29', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.29.29)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:38:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1642', '47', '1030', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 10:38:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1643', '1', '1031', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:40:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1644', '1', '1032', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:44:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1645', '0', '1033', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:44:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1646', '0', '1034', '1', '10.9.28.53', 'test:登录【Authentication failed for token submission [org.apache.shiro.authc.UsernamePasswordToken - test, rememberMe=true (10.9.28.53)].  Possible unexpected error? (Typical or expected login exceptions should extend from AuthenticationException).】', '2018-07-09 10:48:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1647', '1', '1035', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 10:50:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1648', '1', '1036', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test_user\",\"username\":\"测试人员\"}】', '2018-07-09 10:50:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1649', '1', '1037', '2', '10.9.28.53', '退出登录成功', '2018-07-09 10:50:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1650', '3', '1038', '1', '10.9.28.53', '登录成功【{\"username\":\"test_user\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-09 10:50:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1651', '51', '1039', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 11:27:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1652', '51', '1040', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 12:34:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1653', '53', '1041', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 13:17:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1654', '51', '1042', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 13:18:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1655', '47', '1043', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 14:11:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1656', '1', '1044', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 14:31:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1657', '1', '1045', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-09 14:31:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1658', '1', '1046', '11', '10.9.28.53', '编辑用户成功【{\"userid\":3,\"usercode\":\"test\",\"username\":\"测试人员\"}】', '2018-07-09 14:32:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1659', '1', '1047', '2', '10.9.28.53', '退出登录成功', '2018-07-09 14:32:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1660', '3', '1048', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-09 14:32:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1661', '47', '1049', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 15:04:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1662', '51', '1050', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 15:16:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1663', '51', '1051', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 15:59:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1664', '51', '1052', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 16:07:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1665', '47', '1053', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 16:13:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1666', '53', '1054', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 16:23:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1667', '51', '1055', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 16:33:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1668', '3', '1056', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-09 17:12:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1669', '47', '1057', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 17:27:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1670', '47', '1058', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 18:05:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1671', '53', '1059', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 19:16:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1672', '53', '1060', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-09 20:52:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1673', '52', '1061', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 08:59:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1674', '52', '1062', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 09:50:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1675', '51', '1063', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 10:10:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1676', '52', '1064', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 10:21:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1677', '51', '1065', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 10:46:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1678', '52', '1066', '1', '10.33.2.12', '登录【{\"username\":\"60054\",\"password\":\"7148325920e2a45250c01482826f187d\",\"rememberme\":true}】', '2018-07-10 11:35:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1679', '52', '1067', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 11:36:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1680', '1', '1068', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-10 11:52:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1681', '51', '1069', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 12:15:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1682', '51', '1070', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 13:27:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1683', '1', '1071', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-10 13:54:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1684', '47', '1072', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 14:09:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1685', '52', '1073', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 15:12:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1686', '1', '1074', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-10 16:47:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1687', '51', '1075', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 17:05:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1688', '52', '1076', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 17:16:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1689', '47', '1077', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-10 21:33:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1690', '53', '1078', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 09:26:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1691', '52', '1079', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 10:00:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1692', '53', '1080', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 11:23:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1693', '46', '1081', '1', '10.33.2.100', '登录成功【{\"username\":\"60030\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 13:55:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1694', '1', '1082', '1', '10.9.29.29', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-11 14:12:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1695', '52', '1083', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 14:32:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1696', '53', '1084', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 15:25:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1697', '47', '1085', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 15:36:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1698', '47', '1086', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 15:44:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1699', '53', '1087', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 16:03:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1700', '3', '1088', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-11 16:15:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1701', '47', '1089', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 17:02:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1702', '53', '1090', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 17:22:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1703', '52', '1091', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 17:40:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1704', '47', '1092', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 18:09:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1705', '47', '1093', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-11 19:12:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1706', '52', '1094', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 09:27:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1707', '3', '1095', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-12 11:27:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1708', '53', '1096', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 11:39:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1709', '52', '1097', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 12:37:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1710', '53', '1098', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 12:40:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1711', '47', '1099', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 13:14:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1712', '52', '1100', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 14:37:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1713', '47', '1101', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 15:12:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1714', '52', '1102', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 15:32:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1715', '52', '1103', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 17:37:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1716', '47', '1104', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 20:16:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1717', '47', '1105', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-12 20:57:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1718', '52', '1106', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 09:11:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1719', '47', '1107', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 09:17:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1720', '53', '1108', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 09:40:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1721', '52', '1109', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 09:55:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1722', '52', '1110', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 11:06:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1723', '52', '1111', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 13:12:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1724', '53', '1112', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 13:51:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1725', '51', '1113', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 14:29:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1726', '53', '1114', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 15:01:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1727', '52', '1115', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 15:45:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1728', '1', '1116', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-13 16:21:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1729', '52', '1117', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 16:59:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1730', '1', '1118', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-13 18:01:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1731', '53', '1119', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 18:38:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1732', '51', '1120', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 19:05:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1733', '51', '1121', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-13 20:18:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1734', '53', '1122', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 09:22:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1735', '52', '1123', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 10:20:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1736', '53', '1124', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 10:32:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1737', '52', '1125', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 10:45:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1738', '52', '1126', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 12:05:09', null);
INSERT INTO `T_OP_LOG` VALUES ('1739', '53', '1127', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 12:42:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1740', '52', '1128', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 13:42:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1741', '52', '1129', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 14:07:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1742', '51', '1130', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 14:34:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1743', '53', '1131', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 15:47:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1744', '52', '1132', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 15:59:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1745', '51', '1133', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 16:11:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1746', '52', '1134', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 16:39:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1747', '53', '1135', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 16:48:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1748', '51', '1136', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 16:56:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1749', '51', '1137', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-14 19:17:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1750', '47', '1138', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 09:03:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1751', '47', '1139', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 09:56:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1752', '47', '1140', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 11:07:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1753', '47', '1141', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 11:53:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1754', '47', '1142', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 14:10:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1755', '51', '1143', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 14:16:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1756', '51', '1144', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 14:25:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1757', '47', '1145', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 15:17:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1758', '47', '1146', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 16:56:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1759', '47', '1147', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 17:05:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1760', '51', '1148', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-15 21:32:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1761', '47', '1149', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 09:09:34', null);
INSERT INTO `T_OP_LOG` VALUES ('1762', '53', '1150', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 09:16:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1763', '47', '1151', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 11:29:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1764', '1', '1152', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-16 11:34:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1765', '53', '1153', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 12:42:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1766', '47', '1154', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 13:40:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1767', '52', '1155', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 14:03:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1768', '53', '1156', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 14:27:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1769', '53', '1157', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 14:52:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1770', '1', '1158', '1', '10.9.28.53', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-16 14:59:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1771', '1', '1159', '1', '10.9.25.26', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-16 15:04:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1772', '1', '1160', '1', '10.9.25.26', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-16 15:04:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1773', '0', '1161', '1', '10.33.2.96', '60107:登录【15:用户不存在】', '2018-07-16 15:13:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1774', '0', '1162', '1', '10.33.2.96', '60107:登录【15:用户不存在】', '2018-07-16 15:14:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1775', '47', '1163', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 15:24:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1776', '51', '1164', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 15:26:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1777', '47', '1165', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 15:30:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1778', '47', '1166', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 16:41:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1779', '52', '1167', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 16:42:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1780', '53', '1168', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 17:47:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1781', '52', '1169', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-16 17:57:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1782', '47', '1170', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 09:18:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1783', '51', '1171', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 09:22:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1784', '53', '1172', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 09:34:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1785', '51', '1173', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 09:49:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1786', '53', '1174', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 10:20:37', null);
INSERT INTO `T_OP_LOG` VALUES ('1787', '51', '1175', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 10:21:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1788', '53', '1176', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 11:04:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1789', '51', '1177', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 11:43:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1790', '51', '1178', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 12:42:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1791', '52', '1179', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 12:54:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1792', '47', '1180', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 13:54:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1793', '53', '1181', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 14:39:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1794', '51', '1182', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 15:42:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1795', '52', '1183', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 16:12:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1796', '51', '1184', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 16:23:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1797', '53', '1185', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 17:44:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1798', '51', '1186', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 17:54:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1799', '47', '1187', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 18:06:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1800', '53', '1188', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 18:27:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1801', '52', '1189', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 18:50:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1802', '52', '1190', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-17 20:49:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1803', '53', '1191', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 09:04:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1804', '51', '1192', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 09:22:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1805', '51', '1193', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 10:44:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1806', '1', '1194', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-18 12:05:33', null);
INSERT INTO `T_OP_LOG` VALUES ('1807', '51', '1195', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 12:23:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1808', '52', '1196', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 13:25:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1809', '53', '1197', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 13:55:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1810', '51', '1198', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 14:09:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1811', '51', '1199', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 15:20:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1812', '52', '1200', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 15:32:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1813', '1', '1201', '1', '10.9.25.28', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-18 15:53:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1814', '1', '1202', '31', '10.9.25.28', '增加角色成功【{\"rolename\":\"呼叫中心客服部员工\",\"desc\":\"呼叫中心客服部员工\",\"selectedpermissions\":[20,18,21,24,25,17]}】', '2018-07-18 16:00:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1815', '1', '1203', '31', '10.9.25.28', '增加角色成功【{\"rolename\":\"呼叫中心工单部员工\",\"desc\":\"呼叫中心工单部员工\",\"selectedpermissions\":[18,20,21,24,25,26,27,28,29,30,31,32,17]}】', '2018-07-18 16:02:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1816', '1', '1204', '31', '10.9.25.28', '增加角色成功【{\"rolename\":\"呼叫中心工单部管理员\",\"desc\":\"呼叫中心工单部管理员\",\"selectedpermissions\":[20,21,24,25,26,27,28,29,30,31,32,18,22,23,33,34,35,36,19,17]}】', '2018-07-18 16:03:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1817', '1', '1205', '10', '10.9.25.28', '增加用户成功【{\"usercode\":\"gdadmin\",\"username\":\"工单管理员\",\"email\":\"xxx@dafy.com\"}】', '2018-07-18 16:04:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1818', '1', '1206', '2', '10.9.25.28', '退出登录成功', '2018-07-18 16:04:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1819', '55', '1207', '1', '10.9.25.28', '登录成功【{\"username\":\"gdadmin\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-18 16:05:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1820', '55', '1208', '2', '10.9.25.28', '退出登录成功', '2018-07-18 16:05:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1821', '1', '1209', '1', '10.9.25.28', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-18 16:05:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1822', '1', '1210', '32', '10.9.25.28', '编辑角色成功【{\"id\":84,\"rolename\":\"呼叫中心工单部管理员\",\"desc\":\"呼叫中心工单部管理员\"}】', '2018-07-18 16:05:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1823', '1', '1211', '2', '10.9.25.28', '退出登录成功', '2018-07-18 16:06:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1824', '55', '1212', '1', '10.9.25.28', '登录成功【{\"username\":\"gdadmin\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-18 16:06:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1825', '1', '1213', '1', '10.9.25.28', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-18 16:53:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1826', '52', '1214', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 17:01:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1827', '0', '1215', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:30:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1828', '0', '1216', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:31:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1829', '47', '1217', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 17:31:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1830', '0', '1218', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:31:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1831', '0', '1219', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:31:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1832', '53', '1220', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 17:35:02', null);
INSERT INTO `T_OP_LOG` VALUES ('1833', '0', '1221', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:39:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1834', '0', '1222', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:39:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1835', '0', '1223', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:39:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1836', '0', '1224', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:39:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1837', '0', '1225', '1', '10.33.2.18', '60107:登录【15:用户不存在】', '2018-07-18 17:39:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1838', '53', '1226', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-18 17:42:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1839', '53', '1227', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 09:47:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1840', '51', '1228', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 10:20:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1841', '53', '1229', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 10:37:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1842', '53', '1230', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 12:11:07', null);
INSERT INTO `T_OP_LOG` VALUES ('1843', '51', '1231', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 12:11:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1844', '51', '1232', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 12:56:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1845', '51', '1233', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 13:03:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1846', '52', '1234', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 13:32:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1847', '53', '1235', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 13:54:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1848', '51', '1236', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 13:56:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1849', '47', '1237', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 14:48:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1850', '51', '1238', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-19 15:44:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1851', '51', '1239', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 09:08:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1852', '47', '1240', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 09:10:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1853', '51', '1241', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 10:16:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1854', '47', '1242', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 10:21:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1855', '53', '1243', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 13:03:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1856', '47', '1244', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 14:12:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1857', '51', '1245', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 14:52:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1858', '53', '1246', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 15:18:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1859', '47', '1247', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 16:20:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1860', '53', '1248', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 16:25:22', null);
INSERT INTO `T_OP_LOG` VALUES ('1861', '53', '1249', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 16:27:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1862', '1', '1250', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-20 16:45:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1863', '51', '1251', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 17:10:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1864', '51', '1252', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 17:10:40', null);
INSERT INTO `T_OP_LOG` VALUES ('1865', '51', '1253', '1', '10.33.2.96', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 18:04:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1866', '47', '1254', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-20 18:10:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1867', '47', '1255', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 09:04:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1868', '52', '1256', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 09:29:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1869', '52', '1257', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 11:02:13', null);
INSERT INTO `T_OP_LOG` VALUES ('1870', '47', '1258', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 11:42:08', null);
INSERT INTO `T_OP_LOG` VALUES ('1871', '52', '1259', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 12:21:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1872', '47', '1260', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 13:14:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1873', '47', '1261', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 14:36:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1874', '47', '1262', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 17:31:00', null);
INSERT INTO `T_OP_LOG` VALUES ('1875', '47', '1263', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 17:31:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1876', '53', '1264', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 20:25:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1877', '53', '1265', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-21 21:43:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1878', '52', '1266', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 09:01:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1879', '47', '1267', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 09:01:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1880', '52', '1268', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 10:04:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1881', '0', '1269', '1', '10.33.2.37', 'MCY60107:登录【15:用户不存在】', '2018-07-22 10:09:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1882', '0', '1270', '1', '10.33.2.37', '60107:登录【15:用户不存在】', '2018-07-22 10:09:47', null);
INSERT INTO `T_OP_LOG` VALUES ('1883', '47', '1271', '1', '10.33.2.37', '登录【{\"username\":\"60102\",\"password\":\"2ff8175a118ff1a2f0ad510b70d37063\",\"rememberme\":true}】', '2018-07-22 10:09:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1884', '47', '1272', '1', '10.33.2.37', '登录【{\"username\":\"60102\",\"password\":\"5501ac843169f34837f9b4bc7a9f6d47\",\"rememberme\":true}】', '2018-07-22 10:10:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1885', '47', '1273', '1', '10.33.2.37', '登录【{\"username\":\"60102\",\"password\":\"5501ac843169f34837f9b4bc7a9f6d47\",\"rememberme\":true}】', '2018-07-22 10:11:12', null);
INSERT INTO `T_OP_LOG` VALUES ('1886', '47', '1274', '1', '10.33.2.37', '登录【{\"username\":\"60102\",\"password\":\"2ff8175a118ff1a2f0ad510b70d37063\",\"rememberme\":true}】', '2018-07-22 10:11:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1887', '47', '1275', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 10:12:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1888', '52', '1276', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 10:46:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1889', '47', '1277', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 11:11:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1890', '52', '1278', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 12:00:49', null);
INSERT INTO `T_OP_LOG` VALUES ('1891', '47', '1279', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 12:05:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1892', '47', '1280', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 13:24:21', null);
INSERT INTO `T_OP_LOG` VALUES ('1893', '52', '1281', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 14:11:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1894', '53', '1282', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 14:40:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1895', '52', '1283', '1', '10.33.2.12', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 14:51:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1896', '47', '1284', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 15:02:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1897', '53', '1285', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 15:36:06', null);
INSERT INTO `T_OP_LOG` VALUES ('1898', '47', '1286', '1', '10.33.2.37', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 16:12:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1899', '53', '1287', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 18:31:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1900', '53', '1288', '1', '10.33.2.18', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-22 21:03:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1901', '1', '1289', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 09:07:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1902', '3', '1290', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-23 10:01:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1903', '1', '1291', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 11:40:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1904', '1', '1292', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 12:21:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1905', '1', '1293', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 13:43:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1906', '1', '1294', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 14:53:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1907', '1', '1295', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 15:42:29', null);
INSERT INTO `T_OP_LOG` VALUES ('1908', '3', '1296', '1', '10.9.28.53', '登录【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberme\":true}】', '2018-07-23 17:10:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1909', '3', '1297', '1', '10.9.28.53', '登录【{\"username\":\"test\",\"password\":\"876e39b7b78a64ff66cf559641e8e2f9\",\"rememberme\":true}】', '2018-07-23 17:10:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1910', '3', '1298', '1', '10.9.28.53', '登录【{\"username\":\"test\",\"password\":\"33c4fc3286fc274e7d528c8ed9797485\",\"rememberme\":true}】', '2018-07-23 17:11:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1911', '3', '1299', '1', '10.9.28.53', '登录【{\"username\":\"test\",\"password\":\"d332bcbe365d8dfb7903fa582da45996\",\"rememberme\":true}】', '2018-07-23 17:12:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1912', '3', '1300', '1', '10.9.28.53', '登录成功【{\"username\":\"test\",\"password\":\"52387d02f7f7e3ef1977d710fc05e007\",\"rememberme\":true}】', '2018-07-23 17:12:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1913', '1', '1301', '1', '10.8.11.140', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-23 17:54:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1914', '47', '1302', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 09:15:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1915', '51', '1303', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 09:21:17', null);
INSERT INTO `T_OP_LOG` VALUES ('1916', '51', '1304', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 09:21:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1917', '52', '1305', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 09:34:16', null);
INSERT INTO `T_OP_LOG` VALUES ('1918', '1', '1306', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 09:47:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1919', '53', '1307', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 09:56:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1920', '47', '1308', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 10:05:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1921', '53', '1309', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 10:39:04', null);
INSERT INTO `T_OP_LOG` VALUES ('1922', '1', '1310', '1', '10.9.25.26', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 10:40:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1923', '52', '1311', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 10:43:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1924', '51', '1312', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 10:46:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1925', '1', '1313', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 11:10:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1926', '0', '1314', '1', '10.33.2.93', '60107:登录【15:用户不存在】', '2018-07-24 11:32:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1927', '51', '1315', '1', '10.33.2.93', '登录【{\"username\":\"60091\",\"password\":\"1eb7fcbb365bb2447bfb167f7b1fb9ad\",\"rememberme\":true}】', '2018-07-24 11:32:46', null);
INSERT INTO `T_OP_LOG` VALUES ('1928', '52', '1316', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 11:32:53', null);
INSERT INTO `T_OP_LOG` VALUES ('1929', '51', '1317', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 11:32:54', null);
INSERT INTO `T_OP_LOG` VALUES ('1930', '53', '1318', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 11:35:45', null);
INSERT INTO `T_OP_LOG` VALUES ('1931', '51', '1319', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 11:53:03', null);
INSERT INTO `T_OP_LOG` VALUES ('1932', '51', '1320', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 12:08:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1933', '52', '1321', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 12:53:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1934', '47', '1322', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 13:15:42', null);
INSERT INTO `T_OP_LOG` VALUES ('1935', '51', '1323', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 13:15:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1936', '1', '1324', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 13:39:24', null);
INSERT INTO `T_OP_LOG` VALUES ('1937', '51', '1325', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 13:55:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1938', '1', '1326', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 14:44:05', null);
INSERT INTO `T_OP_LOG` VALUES ('1939', '47', '1327', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 14:55:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1940', '53', '1328', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 14:58:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1941', '51', '1329', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 15:03:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1942', '52', '1330', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 15:38:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1943', '51', '1331', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 16:40:19', null);
INSERT INTO `T_OP_LOG` VALUES ('1944', '53', '1332', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 16:49:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1945', '52', '1333', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 16:54:20', null);
INSERT INTO `T_OP_LOG` VALUES ('1946', '1', '1334', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 17:25:43', null);
INSERT INTO `T_OP_LOG` VALUES ('1947', '52', '1335', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 17:26:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1948', '47', '1336', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 17:36:59', null);
INSERT INTO `T_OP_LOG` VALUES ('1949', '1', '1337', '1', '10.9.25.26', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 17:39:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1950', '1', '1338', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-24 18:47:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1951', '51', '1339', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 19:10:41', null);
INSERT INTO `T_OP_LOG` VALUES ('1952', '51', '1340', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-24 20:45:27', null);
INSERT INTO `T_OP_LOG` VALUES ('1953', '1', '1341', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-25 09:18:28', null);
INSERT INTO `T_OP_LOG` VALUES ('1954', '51', '1342', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 09:19:39', null);
INSERT INTO `T_OP_LOG` VALUES ('1955', '52', '1343', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 09:19:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1956', '53', '1344', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 09:24:55', null);
INSERT INTO `T_OP_LOG` VALUES ('1957', '51', '1345', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 10:13:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1958', '53', '1346', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 10:49:15', null);
INSERT INTO `T_OP_LOG` VALUES ('1959', '51', '1347', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 10:55:10', null);
INSERT INTO `T_OP_LOG` VALUES ('1960', '1', '1348', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-25 11:13:48', null);
INSERT INTO `T_OP_LOG` VALUES ('1961', '53', '1349', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 11:32:32', null);
INSERT INTO `T_OP_LOG` VALUES ('1962', '1', '1350', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-25 11:48:56', null);
INSERT INTO `T_OP_LOG` VALUES ('1963', '52', '1351', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 12:16:25', null);
INSERT INTO `T_OP_LOG` VALUES ('1964', '51', '1352', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 14:20:23', null);
INSERT INTO `T_OP_LOG` VALUES ('1965', '47', '1353', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 14:37:57', null);
INSERT INTO `T_OP_LOG` VALUES ('1966', '51', '1354', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 14:58:01', null);
INSERT INTO `T_OP_LOG` VALUES ('1967', '53', '1355', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 16:36:11', null);
INSERT INTO `T_OP_LOG` VALUES ('1968', '51', '1356', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-25 16:49:26', null);
INSERT INTO `T_OP_LOG` VALUES ('1969', '1', '1357', '1', '10.34.2.5', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-25 18:02:35', null);
INSERT INTO `T_OP_LOG` VALUES ('1970', '0', '1358', '1', '10.33.2.90', '60107:登录【15:用户不存在】', '2018-07-26 10:22:50', null);
INSERT INTO `T_OP_LOG` VALUES ('1971', '51', '1359', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 10:24:14', null);
INSERT INTO `T_OP_LOG` VALUES ('1972', '1', '1360', '1', '10.8.11.219', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-26 10:30:38', null);
INSERT INTO `T_OP_LOG` VALUES ('1973', '53', '1361', '1', '10.33.2.34', '登录成功【{\"username\":\"60124\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 10:31:58', null);
INSERT INTO `T_OP_LOG` VALUES ('1974', '51', '1362', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 11:09:44', null);
INSERT INTO `T_OP_LOG` VALUES ('1975', '51', '1363', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 11:56:52', null);
INSERT INTO `T_OP_LOG` VALUES ('1976', '52', '1364', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 12:29:51', null);
INSERT INTO `T_OP_LOG` VALUES ('1977', '52', '1365', '1', '10.33.2.90', '登录成功【{\"username\":\"60054\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 13:02:18', null);
INSERT INTO `T_OP_LOG` VALUES ('1978', '51', '1366', '1', '10.33.2.93', '登录成功【{\"username\":\"60091\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 13:06:31', null);
INSERT INTO `T_OP_LOG` VALUES ('1979', '47', '1367', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 13:06:36', null);
INSERT INTO `T_OP_LOG` VALUES ('1980', '1', '1368', '1', '10.8.11.219', '登录成功【{\"username\":\"admin\",\"password\":\"6dbf62af80463ef2c68d9608f68c234a\",\"rememberme\":true}】', '2018-07-26 14:00:30', null);
INSERT INTO `T_OP_LOG` VALUES ('1981', '47', '1369', '1', '10.33.2.78', '登录成功【{\"username\":\"60102\",\"password\":\"1b481782f94f39aac0bb1ae3e060d35a\",\"rememberme\":true}】', '2018-07-26 14:20:53', null);

-- ----------------------------
-- Table structure for T_ORGANIZATION
-- ----------------------------
DROP TABLE IF EXISTS `T_ORGANIZATION`;
CREATE TABLE `T_ORGANIZATION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL COMMENT '编码',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `addr` varchar(32) DEFAULT NULL COMMENT '办公地点',
  `tel` varchar(32) DEFAULT NULL COMMENT '办公电话',
  `principal` varchar(32) DEFAULT NULL COMMENT '负责人',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级机构',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `status` char(1) DEFAULT '0' COMMENT '是否禁用 默认启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_IDX_ORG_CODE` (`code`),
  UNIQUE KEY `U_IDX_ORG_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of T_ORGANIZATION
-- ----------------------------
INSERT INTO `T_ORGANIZATION` VALUES ('1', 'jszx', '技术中心', '华嵘大厦', '0755-5555555', '', null, '总部技术中心', '0', '2018-05-03 13:50:00', '2018-06-22 14:57:21');
INSERT INTO `T_ORGANIZATION` VALUES ('2', 'hjzx', '呼叫中心', '中民时代广场', null, null, null, '呼叫中心事业部', '0', '2018-05-07 11:40:14', '2018-06-28 17:14:05');
INSERT INTO `T_ORGANIZATION` VALUES ('3', null, '测试中心', null, null, null, '1', '市民中心', '0', '2018-05-08 14:50:19', null);
INSERT INTO `T_ORGANIZATION` VALUES ('4', null, '呼叫中心客服部', null, null, null, '2', '客服部', '0', '2018-05-14 11:10:18', null);
INSERT INTO `T_ORGANIZATION` VALUES ('5', null, '呼叫中心工单部', null, null, null, '2', '工单部', '0', '2018-05-14 16:36:53', '2018-07-05 14:05:03');
INSERT INTO `T_ORGANIZATION` VALUES ('6', null, '达飞云贷', null, null, null, null, '子公司', '0', '2018-06-22 11:51:44', '2018-06-22 14:57:04');
INSERT INTO `T_ORGANIZATION` VALUES ('7', null, '云贷客服部', null, null, null, '50', '云贷客服部', '0', '2018-06-22 11:51:50', '2018-06-22 12:26:51');
INSERT INTO `T_ORGANIZATION` VALUES ('53', null, '物主项目', null, null, null, null, '物主项目', '0', '2018-07-06 19:16:49', null);
INSERT INTO `T_ORGANIZATION` VALUES ('54', null, '客服部', null, null, null, '53', '客服部', '0', '2018-07-06 19:17:09', null);

-- ----------------------------
-- Table structure for T_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `T_PERMISSION`;
CREATE TABLE `T_PERMISSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `p_code` varchar(32) DEFAULT NULL COMMENT '权限code',
  `parentId` bigint(20) DEFAULT NULL COMMENT '对应菜单',
  `p_name` varchar(32) DEFAULT NULL COMMENT '权限名',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_IDX_PERM_CODE` (`p_code`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of T_PERMISSION
-- ----------------------------
INSERT INTO `T_PERMISSION` VALUES ('1', 'CustomerMgr:list', '16', '客户管理', null, '2018-01-31 14:37:54', null);
INSERT INTO `T_PERMISSION` VALUES ('2', 'CustomerList:list', '1', '客户列表', null, '2018-01-31 14:38:06', null);
INSERT INTO `T_PERMISSION` VALUES ('3', 'CustomInfo:list', '1', '客户信息查询', null, '2018-01-31 14:38:22', null);
INSERT INTO `T_PERMISSION` VALUES ('4', 'CustomInfo:commit', '1', '提交来电小结', null, '2018-01-31 14:38:32', null);
INSERT INTO `T_PERMISSION` VALUES ('5', 'CustomerOpLog:list', '1', '客户信息操作记录', null, '2018-02-01 11:17:53', null);
INSERT INTO `T_PERMISSION` VALUES ('6', 'SysSetting:list', '15', '系统设置', null, '2018-02-02 13:44:48', null);
INSERT INTO `T_PERMISSION` VALUES ('7', 'CommunicationSummerySetting:list', '16', '客服记录选项设置', null, '2018-02-02 16:42:10', null);
INSERT INTO `T_PERMISSION` VALUES ('8', 'DepartmentManage:list', '6', '部门管理', '', '2018-02-03 23:30:37', null);
INSERT INTO `T_PERMISSION` VALUES ('9', 'CharManage:list', '6', '权限管理', null, '2018-02-04 00:28:11', null);
INSERT INTO `T_PERMISSION` VALUES ('10', 'UserManage:list', '6', '成员管理', null, '2018-02-04 00:28:36', null);
INSERT INTO `T_PERMISSION` VALUES ('11', 'UserManage:enable', '6', '是否启用成员', null, '2018-02-04 00:29:04', null);
INSERT INTO `T_PERMISSION` VALUES ('12', 'UserManage:add', '6', '新增成员', null, '2018-02-04 00:30:06', null);
INSERT INTO `T_PERMISSION` VALUES ('13', 'UserManage:edit', '6', '编辑成员信息', null, '2018-02-04 00:30:18', null);
INSERT INTO `T_PERMISSION` VALUES ('14', 'OpLog:list', '6', '系统操作日志', null, '2018-02-04 00:30:55', null);
INSERT INTO `T_PERMISSION` VALUES ('15', 'BaseConfig:list', null, '基础设置', null, '2018-06-13 11:29:22', null);
INSERT INTO `T_PERMISSION` VALUES ('16', 'MobileRental:list', null, '手机租赁', null, '2018-06-13 11:29:24', null);
INSERT INTO `T_PERMISSION` VALUES ('17', 'WorkOrder:list', null, '工单系统', null, '2018-06-13 11:29:53', null);
INSERT INTO `T_PERMISSION` VALUES ('18', 'WoManager:list', '17', '工单管理', null, '2018-06-13 11:31:08', null);
INSERT INTO `T_PERMISSION` VALUES ('19', 'WoConfig:list', '17', '工单设置', null, '2018-06-13 11:31:14', null);
INSERT INTO `T_PERMISSION` VALUES ('20', 'WorkOrderList:list', '18', '工单列表', null, '2018-06-13 11:31:17', null);
INSERT INTO `T_PERMISSION` VALUES ('21', 'MyWorkOrder:list', '18', '我的工单', null, '2018-06-13 11:31:19', null);
INSERT INTO `T_PERMISSION` VALUES ('22', 'ClassifySet:list', '19', '分类设置', null, '2018-06-13 11:31:23', null);
INSERT INTO `T_PERMISSION` VALUES ('23', 'Sysconfig:list', '19', '公用配置项设置', null, '2018-06-13 11:32:50', null);
INSERT INTO `T_PERMISSION` VALUES ('24', 'WorkOrder:create', '18', '创建工单', null, '2018-06-21 10:11:48', null);
INSERT INTO `T_PERMISSION` VALUES ('25', 'WorkOrder:export', '18', '导出工单列表', null, '2018-06-21 10:12:44', null);
INSERT INTO `T_PERMISSION` VALUES ('26', 'WorkOrder:approval', '18', '审核工单', null, '2018-06-21 10:22:46', null);
INSERT INTO `T_PERMISSION` VALUES ('27', 'WorkOrder:deal', '18', '处理工单', null, '2018-06-21 10:22:58', null);
INSERT INTO `T_PERMISSION` VALUES ('28', 'WorkOrder:free', '18', '释放工单', null, '2018-06-21 10:26:25', null);
INSERT INTO `T_PERMISSION` VALUES ('29', 'WorkOrder:intrust', '18', '委托工单', null, '2018-06-21 10:26:30', null);
INSERT INTO `T_PERMISSION` VALUES ('30', 'WorkOrder:back', '18', '回退工单', null, '2018-06-21 10:26:37', null);
INSERT INTO `T_PERMISSION` VALUES ('31', 'WorkOrder:close', '18', '关单', null, '2018-06-21 10:26:41', null);
INSERT INTO `T_PERMISSION` VALUES ('32', 'WorkOrder:tempSave', '18', '暂存', null, '2018-06-21 10:27:39', null);
INSERT INTO `T_PERMISSION` VALUES ('33', 'BillType:add', '19', '新增分类', null, '2018-06-21 10:34:51', null);
INSERT INTO `T_PERMISSION` VALUES ('34', 'BillBiz:add', '19', '新增业务细分', null, '2018-06-21 10:34:57', null);
INSERT INTO `T_PERMISSION` VALUES ('35', 'BillBizDetail:add', '19', '新增业务详情', null, '2018-06-21 10:35:03', null);
INSERT INTO `T_PERMISSION` VALUES ('36', 'Sysconfig:add', '19', '新增配置项', null, '2018-06-21 10:36:37', null);

-- ----------------------------
-- Table structure for T_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE`;
CREATE TABLE `T_ROLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL,
  `role_name` varchar(32) NOT NULL COMMENT '角色名',
  `status` char(1) DEFAULT '0' COMMENT '是否禁用',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_IDX_ROLE_NAME` (`role_name`),
  UNIQUE KEY `U_IDX_ROLE_CODE` (`code`),
  KEY `INDEX_ROLE_NAME` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of T_ROLE
-- ----------------------------
INSERT INTO `T_ROLE` VALUES ('1', 'admin', '系统管理员', '0', '系统管理员（请勿删除和编辑）', '2018-05-04 16:35:01', '2018-05-14 16:46:50');
INSERT INTO `T_ROLE` VALUES ('2', null, '工单部员工', '0', '', '2018-05-14 16:45:06', null);
INSERT INTO `T_ROLE` VALUES ('3', null, '客服专员(物主)', '0', '客服专员（物主）', '2018-05-15 11:17:39', '2018-07-06 19:19:00');
INSERT INTO `T_ROLE` VALUES ('4', null, '测试人员', '0', '测试', '2018-07-06 17:31:53', '2018-07-06 19:27:38');
INSERT INTO `T_ROLE` VALUES ('81', null, 'test测试', '0', 'test测试', '2018-07-06 19:39:27', '2018-07-06 19:45:09');
INSERT INTO `T_ROLE` VALUES ('82', null, '呼叫中心客服部员工', '0', '呼叫中心客服部员工', '2018-07-18 16:00:56', null);
INSERT INTO `T_ROLE` VALUES ('83', null, '呼叫中心工单部员工', '0', '呼叫中心工单部员工', '2018-07-18 16:02:23', null);
INSERT INTO `T_ROLE` VALUES ('84', null, '呼叫中心工单部管理员', '0', '呼叫中心工单部管理员', '2018-07-18 16:03:02', '2018-07-18 16:05:56');

-- ----------------------------
-- Table structure for T_ROLE_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE_DETAIL`;
CREATE TABLE `T_ROLE_DETAIL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级角色',
  `desc` varchar(500) DEFAULT NULL COMMENT '描述信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色详情';

-- ----------------------------
-- Records of T_ROLE_DETAIL
-- ----------------------------

-- ----------------------------
-- Table structure for T_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE_PERMISSION`;
CREATE TABLE `T_ROLE_PERMISSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_ROLE_PERMISSION_RID` (`role_id`),
  KEY `INDEX_ROLE_PERMISSION_PID` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=864 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of T_ROLE_PERMISSION
-- ----------------------------
INSERT INTO `T_ROLE_PERMISSION` VALUES ('650', '1', '1');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('651', '1', '2');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('652', '1', '3');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('653', '1', '4');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('654', '1', '5');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('655', '1', '6');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('656', '1', '7');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('657', '1', '8');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('658', '1', '9');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('659', '1', '10');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('660', '1', '11');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('661', '1', '12');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('662', '1', '13');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('663', '1', '14');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('664', '1', '15');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('665', '1', '16');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('666', '1', '17');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('667', '1', '18');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('668', '1', '19');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('695', '1', '20');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('696', '1', '21');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('697', '1', '22');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('698', '1', '23');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('699', '1', '24');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('700', '1', '25');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('701', '1', '26');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('702', '1', '27');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('703', '1', '28');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('704', '1', '29');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('705', '1', '30');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('706', '1', '31');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('707', '1', '32');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('708', '1', '33');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('709', '1', '34');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('710', '1', '35');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('711', '1', '36');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('717', '3', '2');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('718', '3', '1');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('719', '3', '3');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('720', '3', '4');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('721', '3', '16');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('722', '4', '2');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('723', '4', '1');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('724', '4', '3');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('725', '4', '4');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('726', '4', '16');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('763', '81', '6');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('764', '81', '8');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('765', '81', '9');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('766', '81', '10');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('767', '81', '11');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('768', '81', '12');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('769', '81', '13');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('770', '81', '14');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('771', '81', '2');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('772', '81', '1');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('773', '81', '3');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('774', '81', '4');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('775', '81', '5');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('776', '81', '7');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('777', '81', '18');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('778', '81', '20');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('779', '81', '21');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('780', '81', '24');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('781', '81', '25');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('782', '81', '26');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('783', '81', '27');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('784', '81', '28');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('785', '81', '29');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('786', '81', '30');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('787', '81', '31');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('788', '81', '32');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('789', '81', '19');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('790', '81', '22');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('791', '81', '23');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('792', '81', '33');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('793', '81', '34');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('794', '81', '35');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('795', '81', '36');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('796', '81', '15');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('797', '81', '16');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('798', '81', '17');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('799', '82', '20');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('800', '82', '18');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('801', '82', '21');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('802', '82', '24');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('803', '82', '25');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('804', '82', '17');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('805', '83', '18');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('806', '83', '20');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('807', '83', '21');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('808', '83', '24');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('809', '83', '25');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('810', '83', '26');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('811', '83', '27');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('812', '83', '28');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('813', '83', '29');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('814', '83', '30');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('815', '83', '31');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('816', '83', '32');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('817', '83', '17');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('838', '84', '20');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('839', '84', '21');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('840', '84', '24');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('841', '84', '25');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('842', '84', '26');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('843', '84', '27');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('844', '84', '28');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('845', '84', '29');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('846', '84', '30');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('847', '84', '31');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('848', '84', '32');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('849', '84', '18');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('850', '84', '22');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('851', '84', '23');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('852', '84', '33');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('853', '84', '34');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('854', '84', '35');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('855', '84', '36');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('856', '84', '19');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('857', '84', '10');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('858', '84', '6');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('859', '84', '12');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('860', '84', '13');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('861', '84', '11');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('862', '84', '15');
INSERT INTO `T_ROLE_PERMISSION` VALUES ('863', '84', '17');

-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(32) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `phoneno` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `pwd` varchar(64) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL COMMENT '固定盐',
  `status` char(1) DEFAULT '0',
  `last_login` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_INDEX_USER_PHONENO` (`phoneno`),
  UNIQUE KEY `U_IDX_USER_EMAIL` (`email`),
  UNIQUE KEY `U_IDX_USER_CODENAME` (`user_code`,`username`),
  UNIQUE KEY `U_IDX_USER_CODE` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of T_USER
-- ----------------------------
INSERT INTO `T_USER` VALUES ('1', 'admin', '系统管理员', '13148899469', 'liaoxudong@dafy.com', '548a58bede2d9957ffb70abf35984cef', '066cda101999498ab538b1f44d3174a3', '1', '2018-07-26 14:00:29', '2018-05-14 16:48:11', null);
INSERT INTO `T_USER` VALUES ('3', 'test', '测试人员', '13600000000', '123456789@qq.com', 'bc57ea97093ee0eacfa5344c2da13117', '902d524a80a648ddabdc666bbf3ba320', '1', '2018-07-23 17:12:32', '2018-04-27 15:08:18', null);
INSERT INTO `T_USER` VALUES ('46', '60030', '董娟', '18195209990', 'dongjuan@dafy.com', 'd7389cf7c4f3cd139df1c9bf4bd7c641', 'e89fe48fcec247a3bfe1020d1d9e1cbe', '1', '2018-07-11 13:55:49', '2018-05-14 16:51:23', null);
INSERT INTO `T_USER` VALUES ('47', '60102', '史梦迪', '13619569625', 'shimengdi@dafy.com', '4273939a7ce7e6b9c6c2bd1efc20eb81', '3f95403ccbfe422e957419d3cd5ff962', '1', '2018-07-26 14:20:53', '2018-05-14 16:51:49', null);
INSERT INTO `T_USER` VALUES ('48', '60066', '冯霞', '13709562454', 'fengxia@dafy.com', 'cbfbcd80aec89a7f6c5b81a24ed33c4b', 'a9ece1f4d50e47acb5e87a7abd9efcbb', '0', '2018-05-15 11:11:50', '2018-05-14 16:52:27', null);
INSERT INTO `T_USER` VALUES ('49', '60067', '谈慧', '18095246886', 'tanhui@dafy.com', '9931f967096bd28f9ad6c525153da35d', '911bfeb62e054e01b033e370e319a5e9', '0', '2018-05-15 11:11:55', '2018-05-14 16:52:50', null);
INSERT INTO `T_USER` VALUES ('50', '60042', '朱叶蕊', '15009527670', 'zhuyerui@dafy.com', '7aa5f1f247e821f5d323748e8a3462bf', '757077b89ed24e879fca9a1d0991ecfe', '0', '2018-05-15 11:12:00', '2018-05-14 17:01:34', null);
INSERT INTO `T_USER` VALUES ('51', '60091', '罗园园', '13895128155', 'luoyuanyuan@dafy.com', 'b9f04ccd7377d6a46d59cd6ebad5f785', '7a72baec94594affbc63333142243ffe', '1', '2018-07-26 13:06:31', '2018-05-15 09:43:57', null);
INSERT INTO `T_USER` VALUES ('52', '60054', '李亚楠', '18009527047', 'liyanan@dafy.com', 'fe7a457cfbbf0c7c2105c4d9ec8cc05f', 'f39238992ea54e18b1c8914718a17a03', '1', '2018-07-26 13:02:18', '2018-05-15 09:44:22', null);
INSERT INTO `T_USER` VALUES ('53', '60124', '王娇娇', '13709562671', 'wangjiaojiao@dafy.com', '4203addb6a74a2548efcd39281e21fce', '7552650498b841dab9b4918dd809d9c3', '1', '2018-07-26 10:31:58', '2018-05-15 09:44:41', null);
INSERT INTO `T_USER` VALUES ('55', 'gdadmin', '工单管理员', '1234567890', 'xxx@dafy.com', '33d8311a5413a61c224eeff163c30d36', '3bdb0227669d4141905018e95095189d', '1', '2018-07-18 16:06:11', '2018-07-18 16:04:49', null);

-- ----------------------------
-- Table structure for T_USER_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_DETAIL`;
CREATE TABLE `T_USER_DETAIL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(32) DEFAULT NULL COMMENT '全称',
  `avatar` varchar(32) DEFAULT NULL COMMENT '头像url',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

-- ----------------------------
-- Records of T_USER_DETAIL
-- ----------------------------

-- ----------------------------
-- Table structure for T_USER_ORGANIZATION
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ORGANIZATION`;
CREATE TABLE `T_USER_ORGANIZATION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织机构id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='用户与机构关系表';

-- ----------------------------
-- Records of T_USER_ORGANIZATION
-- ----------------------------
INSERT INTO `T_USER_ORGANIZATION` VALUES ('31', '1', '1');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('40', '3', '1');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('41', '46', '54');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('42', '47', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('43', '48', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('44', '49', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('45', '50', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('46', '51', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('47', '52', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('48', '53', '7');
INSERT INTO `T_USER_ORGANIZATION` VALUES ('50', '55', '5');

-- ----------------------------
-- Table structure for T_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ROLE`;
CREATE TABLE `T_USER_ROLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_USER_ROLE_UID` (`user_id`),
  KEY `INDEX_USER_ROLE_RID` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of T_USER_ROLE
-- ----------------------------
INSERT INTO `T_USER_ROLE` VALUES ('42', '1', '1');
INSERT INTO `T_USER_ROLE` VALUES ('58', '3', '81');
INSERT INTO `T_USER_ROLE` VALUES ('59', '46', '1');
INSERT INTO `T_USER_ROLE` VALUES ('60', '47', '3');
INSERT INTO `T_USER_ROLE` VALUES ('61', '48', '3');
INSERT INTO `T_USER_ROLE` VALUES ('62', '49', '3');
INSERT INTO `T_USER_ROLE` VALUES ('63', '50', '3');
INSERT INTO `T_USER_ROLE` VALUES ('64', '51', '3');
INSERT INTO `T_USER_ROLE` VALUES ('65', '52', '3');
INSERT INTO `T_USER_ROLE` VALUES ('66', '53', '3');
INSERT INTO `T_USER_ROLE` VALUES ('68', '55', '84');
