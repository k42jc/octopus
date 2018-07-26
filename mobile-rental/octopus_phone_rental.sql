/*
Navicat MySQL Data Transfer

Source Server         : xjyc-svr1
Source Server Version : 50722
Source Host           : xjyc-svr1:3306
Source Database       : octopus_phone_rental

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-26 14:38:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for deal_status
-- ----------------------------
DROP TABLE IF EXISTS `deal_status`;
CREATE TABLE `deal_status` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '处理状态名称',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='处理状态';

-- ----------------------------
-- Records of deal_status
-- ----------------------------
INSERT INTO `deal_status` VALUES ('1', '已解决', '2018-05-02 14:52:05', '2018-05-08 14:46:24');
INSERT INTO `deal_status` VALUES ('2', '转工单', '2018-05-02 14:52:12', '2018-05-08 14:46:30');
INSERT INTO `deal_status` VALUES ('6', '未解决', '2018-05-08 14:46:59', null);

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) unsigned DEFAULT '0' COMMENT '父ID，一级目录的parentId为0',
  `src_name` varchar(32) DEFAULT NULL COMMENT '原名称',
  `cur_name` varchar(32) DEFAULT NULL COMMENT '当前名称',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='商品类型';

-- ----------------------------
-- Records of goods_type
-- ----------------------------
INSERT INTO `goods_type` VALUES ('4', '3', null, '二级', '2018-05-02 14:55:04', null);
INSERT INTO `goods_type` VALUES ('6', '5', null, '四级', '2018-05-02 14:56:47', null);
INSERT INTO `goods_type` VALUES ('7', '6', null, '五级', '2018-05-02 14:57:08', null);
INSERT INTO `goods_type` VALUES ('8', '1', null, '苹果', '2018-05-07 14:51:17', null);
INSERT INTO `goods_type` VALUES ('25', '0', null, '分期前', '2018-05-10 13:12:56', null);
INSERT INTO `goods_type` VALUES ('26', '0', null, '分期中', '2018-05-10 13:13:02', null);
INSERT INTO `goods_type` VALUES ('27', '0', null, '分期后', '2018-05-10 13:13:07', null);
INSERT INTO `goods_type` VALUES ('28', '0', null, '商户', '2018-05-10 13:13:23', null);
INSERT INTO `goods_type` VALUES ('29', '0', null, '店员', '2018-05-10 13:13:27', null);
INSERT INTO `goods_type` VALUES ('30', '0', null, 'BD', '2018-05-10 13:13:32', null);
INSERT INTO `goods_type` VALUES ('31', '0', null, '服务类', '2018-05-10 13:13:38', null);
INSERT INTO `goods_type` VALUES ('32', '0', null, '活动资讯', '2018-05-10 13:13:43', null);
INSERT INTO `goods_type` VALUES ('33', '0', null, '其他', '2018-05-10 13:13:46', null);
INSERT INTO `goods_type` VALUES ('34', '25', null, '审核速度问题', '2018-05-10 13:14:00', null);
INSERT INTO `goods_type` VALUES ('35', '25', null, '提额问题', '2018-05-10 13:14:05', null);
INSERT INTO `goods_type` VALUES ('36', '25', null, '认证问题', '2018-05-10 13:14:08', null);
INSERT INTO `goods_type` VALUES ('37', '25', null, '程序异常', '2018-05-10 13:14:12', null);
INSERT INTO `goods_type` VALUES ('38', '25', null, '收不到验证码', '2018-05-10 13:14:16', null);
INSERT INTO `goods_type` VALUES ('39', '25', null, '操作问题', '2018-05-10 13:14:20', null);
INSERT INTO `goods_type` VALUES ('40', '25', null, '修改信息问题', '2018-05-10 13:14:23', null);
INSERT INTO `goods_type` VALUES ('41', '25', null, '注销账户', '2018-05-10 13:14:26', null);
INSERT INTO `goods_type` VALUES ('42', '25', null, 'APP异常问题', '2018-05-10 13:14:28', null);
INSERT INTO `goods_type` VALUES ('43', '26', null, '提货问题', '2018-05-10 13:14:34', null);
INSERT INTO `goods_type` VALUES ('44', '26', null, '合同问题', '2018-05-10 13:14:38', null);
INSERT INTO `goods_type` VALUES ('45', '26', null, '还款问题', '2018-05-10 13:14:40', null);
INSERT INTO `goods_type` VALUES ('46', '26', null, '逾期问题', '2018-05-10 13:14:43', null);
INSERT INTO `goods_type` VALUES ('47', '26', null, '手续费问题', '2018-05-10 13:14:47', null);
INSERT INTO `goods_type` VALUES ('48', '26', null, '退货问题', '2018-05-10 13:14:51', null);
INSERT INTO `goods_type` VALUES ('49', '26', null, '催收问题', '2018-05-10 13:14:59', null);
INSERT INTO `goods_type` VALUES ('51', '27', null, '提货问题', '2018-05-10 13:15:18', null);
INSERT INTO `goods_type` VALUES ('52', '27', null, '还款问题', '2018-05-10 13:15:21', null);
INSERT INTO `goods_type` VALUES ('53', '28', null, '结算问题', '2018-05-10 13:15:28', null);
INSERT INTO `goods_type` VALUES ('54', '28', null, '退货款问题', '2018-05-10 13:15:33', null);
INSERT INTO `goods_type` VALUES ('55', '29', null, '佣金问题', '2018-05-10 13:15:38', null);
INSERT INTO `goods_type` VALUES ('56', '30', null, '商户准入流程问题', '2018-05-10 13:15:42', null);
INSERT INTO `goods_type` VALUES ('57', '30', null, '商户审核问题', '2018-05-10 13:15:45', null);
INSERT INTO `goods_type` VALUES ('58', '31', null, '服务问题', '2018-05-10 13:15:52', null);
INSERT INTO `goods_type` VALUES ('59', '31', null, '各类建议', '2018-05-10 13:15:56', null);
INSERT INTO `goods_type` VALUES ('60', '32', null, '近期活动', '2018-05-10 13:16:00', null);
INSERT INTO `goods_type` VALUES ('61', '33', null, '加盟合作', '2018-05-10 13:16:04', null);
INSERT INTO `goods_type` VALUES ('62', '33', null, '其他问题', '2018-05-10 13:16:08', null);
INSERT INTO `goods_type` VALUES ('63', '34', null, '审核速度问题1', '2018-05-10 13:17:23', null);
INSERT INTO `goods_type` VALUES ('64', '34', null, '审核速度问题2', '2018-05-10 13:17:31', null);
INSERT INTO `goods_type` VALUES ('65', '63', null, '审核速度问题1-1', '2018-05-10 13:17:43', null);
INSERT INTO `goods_type` VALUES ('66', '65', '审核速度问题1-2', '审核速度问题1-1-1', '2018-05-10 13:17:48', '2018-05-10 13:18:13');
INSERT INTO `goods_type` VALUES ('67', '63', null, '审核速度问题1-2', '2018-05-10 13:18:07', null);
INSERT INTO `goods_type` VALUES ('68', '65', null, '审核速度问题1-1-2', '2018-05-10 13:18:23', null);

-- ----------------------------
-- Table structure for order_handle_info
-- ----------------------------
DROP TABLE IF EXISTS `order_handle_info`;
CREATE TABLE `order_handle_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户姓名',
  `user_phone` varchar(32) DEFAULT NULL COMMENT '用户手机号',
  `worker_code` varchar(32) DEFAULT NULL COMMENT '客服工号',
  `handler_id` varchar(32) DEFAULT NULL COMMENT '客服ID',
  `handle_type_name` varchar(32) DEFAULT NULL COMMENT '操作内容/操作类型',
  `handle_reason` varchar(32) DEFAULT NULL COMMENT '操作原因',
  `handle_ip_address` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='记录客户订单操作记录';

-- ----------------------------
-- Records of order_handle_info
-- ----------------------------
INSERT INTO `order_handle_info` VALUES ('5', '00120180602M000130368128', '201806020831101', '陈俊', '15956515729', '60124', '53', '取消订单', '拍错商品，重新下单', '10.33.2.18', '2018-06-02 14:51:36', null);
INSERT INTO `order_handle_info` VALUES ('6', '00120180606M003601093013', '201805181624401', '翟福林', '15656668444', '60091', '51', '取消订单', '商品拍错 重新下单', '10.33.2.96', '2018-06-07 12:20:46', null);
INSERT INTO `order_handle_info` VALUES ('7', '00220180614M000696949567', '201806142313331', '夏亚军', '15815536864', '60054', '52', '取消订单', '不想租赁', '10.33.2.12', '2018-06-15 14:59:56', null);
INSERT INTO `order_handle_info` VALUES ('8', '00220180614M003825562021', '201806141343461', '胡遇龙', '13823507073', '60054', '52', '取消订单', '不想租赁', '10.33.2.12', '2018-06-15 15:08:49', null);
INSERT INTO `order_handle_info` VALUES ('9', '00220180614Q009665326282', '201806141135221', '牛东林', '17620376876', '60054', '52', '取消订单', '下错单，要求重下', '10.33.2.12', '2018-06-15 15:10:16', null);
INSERT INTO `order_handle_info` VALUES ('10', '00220180615M004863460675', '201806142313361', '毛海宾', '15919465994', '60054', '52', '取消订单', '测试订单', '10.33.2.12', '2018-06-15 15:20:28', null);
INSERT INTO `order_handle_info` VALUES ('11', '00220180614P005680728169', '201806142314041', '郭祖渊', '18123942504', '60091', '51', '取消订单', '内部员工测线  取消订单', '10.33.2.96', '2018-06-15 15:20:46', null);
INSERT INTO `order_handle_info` VALUES ('12', '00120180613M004122830047', '201806131754361', '高鹏', '18734910165', '60091', '51', '取消订单', '不想租赁  直接购买', '10.33.2.96', '2018-06-17 11:54:20', null);
INSERT INTO `order_handle_info` VALUES ('13', '00120180613M004115313838', '201806132018311', '李鑫', '18435179663', '60102', '47', '取消订单', '用户表示价位不合适，要求取消。', '10.33.2.37', '2018-06-17 16:25:26', null);
INSERT INTO `order_handle_info` VALUES ('14', '00220180619M005090047678', '201806191044231', '杨文强', '15167103027', '60054', '52', '取消订单', '内部测试订单', '10.33.2.12', '2018-06-19 10:58:20', null);
INSERT INTO `order_handle_info` VALUES ('15', '00220180619Q009820337545', '201806191459431', '牛东林', '17620376876', '60124', '53', '取消订单', '拍错商品，取消订单', '10.33.2.18', '2018-06-19 15:45:19', null);
INSERT INTO `order_handle_info` VALUES ('16', '00220180619C003233691839', '201806191519001', '吴彬鹏', '15818521932', '60124', '53', '取消订单', '内部员工取消订单', '10.33.2.18', '2018-06-19 15:53:12', null);
INSERT INTO `order_handle_info` VALUES ('17', '00220180619C007115755606', '201806141343461', '胡遇龙', '13823507073', '60124', '53', '取消订单', '内部员工', '10.33.2.18', '2018-06-20 14:47:21', null);
INSERT INTO `order_handle_info` VALUES ('18', '00120180620P005893811802', '201806091844461', '舒童', '16605652106', '60054', '52', '取消订单', '地址错误，需要重新下单', '10.33.2.12', '2018-06-26 18:05:01', null);
INSERT INTO `order_handle_info` VALUES ('19', '00220180626M005894961648', '201806262207422', '李明慧', '15751836996', '60102', '47', '取消订单', '测试账号', '10.33.2.37', '2018-06-27 11:06:43', null);
INSERT INTO `order_handle_info` VALUES ('20', '00220180626Q009930161722', '201806191519001', '吴彬鹏', '15818521932', '60091', '51', '取消订单', '测试账号', '10.33.2.96', '2018-06-27 11:06:54', null);
INSERT INTO `order_handle_info` VALUES ('21', '00220180626Q001236125374', '201806252024141', '罗家裕', '13560722439', '60102', '47', '取消订单', '测试账号', '10.33.2.37', '2018-06-27 11:07:23', null);
INSERT INTO `order_handle_info` VALUES ('22', '00220180626M006074121124', '201806191519001', '吴彬鹏', '15818521932', '60091', '51', '取消订单', '测试账号', '10.33.2.96', '2018-06-27 11:07:24', null);
INSERT INTO `order_handle_info` VALUES ('23', '00220180626M004475067590', '201806252212331', '吕艳娟', '18682079176', '60102', '47', '取消订单', '测试账号', '10.33.2.37', '2018-06-27 11:08:00', null);
INSERT INTO `order_handle_info` VALUES ('24', '00220180626M000593665535', '201806261143101', '游雪晴', '13924619515', '60091', '51', '取消订单', '测试账号', '10.33.2.96', '2018-06-27 11:08:16', null);
INSERT INTO `order_handle_info` VALUES ('25', '00220180626Q003478886429', '201806252212331', '吕艳娟', '18682079176', '60102', '47', '取消订单', '测试账号', '10.33.2.37', '2018-06-27 11:08:21', null);
INSERT INTO `order_handle_info` VALUES ('26', '00220180626Q000746801096', '201806252013541', '袁甜', '18692273284', '60091', '51', '取消订单', '测试账号', '10.33.2.96', '2018-06-27 11:08:48', null);
INSERT INTO `order_handle_info` VALUES ('27', '00220180626M004181447032', '201806141343461', '胡遇龙', '13823507073', '60102', '47', '取消订单', '测试账号', '10.33.2.37', '2018-06-27 11:09:07', null);
INSERT INTO `order_handle_info` VALUES ('28', '00220180626M004499343070', '201806261342181', '宗圣威', '17612755521', '60091', '51', '取消订单', '测试账号', '10.33.2.96', '2018-06-27 11:09:31', null);
INSERT INTO `order_handle_info` VALUES ('29', '00120180624P003226087690', '201805312211341', '张祎超', '15195773385', '60102', '47', '取消订单', '发货太慢，用户已经不需要了', '10.33.2.37', '2018-06-27 11:34:29', null);
INSERT INTO `order_handle_info` VALUES ('30', '00220180627M007273634242', '201806262045231', '徐四成', '15623813618', '60091', '51', '取消订单', '内部员工测线', '10.33.2.96', '2018-06-27 12:17:52', null);
INSERT INTO `order_handle_info` VALUES ('31', '00120180626C007219303281', '201806091222001', '付品欣', '18025853669', '60091', '51', '取消订单', '内部员工测线', '10.33.2.96', '2018-06-27 12:22:44', null);
INSERT INTO `order_handle_info` VALUES ('32', '00120180627Q014742306239', '201806091222001', '付品欣', '18025853669', '60091', '51', '取消订单', '内部员工测线', '10.33.2.96', '2018-06-27 12:23:00', null);
INSERT INTO `order_handle_info` VALUES ('33', '00220180627Q018194501147', '201806271215391', '邓鹏', '15071356806', '60102', '47', '取消订单', '收货地址填错，重新下单', '10.33.2.37', '2018-06-27 13:44:55', null);
INSERT INTO `order_handle_info` VALUES ('34', '00120180627Q008895449321', '201806271849101', '赵崧淞', '15893002975', '60054', '52', '取消订单', '客户反馈下错订单，要求取消', '10.33.2.12', '2018-06-27 21:21:04', null);
INSERT INTO `order_handle_info` VALUES ('35', '00220180629M010526152267', '201806291205391', '周晨', '18805198780', '60124', '53', '取消订单', '拍错商品', '10.33.2.18', '2018-06-29 12:44:04', null);
INSERT INTO `order_handle_info` VALUES ('36', '00220180630Q018802787586', '201806292237441', '林娟', '18859268180', '60124', '53', '取消订单', '内存太小', '10.33.2.18', '2018-06-30 10:39:26', null);
INSERT INTO `order_handle_info` VALUES ('37', '00220180630Q019092114623', '201806292237441', '林娟', '18859268180', '60124', '53', '取消订单', '高出市场价格', '10.33.2.18', '2018-06-30 16:38:51', null);
INSERT INTO `order_handle_info` VALUES ('38', '00220180704M010586204580', '201807041110531', '陈树东', '18861933961', '60124', '53', '取消订单', '颜色错误', '10.33.2.18', '2018-07-04 20:24:27', null);
INSERT INTO `order_handle_info` VALUES ('39', '00220180704M010529790601', '201807041110531', '陈树东', '18861933961', '60124', '53', '取消订单', '填错，重新下单', '10.33.2.18', '2018-07-04 20:51:01', null);
INSERT INTO `order_handle_info` VALUES ('40', '00220180706M011213700678', '201807061406141', '刘洋', '18780134418', '60054', '52', '取消订单', '订单地址错，商品拍错', '10.33.2.12', '2018-07-06 17:38:35', null);
INSERT INTO `order_handle_info` VALUES ('41', '00220180707M007694899455', '201807071210431', '赵军生', '13203862612', '60091', '51', '取消订单', '用户校园合伙人，想通过支付宝熟悉一下流程，不小心下单。', '10.33.2.96', '2018-07-07 12:41:44', null);
INSERT INTO `order_handle_info` VALUES ('42', '00220180707M010848124065', '201807061046381', '周璐', '18373375906', '60091', '51', '取消订单', '多拍一个同款手机', '10.33.2.96', '2018-07-07 13:08:01', null);
INSERT INTO `order_handle_info` VALUES ('43', '00220180707M011409048408', '201807070833202', '赵宇', '18227308000', '60091', '51', '取消订单', '拍错', '10.33.2.96', '2018-07-07 15:05:58', null);
INSERT INTO `order_handle_info` VALUES ('44', '00220180708Q021674861656', '201807080257261', '花敏', '18805166082', '60091', '51', '取消订单', '用户不需要租赁了', '10.33.2.96', '2018-07-08 13:02:25', null);
INSERT INTO `order_handle_info` VALUES ('45', '00220180708M013537028316', '201807081023471', '李凯科', '18303218648', '60124', '53', '取消订单', '客户没搞清楚下单要求取消', '10.33.2.18', '2018-07-08 15:46:23', null);
INSERT INTO `order_handle_info` VALUES ('46', '00220180709M007300977419', '201807041107471', '何奇', '18988223747', '60091', '51', '取消订单', '拍错商品', '10.33.2.96', '2018-07-09 12:57:41', null);
INSERT INTO `order_handle_info` VALUES ('47', '00220180708M008034449768', '201807071903561', '解富林', '13458532157', '60102', '47', '取消订单', '收货地址填写错误，等需要时再次下单', '10.33.2.37', '2018-07-09 16:14:16', null);
INSERT INTO `order_handle_info` VALUES ('48', '00220180709M016924321661', '201807071058531', '王瑶瑶', '13523851377', '60091', '51', '取消订单', '个人原因', '10.33.2.96', '2018-07-09 16:15:22', null);
INSERT INTO `order_handle_info` VALUES ('49', '00220180709M013283945284', '201807061552521', '刘宇', '18697039612', '60091', '51', '取消订单', '用户表示资金短缺', '10.33.2.96', '2018-07-09 17:15:58', null);
INSERT INTO `order_handle_info` VALUES ('50', '00220180709M016940782909', '201807071058531', '王瑶瑶', '13523851377', '60091', '51', '取消订单', '个人原因', '10.33.2.96', '2018-07-09 17:26:19', null);
INSERT INTO `order_handle_info` VALUES ('51', '00220180709M016906241426', '201807071242151', '丁小兵', '15083777474', '60091', '51', '取消订单', '拍错从新下单', '10.33.2.96', '2018-07-09 18:14:00', null);
INSERT INTO `order_handle_info` VALUES ('52', '00220180709M007207491294', '201806261024201', '李思齐', '13590354830', '60124', '53', '取消订单', '无原因', '10.33.2.18', '2018-07-09 20:55:24', null);
INSERT INTO `order_handle_info` VALUES ('53', '00220180710M008048298403', '201807061651441', '冯贵宏', '14759398507', '60054', '52', '取消订单', '地址错，暂时不需要租赁商品', '10.33.2.12', '2018-07-10 10:05:49', null);
INSERT INTO `order_handle_info` VALUES ('54', '00220180709M007250457997', '201807092014501', '张晟', '15856944490', '60054', '52', '取消订单', '拍错商品 从新下单', '10.33.2.12', '2018-07-10 10:21:49', null);
INSERT INTO `order_handle_info` VALUES ('55', '00220180709M013668695557', '201807091137241', '梁晟', '18636102568', '60054', '52', '取消订单', '用户己电话沟通更换128G', '10.33.2.12', '2018-07-10 15:35:50', null);
INSERT INTO `order_handle_info` VALUES ('56', '00220180710M013292785040', '201807102117321', '谢俊琦', '17791185330', '60054', '52', '取消订单', '地址错，暂时不需要商品，需要取消', '10.33.2.12', '2018-07-11 10:26:31', null);
INSERT INTO `order_handle_info` VALUES ('57', '00220180711M011191129126', '201807111444371', '杨建', '15938687595', '60124', '53', '取消订单', '拍错商品 ', '10.33.2.18', '2018-07-11 16:10:05', null);
INSERT INTO `order_handle_info` VALUES ('58', '00220180711C014753322814', '201807070915051', '蔡淼', '13600316957', '60124', '53', '取消订单', '内存太小', '10.33.2.18', '2018-07-11 16:35:39', null);
INSERT INTO `order_handle_info` VALUES ('59', '00220180711M007651954052', '201807101349081', '段聪', '17721251084', '60124', '53', '取消订单', '尝试操作，不想租赁', '10.33.2.18', '2018-07-11 16:36:28', null);
INSERT INTO `order_handle_info` VALUES ('60', '00220180711M016979432531', '201807081052111', '王鑫', '13045526023', '60054', '52', '取消订单', '不想要了', '10.33.2.12', '2018-07-12 12:38:54', null);
INSERT INTO `order_handle_info` VALUES ('61', '00220180712M007258649285', '201807122045271', '陈骁', '13226812222', '60102', '47', '取消订单', '小孩子不小心点错了', '10.33.2.37', '2018-07-12 21:43:04', null);
INSERT INTO `order_handle_info` VALUES ('62', '00220180713M008085569195', '201807071003151', '杜玮', '17868803564', '60124', '53', '取消订单', '拍错商品', '10.33.2.18', '2018-07-14 11:17:52', null);
INSERT INTO `order_handle_info` VALUES ('63', '00220180713M011376471589', '201807122204021', '张琨', '13322256664', '60054', '52', '取消订单', '不要了', '10.33.2.12', '2018-07-14 13:42:44', null);
INSERT INTO `order_handle_info` VALUES ('64', '00220180714C015297219601', '201807101707331', '管新万', '13739235224', '60124', '53', '取消订单', '商品拍错，中再次下单', '10.33.2.18', '2018-07-14 16:09:59', null);
INSERT INTO `order_handle_info` VALUES ('65', '00220180714M011501226549', '201807131112371', '赵东洋', '13767101231', '60102', '47', '取消订单', '用户拍错机型', '10.33.2.37', '2018-07-15 09:56:40', null);
INSERT INTO `order_handle_info` VALUES ('66', '00220180715M013289449116', '201807150229061', '邵传斌', '15254893346', '60102', '47', '取消订单', '用户表示拍错机型，要求取消', '10.33.2.37', '2018-07-15 11:18:26', null);
INSERT INTO `order_handle_info` VALUES ('67', '00220180713M009087352684', '201807081054491', '郭兴光', '15127803706', '60102', '47', '取消订单', '用户表示发货太慢了，要求取消', '10.33.2.37', '2018-07-15 11:54:27', null);
INSERT INTO `order_handle_info` VALUES ('68', '00220180715M010331741011', '201807150759421', '郭吉翔', '13834480263', '60102', '47', '取消订单', '收货地址填写错误', '10.33.2.37', '2018-07-15 12:12:27', null);
INSERT INTO `order_handle_info` VALUES ('69', '00220180716M011707247453', '201807161110321', '郑泽文', '13210575661', '60124', '53', '取消订单', '重复下单', '10.33.2.18', '2018-07-16 12:55:51', null);
INSERT INTO `order_handle_info` VALUES ('70', '00220180715M020080395972', '201807151350351', '郭宁静', '15501585903', '60124', '53', '取消订单', '重复拍错。', '10.33.2.18', '2018-07-16 14:54:50', null);
INSERT INTO `order_handle_info` VALUES ('71', '00220180716Q027301689965', '201806252027022', '刘伶艳', '18674701656', '60124', '53', '取消订单', '拍错商品', '10.33.2.18', '2018-07-16 15:01:58', null);
INSERT INTO `order_handle_info` VALUES ('72', '00220180716M018587790370', '201807161459191', '阳珍', '15118397873', '60102', '47', '取消订单', '租期太长', '10.33.2.37', '2018-07-16 16:51:36', null);
INSERT INTO `order_handle_info` VALUES ('73', '00220180715M011787561340', '201807110117441', '杜军', '18055535837', '60091', '51', '取消订单', '用户称发货太慢', '10.33.2.96', '2018-07-17 10:22:15', null);
INSERT INTO `order_handle_info` VALUES ('74', '00220180717M013297253360', '201807171528401', '胡伟', '18955507333', '60124', '53', '取消订单', '拍错商品', '10.33.2.18', '2018-07-17 17:45:06', null);
INSERT INTO `order_handle_info` VALUES ('75', '00220180717M013266801218', '201807170620411', '张钦', '18380996947', '60124', '53', '取消订单', '拍错商品', '10.33.2.18', '2018-07-17 18:28:04', null);
INSERT INTO `order_handle_info` VALUES ('76', '00220180717M021148841453', '201807171534191', '白尚辉', '15035180865', '60054', '52', '取消订单', '订单错，取消重新下', '10.33.2.12', '2018-07-17 21:19:52', null);
INSERT INTO `order_handle_info` VALUES ('77', '00220180718Q029179644864', '201807181058341', '黄育彬', '13112118448', '60091', '51', '取消订单', '版本太低', '10.33.2.96', '2018-07-18 11:15:37', null);
INSERT INTO `order_handle_info` VALUES ('78', '00220180718M011310657162', '201807180059141', '张维康', '18434392567', '60091', '51', '取消订单', '家里买了一部同样的手机，不需要租赁。', '10.33.2.96', '2018-07-19 12:21:19', null);
INSERT INTO `order_handle_info` VALUES ('79', '00220180719M019919360344', '201807192302421', '汤小燕', '13716236311', '60102', '47', '取消订单', '租期太长', '10.33.2.37', '2018-07-20 09:49:08', null);
INSERT INTO `order_handle_info` VALUES ('80', '00220180720M007246944007', '201807201604521', '穆沂', '15601821426', '60124', '53', '取消订单', '想租赁其他的商品', '10.33.2.18', '2018-07-20 16:36:10', null);
INSERT INTO `order_handle_info` VALUES ('81', '00220180720M007265786495', '201807202258341', '刘庆', '17719815769', '60102', '47', '取消订单', '拍错机型', '10.33.2.37', '2018-07-21 09:12:58', null);
INSERT INTO `order_handle_info` VALUES ('82', '00220180721M027432542840', '201807211736241', '穆峰', '17682473268', '60102', '47', '取消订单', '租期太长', '10.33.2.37', '2018-07-21 18:04:32', null);
INSERT INTO `order_handle_info` VALUES ('83', '00220180721M027244118480', '201807211017101', '刘洋', '18101059430', '60124', '53', '取消订单', '不需要这个商品', '10.33.2.18', '2018-07-21 21:44:01', null);
INSERT INTO `order_handle_info` VALUES ('84', '00220180722M027265674789', '201807220824321', '薛敬恩', '13103595448', '60054', '52', '取消订单', '下错订单', '10.33.2.12', '2018-07-22 10:08:34', null);
INSERT INTO `order_handle_info` VALUES ('85', '00220180720M011308066464', '201807202332141', '张耀辉', '18269779391', '60102', '47', '取消订单', '客户表示发货慢、买断价格贵', '10.33.2.37', '2018-07-22 10:13:28', null);
INSERT INTO `order_handle_info` VALUES ('86', '00220180721M007535632496', '201807212305491', '刁尔萨', '13782397752', '60102', '47', '取消订单', '经致电客服了解到提前退还后期租金仍需支付', '10.33.2.37', '2018-07-22 10:14:04', null);
INSERT INTO `order_handle_info` VALUES ('87', '00220180722Q020746167118', '201807131038131', '陈丹妮', '15157895068', '60102', '47', '取消订单', '拍错订单，要求取消', '10.33.2.37', '2018-07-22 12:13:16', null);
INSERT INTO `order_handle_info` VALUES ('88', '00220180722M008778984972', '201807221204171', '黄旭初', '17605038017', '60054', '52', '取消订单', '下错订单', '10.33.2.12', '2018-07-22 12:25:02', null);
INSERT INTO `order_handle_info` VALUES ('89', '00120180722Q029637686873', '201805181125091', '于明亮', '18816217919', '60102', '47', '取消订单', '最近要到远的地方，租了玩不了', '10.33.2.37', '2018-07-22 13:33:46', null);
INSERT INTO `order_handle_info` VALUES ('90', '00220180722Q020712814588', '201807171728471', '刘乐', '13810376987', '60102', '47', '取消订单', '已在实体店进行购买相同产品', '10.33.2.37', '2018-07-22 16:28:16', null);
INSERT INTO `order_handle_info` VALUES ('91', '00220180724M022315488426', '201807241040391', '王伟东', '15203466928', '60091', '51', '取消订单', '拍错商品', '10.33.2.93', '2018-07-24 11:33:25', null);
INSERT INTO `order_handle_info` VALUES ('92', '00220180723M008987611397', '201807192200231', '王建坤', '13421718459', '60091', '51', '取消订单', '担心后期若有损坏会进行维修，维修期间使用不了手机', '10.33.2.93', '2018-07-24 12:19:24', null);
INSERT INTO `order_handle_info` VALUES ('93', '00220180722M027212456252', '201807172326321', '杨键', '18565687997', '60102', '47', '取消订单', '不想租赁', '10.33.2.78', '2018-07-24 15:09:45', null);
INSERT INTO `order_handle_info` VALUES ('94', '00220180723C013493383637', '201807121620581', '徐浩', '13409656059', '60124', '53', '取消订单', '没货', '10.33.2.34', '2018-07-24 17:05:54', null);
INSERT INTO `order_handle_info` VALUES ('95', '00220180722M008659788490', '201807221647011', '陈燕玲', '13635282918', '60124', '53', '取消订单', '拍错商品', '10.33.2.34', '2018-07-24 17:18:36', null);
INSERT INTO `order_handle_info` VALUES ('96', '00220180724C012808320958', '201807230728391', '邓晓飞', '18575065687', '60091', '51', '取消订单', '租期选错，从新下单。', '10.33.2.93', '2018-07-24 19:21:25', null);
INSERT INTO `order_handle_info` VALUES ('97', '00220180724M027238364528', '201807242036441', '张永建', '18793049700', '60091', '51', '取消订单', '地址有误，重新下单。', '10.33.2.93', '2018-07-24 21:06:56', null);
INSERT INTO `order_handle_info` VALUES ('98', '00220180726M027236652093', '201807261024321', '孙振宇', '17853726102', '60091', '51', '取消订单', '查询到该款手机玩游戏发热严重，从新租赁其他机型。', '10.33.2.93', '2018-07-26 13:16:26', null);
INSERT INTO `order_handle_info` VALUES ('99', '00220180725M027285808777', '201807251457421', '黄景松', '18877704029', '60102', '47', '取消订单', '租金不合适', '10.33.2.78', '2018-07-26 13:32:45', null);

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '产品类型名称',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='产品类型';

-- ----------------------------
-- Records of product_type
-- ----------------------------
INSERT INTO `product_type` VALUES ('1', '物主项目', '2018-05-02 14:48:28', '2018-05-10 13:11:48');
INSERT INTO `product_type` VALUES ('2', '云贷项目', '2018-05-02 14:48:35', '2018-05-10 13:11:55');
INSERT INTO `product_type` VALUES ('7', '七贷项目', '2018-05-08 11:49:06', '2018-05-10 13:12:03');

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `server_code` varchar(32) DEFAULT NULL COMMENT '案例编号',
  `server_order_code` varchar(32) DEFAULT NULL COMMENT '工单编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `handler_id` varchar(32) DEFAULT NULL COMMENT '处理人ID',
  `source_type_name` varchar(32) DEFAULT NULL COMMENT '来源类型名称',
  `product_type_name` varchar(32) DEFAULT NULL COMMENT '产品类型名称',
  `goods_type_name` varchar(32) DEFAULT NULL COMMENT '商品类型名称',
  `deal_record_content` varchar(1024) DEFAULT NULL COMMENT '客服处理记录',
  `deal_status_name` varchar(32) DEFAULT NULL COMMENT '处理状态名称',
  `source_type_id` int(10) unsigned DEFAULT NULL COMMENT '来源类型ID',
  `product_type_id` int(10) unsigned DEFAULT NULL COMMENT '产品类型ID',
  `goods_type_id` int(10) unsigned DEFAULT NULL COMMENT '商品类型ID',
  `deal_status_id` int(10) unsigned DEFAULT NULL COMMENT '处理状态ID',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `worker_code` varchar(32) DEFAULT NULL COMMENT '客服工号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服服务记录';

-- ----------------------------
-- Records of server_info
-- ----------------------------

-- ----------------------------
-- Table structure for server_info_goods_type
-- ----------------------------
DROP TABLE IF EXISTS `server_info_goods_type`;
CREATE TABLE `server_info_goods_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) unsigned DEFAULT NULL COMMENT '商品ID',
  `server_info_id` bigint(20) unsigned DEFAULT NULL COMMENT '客户服务ID',
  `level_index` int(10) unsigned DEFAULT NULL COMMENT '商品分级序列',
  `name` varchar(32) DEFAULT NULL COMMENT '商品名称',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户记录的各级商品名称';

-- ----------------------------
-- Records of server_info_goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for source_type
-- ----------------------------
DROP TABLE IF EXISTS `source_type`;
CREATE TABLE `source_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '工单来源类型名称',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='工单来源类型';

-- ----------------------------
-- Records of source_type
-- ----------------------------
INSERT INTO `source_type` VALUES ('1', '呼入', '2018-04-28 17:08:28', '2018-05-08 15:17:19');
INSERT INTO `source_type` VALUES ('3', '呼出', '2018-05-08 11:20:45', '2018-05-08 15:17:24');
INSERT INTO `source_type` VALUES ('6', '在线', '2018-05-08 14:42:34', '2018-05-08 15:17:29');
