CREATE DATABASE octopus_phone_rental
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE octopus_phone_rental;

-- -----------------------------------------------------
-- Table `loan`.`deal_status` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`deal_status` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`deal_status` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NULL COMMENT '处理状态名称',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '处理状态';

-- -----------------------------------------------------
-- Table `loan`.`goods_type` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`goods_type` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`goods_type` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` bigint unsigned DEFAULT 0 COMMENT '父ID，一级目录的parentId为0',
  `src_name` VARCHAR(32) NULL COMMENT '原名称',
  `cur_name` VARCHAR(32) NULL COMMENT '当前名称',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '商品类型';

-- -----------------------------------------------------
-- Table `loan`.`product_type` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`product_type` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`product_type` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NULL COMMENT '产品类型名称',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '产品类型';

-- -----------------------------------------------------
-- Table `loan`.`source_type` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`source_type` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`source_type` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NULL COMMENT '工单来源类型名称',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '工单来源类型';

-- -----------------------------------------------------
-- Table `loan`.`source_type` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`server_info` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`server_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `server_code` VARCHAR(32) NULL COMMENT '案例编号',
  `server_order_code` VARCHAR(32) NULL COMMENT '工单编号',
  `user_id` VARCHAR(32) NULL COMMENT '客户ID',
  `handler_id` VARCHAR(32) NULL COMMENT '处理人ID',
  `source_type_name` VARCHAR(32) NULL COMMENT '来源类型名称',
  `product_type_name` VARCHAR(32) NULL COMMENT '产品类型名称',
  `worker_code` VARCHAR(32) NULL COMMENT '客服工号',
  `goods_type_name` VARCHAR(32) NULL COMMENT '商品类型名称',
  `deal_record_content` VARCHAR(1024) NULL COMMENT '客服处理记录',
  `deal_status_name` VARCHAR(32) NULL COMMENT '处理状态名称',
  `source_type_id` INT unsigned NULL COMMENT '来源类型ID',
  `product_type_id` INT unsigned NULL COMMENT '产品类型ID',
  `goods_type_id` INT unsigned NULL COMMENT '商品类型ID',
  `deal_status_id` INT unsigned NULL COMMENT '处理状态ID',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '客服服务记录';

-- -----------------------------------------------------
-- Table `loan`.`source_type` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`server_info_goods_type` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`server_info_goods_type` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint unsigned NULL COMMENT '商品ID',
  `server_info_id` bigint unsigned NULL COMMENT '客户服务ID',
  `level_index` INT unsigned NULL COMMENT '商品分级序列',
  `name` VARCHAR(32) NULL COMMENT '商品名称',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '客户记录的各级商品名称';

-- -----------------------------------------------------
-- Table `loan`.`source_type` ;
-- -----------------------------------------------------
DROP TABLE IF EXISTS `octopus_phone_rental`.`order_handle_info` ;

CREATE TABLE IF NOT EXISTS `octopus_phone_rental`.`order_handle_info` (
  `id` INT unsigned NOT NULL AUTO_INCREMENT,
  `order_id` VARCHAR(32) NULL COMMENT '订单ID',
  `user_id` VARCHAR(32) NULL COMMENT '用户ID',
  `user_name` VARCHAR(64) NULL COMMENT '用户姓名',
  `user_phone` VARCHAR(32) NULL COMMENT '用户手机号',
  `worker_code` VARCHAR(32) NULL COMMENT '客服工号',
  `handler_id` VARCHAR(32) NULL COMMENT '客服ID',
  `handle_type_name` VARCHAR(32) NULL COMMENT '操作内容/操作类型',
  `handle_reason` VARCHAR(256) NULL COMMENT '操作原因',
  `handle_ip_address` VARCHAR(32) NULL COMMENT 'ip地址',
  `create_date` DATETIME NULL,
  `update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
  charset utf8 collate utf8_general_ci
  ENGINE = InnoDB
  COMMENT = '记录客户订单操作记录';