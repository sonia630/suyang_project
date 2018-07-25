/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : tn

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-02-22 18:34:11
*/

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `customer_address`;
CREATE TABLE `customer_address` (
  `id`              VARCHAR(50) NOT NULL,
  `address`         VARCHAR(200)    DEFAULT NULL
  COMMENT '地址中文名称',
  `detail_address`  VARCHAR(200)    DEFAULT NULL
  COMMENT '详细地址',
  `latitude`        DECIMAL(25, 16) DEFAULT NULL
  COMMENT '经度',
  `longitude`       DECIMAL(25, 16) DEFAULT NULL
  COMMENT '纬度',
  `userId`          VARCHAR(50)     DEFAULT NULL,
  `default_address` TINYINT(4)      DEFAULT NULL
  COMMENT '默认地址',
  `in_used`         TINYINT(4)      DEFAULT NULL
  COMMENT '是否被选择',
  `create_time`     DATETIME        DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `create_by`       VARCHAR(50)     DEFAULT NULL,
  `update_by`       VARCHAR(50)     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='用户地址';

-- ----------------------------
-- Table structure for customer_info
-- ----------------------------
DROP TABLE IF EXISTS `customer_info`;
CREATE TABLE `customer_info` (
  `customer_id`      BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '客户Id',
  `customer_user_id` VARCHAR(50)         DEFAULT NULL
  COMMENT '系统用户Id',
  `customer_source`  VARCHAR(50)         DEFAULT NULL
  COMMENT '客户来源',
  `create_time`      DATETIME            DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`      DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `create_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '创建人',
  `update_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '更新人',
  `status`           TINYINT(10)         DEFAULT NULL
  COMMENT '客户状态',
  `remarks`          VARCHAR(255)        DEFAULT NULL
  COMMENT '备注说明',
  PRIMARY KEY (`customer_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='被服务者（客户）基本信息表';

-- ----------------------------
-- Table structure for evaluation_info
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_info`;
CREATE TABLE evaluation_info
(
  evaluation_id    BIGINT AUTO_INCREMENT
  COMMENT '评价ID'
    PRIMARY KEY,
  provider_user_id VARCHAR(50)                        NULL
  COMMENT '服务提供者ID',
  order_no         VARCHAR(50)                        NULL
  COMMENT '订单编号',
  evalucation_desc VARCHAR(255)                       NULL
  COMMENT '评价内容',
  evaluation_score DECIMAL(5, 3)                      NULL
  COMMENT '评价分数',
  create_time      DATETIME DEFAULT CURRENT_TIMESTAMP NULL
  COMMENT '创建时间',
  update_time      DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  create_by        VARCHAR(255)                       NULL
  COMMENT '创建人',
  update_by        VARCHAR(255)                       NULL
  COMMENT '更新人',
  status           TINYINT(10) DEFAULT '1'            NULL
  COMMENT '点评状态',
  remarks          VARCHAR(255)                       NULL
  COMMENT '备注说明'
)
  COMMENT '评价信息表'
  ENGINE = InnoDB
  CHARSET = utf8;

-- ----------------------------
-- Table structure for medical_case_info
-- ----------------------------
DROP TABLE IF EXISTS `medical_case_info`;
CREATE TABLE `medical_case_info` (
  `medical_case_id`     VARCHAR(255) NOT NULL
  COMMENT '病案ID',
  `symptom_description` VARCHAR(255)          DEFAULT NULL
  COMMENT '病例描述',
  `customer_user_id`    VARCHAR(50)           DEFAULT NULL
  COMMENT '客户ID',
  `provider_user_id`    VARCHAR(50)           DEFAULT NULL,
  `member_id`           BIGINT(20)            DEFAULT NULL,
  `order_id`            BIGINT(20)            DEFAULT NULL
  COMMENT '订单ID',
  `create_by`           VARCHAR(255)          DEFAULT NULL,
  `update_by`           VARCHAR(255)          DEFAULT NULL,
  `create_time`         DATETIME              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time`         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  PRIMARY KEY (`medical_case_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='病案表';

DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id`              BIGINT(20)   NOT NULL AUTO_INCREMENT
  COMMENT '订单自增id',
  `order_no`              VARCHAR(50)  NOT NULL
  COMMENT '订单号，唯一',
  `customer_user_id`      VARCHAR(50)  NOT NULL
  COMMENT '下单用户ID,关联user_info.user_id',
  `customer_name`         VARCHAR(30)           DEFAULT NULL
  COMMENT '用户名称',
  `provider_user_id`      VARCHAR(50)           DEFAULT NULL
  COMMENT '服务提供者用户ID,关联user_info.user_id',
  `provider_name`         VARCHAR(30)           DEFAULT NULL
  COMMENT '服务提供者名称',
  `member_id`             BIGINT(20)            DEFAULT NULL
  COMMENT '实际服务接收者ID',
  `latitude`              DECIMAL(25, 16)       DEFAULT NULL
  COMMENT '纬度',
  `longitude`             DECIMAL(25, 16)       DEFAULT NULL
  COMMENT '经度',
  `order_status`          TINYINT(2)   NOT NULL
  COMMENT '订单状态。1-待顾问确认,2-待支付,3-待服务,4-服务中，5-交易完成,6-交易取消,,7-待抢单,8-待用户确认',
  `city_id`               SMALLINT(5)  NOT NULL DEFAULT '0'
  COMMENT '收货人的城市',
  `address`               VARCHAR(255) NOT NULL
  COMMENT '收货人的详细地址',
  `comment_status`        TINYINT(4)   NOT NULL DEFAULT '0'
  COMMENT '点评状态；0，None；1，待评价；2，已评价',
  `contact_phone`         VARCHAR(20)           DEFAULT NULL
  COMMENT '联系电话',
  `contact_man`           VARCHAR(20)           DEFAULT NULL
  COMMENT '联系人',
  `service_id`            VARCHAR(255)          DEFAULT NULL
  COMMENT '服务ID',
  `service_name`          VARCHAR(255)          DEFAULT NULL
  COMMENT '服务名称',
  `journey_fee`           DECIMAL(10, 2)        DEFAULT '0.00'
  COMMENT '行程费用',
  `service_price`         DECIMAL(10, 2)        DEFAULT '0.00'
  COMMENT '服务单价',
  `service_count`         INT(4)                DEFAULT '1'
  COMMENT '服务份数',
  `total_amount`          DECIMAL(10, 2)        DEFAULT '0.00'
  COMMENT '总计费用',
  `book_start_time`       DATETIME              DEFAULT NULL
  COMMENT '预约服务开始时间',
  `book_end_time`         DATETIME              DEFAULT NULL
  COMMENT '预约服务结束时间',
  `departure_time`        DATETIME              DEFAULT NULL
  COMMENT '出发上门时间',
  `real_serv_start_time`  DATETIME              DEFAULT NULL
  COMMENT '实际服务开始时间',
  `real_serv_done_time`   DATETIME              DEFAULT NULL
  COMMENT '实际服务结束时间',
  `cancel_reason`         TINYINT(2)            DEFAULT NULL
  COMMENT '订单取消原因,1-用户取消，2-推拿师取消,3-超时取消',
  `cancel_time`           DATETIME              DEFAULT NULL
  COMMENT '订单取消时间',
  `create_time`           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `customer_confirm_time` DATETIME              DEFAULT NULL
  COMMENT '客户确认时间',
  `provider_confirm_time` DATETIME              DEFAULT NULL
  COMMENT '顾问确认时间',
  `provider_grab_time`    DATETIME              DEFAULT NULL
  COMMENT '顾问抢单时间',
  `state_expire_time`     DATETIME              DEFAULT NULL
  COMMENT '到期时间',
  `update_time`           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `update_by`             VARCHAR(30)           DEFAULT NULL
  COMMENT '最后修改者',
  `version`               INT(5)       NOT NULL DEFAULT '1'
  COMMENT '版本-乐观锁',
  PRIMARY KEY (`order_id`),
  KEY `order_info_customer_user_id_index` (`customer_user_id`),
  KEY `order_info_create_time_index` (`create_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for payment_order
-- ----------------------------
DROP TABLE IF EXISTS `payment_order`;
CREATE TABLE `payment_order` (
  `id`                BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `biz_order_no`      VARCHAR(50)                  DEFAULT NULL
  COMMENT '业务系统原始订单号',
  `pay_trade_no`      VARCHAR(50)                  DEFAULT NULL
  COMMENT '支付单号/支付单交易号',
  `pay_trade_status`  TINYINT(3) UNSIGNED NOT NULL DEFAULT '100'
  COMMENT '支付交易状态,100-待支付，101-部分支付，102-交易成功，103-交易取消，104-交易关闭',
  `customer_user_id`  VARCHAR(50)         NOT NULL
  COMMENT '用户ID',
  `customer_name`     VARCHAR(30)                  DEFAULT NULL
  COMMENT '用户名称',
  `provider_user_id`  VARCHAR(50)                  DEFAULT NULL
  COMMENT '服务提供者ID',
  `provider_name`     VARCHAR(50)                  DEFAULT NULL
  COMMENT '服务提供者名称',
  `service_name`      VARCHAR(50)                  DEFAULT NULL
  COMMENT '服务名称',
  `order_amount`      DECIMAL(12, 2)      NOT NULL DEFAULT '0.00'
  COMMENT '订单金额',
  `order_paid_amount` DECIMAL(12, 2)      NOT NULL DEFAULT '0.00'
  COMMENT '订单已支付金额',
  `order_period`      INT(11) UNSIGNED    NOT NULL DEFAULT '0'
  COMMENT '订单支付时长(分钟)',
  `order_expire_time` DATETIME            NOT NULL
  COMMENT '到期时间',
  `trade_token`       VARCHAR(50)         NOT NULL
  COMMENT '支付token(guid)',
  `trade_type`        TINYINT(3) UNSIGNED NOT NULL
  COMMENT '交易类型（充值、消费）',
  `has_refund`        TINYINT(3) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '是否有退款',
  `refund_amount`     DECIMAL(12, 2)      NOT NULL DEFAULT '0.00'
  COMMENT '退款金额',
  `complete_time`     DATETIME                     DEFAULT NULL
  COMMENT '支付完成时间',
  `remark`            VARCHAR(200)                 DEFAULT NULL
  COMMENT '支付备注',
  `create_by`         VARCHAR(30)                  DEFAULT NULL
  COMMENT '创建者',
  `create_time`       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `creator_ip`        VARCHAR(50)                  DEFAULT NULL
  COMMENT '创建者IP',
  `update_by`         VARCHAR(30)                  DEFAULT NULL
  COMMENT '最后修改者',
  `update_time`       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `version`           INT(5)              NOT NULL DEFAULT '1'
  COMMENT '版本-乐观锁',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='交易流水明细表';

-- ----------------------------
-- Table structure for payment_record
-- ----------------------------
DROP TABLE IF EXISTS `payment_record`;
CREATE TABLE `payment_record` (
  `id`                 BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `payment_order_id`   BIGINT(20) UNSIGNED NOT NULL
  COMMENT '支付单ID，外键',
  `pay_trade_no`       VARCHAR(50)         NOT NULL
  COMMENT '支付单号/支付交易号',
  `customer_user_id`   VARCHAR(50)         NOT NULL DEFAULT '0'
  COMMENT '用户ID',
  `customer_name`      VARCHAR(30)                  DEFAULT NULL
  COMMENT '用户名称',
  `biz_order_no`       VARCHAR(50)         NOT NULL
  COMMENT '业务订单号',
  `transaction_no`     VARCHAR(50)         NOT NULL
  COMMENT '支付记录交易号/支付流水号(支付系统生成，唯一)',
  `pay_status`         TINYINT(3) UNSIGNED NOT NULL DEFAULT '100'
  COMMENT '支付状态(100->未支付，101->支付成功)',
  `pay_amount`         DECIMAL(12, 2)      NOT NULL DEFAULT '0.00'
  COMMENT '支付金额',
  `fee_rate`           DECIMAL(6, 4)       NOT NULL DEFAULT '0.0000'
  COMMENT '支付手续费费率',
  `out_trade_no`       VARCHAR(50)                  DEFAULT NULL
  COMMENT '发送至三方的交易流水号',
  `out_transaction_no` VARCHAR(50)                  DEFAULT NULL
  COMMENT '三方交易流水号',
  `pay_way_no`         TINYINT(3) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '支付渠道编号',
  `pay_way_name`       VARCHAR(30)                  DEFAULT NULL
  COMMENT '支付渠道名称',
  `paid_time`          DATETIME                     DEFAULT NULL
  COMMENT '支付完成时间',
  `payment_sign`       VARCHAR(50)         NOT NULL
  COMMENT '支付凭证',
  `notify_url`         VARCHAR(200)                 DEFAULT NULL
  COMMENT '后台异步通知url',
  `has_refund`         TINYINT(3) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '是否有退款',
  `refund_amount`      DECIMAL(12, 2)      NOT NULL DEFAULT '0.00'
  COMMENT '退款金额',
  `create_by`          VARCHAR(30)                  DEFAULT NULL
  COMMENT '创建者',
  `create_time`        DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_by`          VARCHAR(30)                  DEFAULT NULL
  COMMENT '最后修改者',
  `update_time`        DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `version`            INT(5)              NOT NULL DEFAULT '1'
  COMMENT '版本-乐观锁',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='支付流水记录表';

DROP TABLE IF EXISTS `refund_record`;
CREATE TABLE `refund_record` (
  `id`                       BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `payment_order_id`         BIGINT(20) UNSIGNED NOT NULL
  COMMENT '原支付单ID，外键',
  `pay_trade_no`             VARCHAR(50)                  DEFAULT NULL
  COMMENT '支付单号/支付交易号',
  `biz_order_no`             VARCHAR(50)         NOT NULL
  COMMENT '外部订单号',
  `customer_user_id`         VARCHAR(50)         NOT NULL DEFAULT '0'
  COMMENT '用户ID',
  `customer_name`            VARCHAR(30)                  DEFAULT NULL
  COMMENT '用户名称',
  `orgin_transaction_no`     VARCHAR(50)         NOT NULL
  COMMENT '原支付交易号/流水号',
  `pay_type_no`              TINYINT(3) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '原支付类型编号',
  `pay_type_name`            VARCHAR(20)                  DEFAULT NULL
  COMMENT '原支付类型名称',
  `pay_amount`               DECIMAL(12, 2)      NOT NULL DEFAULT '0.0000'
  COMMENT '原支付金额',
  `orgin_out_transaction_no` VARCHAR(50)                  DEFAULT NULL
  COMMENT '原三方交易流水号',
  `refund_amount`            DECIMAL(12, 2)      NOT NULL DEFAULT '0.0000'
  COMMENT '退款金额',
  `transaction_no`           VARCHAR(50)         NOT NULL
  COMMENT '退款交易号',
  `out_transaction_no`       VARCHAR(50)                  DEFAULT NULL
  COMMENT '三方退款交易号',
  `refund_way`               TINYINT(3) UNSIGNED NOT NULL DEFAULT '101'
  COMMENT '退款方式(原路退回，线下退款)',
  `notify_url`               VARCHAR(200)                 DEFAULT NULL
  COMMENT '退款结果通知地址',
  `refund_complete_time`     DATETIME                     DEFAULT NULL
  COMMENT '退款完成时间',
  `refund_status`            TINYINT(3) UNSIGNED NOT NULL DEFAULT '100'
  COMMENT '退款状态',
  `refund_reason`            VARCHAR(200)                 DEFAULT NULL
  COMMENT '退款原因',
  `remark`                   VARCHAR(255)        NOT NULL DEFAULT ''
  COMMENT '备注',
  `create_by`                VARCHAR(30)                  DEFAULT NULL
  COMMENT '创建者',
  `create_time`              DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_by`                VARCHAR(30)                  DEFAULT NULL
  COMMENT '最后修改者',
  `update_time`              DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `version`                  INT(5)              NOT NULL DEFAULT '1'
  COMMENT '版本-乐观锁',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='退款流水记录表';

-- ----------------------------
-- Table structure for provider_certification_info
-- ----------------------------
DROP TABLE IF EXISTS `provider_certification_info`;
CREATE TABLE `provider_certification_info` (
  `certification_id` BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '证书id',
  `descr`            VARCHAR(255)        DEFAULT NULL
  COMMENT '证书说明',
  `name`             VARCHAR(255)        DEFAULT NULL
  COMMENT '证书名称',
  `provider_id`      BIGINT(20)          DEFAULT NULL
  COMMENT '服务提供者id',
  `status`           TINYINT(10)         DEFAULT NULL
  COMMENT '证书状态',
  `create_time`      DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `create_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '创建人',
  `update_time`      DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `update_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '更新人',
  `remarks`          VARCHAR(255)        DEFAULT NULL
  COMMENT '备注说明',
  `expire_time`      DATETIME            DEFAULT NULL
  COMMENT '证书过期时间',
  PRIMARY KEY (`certification_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='服务提供者证书信息表';

-- ----------------------------
-- Table structure for provider_info
-- ----------------------------
DROP TABLE IF EXISTS `provider_info`;
CREATE TABLE `provider_info` (
  `provider_id`           BIGINT(20)      NOT NULL AUTO_INCREMENT
  COMMENT '服务提供者ID',
  `provider_user_id`      VARCHAR(50)     NOT NULL
  COMMENT '服务提供者ID,关联user_info.user_id',
  `provider_edu_lev`      VARCHAR(255)    NULL
  COMMENT '服务者教育程度',
  `provider_birth_place`  VARCHAR(255)    NULL
  COMMENT '服务者籍贯',
  `provider_introduction` VARCHAR(255)    NULL
  COMMENT '服务者简介',
  `certification_id`      BIGINT          NULL
  COMMENT '资质证书',
  `health`                VARCHAR(255)    NULL
  COMMENT '健康状况',
  `health_report`         VARCHAR(255)    NULL
  COMMENT '健康报告',
  `health_report_time`    DATETIME        NULL
  COMMENT '健康报告时间',
  `latitude`              DECIMAL(25, 16) NULL
  COMMENT '纬度',
  `longitude`             DECIMAL(25, 16) NULL
  COMMENT '经度',
  `common_address`        VARCHAR(255)             DEFAULT NULL
  COMMENT '常用地址',
  `status`                TINYINT(10)              DEFAULT '0'
  COMMENT '服务者状态（1、已审核 0、未审核）',
  `level`                 TINYINT(10)              DEFAULT NULL
  COMMENT '服务者等级',
  `create_time`           DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `update_time`           DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `create_by`             VARCHAR(255)             DEFAULT NULL
  COMMENT '创建者',
  `update_by`             VARCHAR(255)             DEFAULT NULL
  COMMENT '更新人',
  `remarks`               VARCHAR(255)             DEFAULT NULL
  COMMENT '备注说明',
  PRIMARY KEY (`provider_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='服务提供者基本信息表';

DROP TABLE IF EXISTS `provider_schedule`;
CREATE TABLE `provider_schedule` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `latitude`    DECIMAL(25, 16)     DEFAULT NULL,
  `longitude`   DECIMAL(25, 16)     DEFAULT NULL,
  `provider_id` VARCHAR(50)         DEFAULT NULL,
  `date`        DATE                DEFAULT NULL
  COMMENT '排期日期',
  `slot`        INT(11)             DEFAULT NULL
  COMMENT '排期时间段',
  `order_no`    VARCHAR(50)         DEFAULT NULL
  COMMENT '订单号',
  `create_time` DATETIME            DEFAULT NULL,
  `create_by`   VARCHAR(50)         DEFAULT NULL,
  `update_time` DATETIME            DEFAULT NULL,
  `update_by`   VARCHAR(50)         DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `provider_schedule_latitude_index` (`latitude`),
  KEY `provider_schedule_longitude_index` (`longitude`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 29
  DEFAULT CHARSET = utf8
  COMMENT ='顾问排期转换表';

--


-- ----------------------------
-- Table structure for service_info
-- ----------------------------
DROP TABLE IF EXISTS `service_info`;
CREATE TABLE `service_info` (
  `service_id`      VARCHAR(50) NOT NULL
  COMMENT '服务定义ID',
  `service_name`    VARCHAR(100)         DEFAULT NULL
  COMMENT '服务名称',
  `service_summary` VARCHAR(50)          DEFAULT NULL
  COMMENT '服务简介',
  `description`     VARCHAR(500)         DEFAULT NULL
  COMMENT '服务定义描述',
  `sort_order`      INT(4)               DEFAULT '0'
  COMMENT '排序',
  `create_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `create_by`       VARCHAR(255)         DEFAULT NULL
  COMMENT '服务创建人',
  `update_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `update_by`       VARCHAR(255)         DEFAULT NULL
  COMMENT '服务更新人',
  `status`          TINYINT(2)           DEFAULT '1'
  COMMENT '状态',
  `remarks`         VARCHAR(255)         DEFAULT NULL
  COMMENT '备注说明',
  `pic`             VARCHAR(255)         DEFAULT NULL,
  PRIMARY KEY (`service_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='服务定义表';


DROP TABLE IF EXISTS `service_category`;
CREATE TABLE `service_category` (
  `cat_id`      VARCHAR(50)  DEFAULT NULL
  COMMENT '分类ID',
  `cat_name`    VARCHAR(50)  DEFAULT NULL
  COMMENT '分类名称',
  `sort`        INT(11)      DEFAULT NULL
  COMMENT '排序',
  `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP,
  `create_by`   VARCHAR(50)  DEFAULT NULL,
  `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP,
  `update_by`   VARCHAR(50)  DEFAULT NULL,
  `remarks`     VARCHAR(255) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='服务分类表';


DROP TABLE IF EXISTS `service_info_category`;
CREATE TABLE `service_info_category` (
  `id`         VARCHAR(50) NOT NULL,
  `cat_id`     VARCHAR(50) DEFAULT NULL,
  `service_id` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `service_info_category_cat_id_service_id_uindex` (`cat_id`, `service_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='服务分类关系表';

DROP TABLE IF EXISTS `srv_provider_srv_rel`;
CREATE TABLE `srv_provider_srv_rel` (
  `id`               BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '服务者提供服务的映射ID',
  `provider_user_id` VARCHAR(50)         DEFAULT NULL
  COMMENT '服务提供者的user_id',
  `service_id`       VARCHAR(50)         DEFAULT NULL
  COMMENT '服务ID',
  `price`            DECIMAL(15, 5)      DEFAULT NULL
  COMMENT '服务定价',
  `sort_order`       INT(4)              DEFAULT '0'
  COMMENT '排序',
  `status`           TINYINT(2)          DEFAULT '1'
  COMMENT '状态',
  `create_time`      DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `create_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '创建人',
  `update_time`      DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `update_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '更新人',
  `remarks`          VARCHAR(255)        DEFAULT NULL
  COMMENT '备注说明',
  `billing_type`     TINYINT(4)          DEFAULT NULL
  COMMENT '计费方式',
  `estimated_time`   INT(11)             DEFAULT NULL
  COMMENT '预计服务时长(单位分钟)',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8
  COMMENT ='服务者能够提供的服务';

DROP TABLE IF EXISTS `service_time_def`;
CREATE TABLE `service_time_def` (
  `id`               BIGINT(20) NOT NULL AUTO_INCREMENT
  COMMENT '时间定义ID',
  `provider_user_id` VARCHAR(50)         DEFAULT NULL
  COMMENT '服务提供者的user_id',
  `start_time`       DATETIME   NOT NULL
  COMMENT '开始时间',
  `end_time`         DATETIME   NOT NULL
  COMMENT '结束时间',
  `create_time`      DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `create_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '服务创建人',
  `update_time`      DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `update_by`        VARCHAR(255)        DEFAULT NULL
  COMMENT '服务更新人',
  `status`           TINYINT(2)          DEFAULT '1'
  COMMENT '状态 1-有效,0-无效',
  `remarks`          VARCHAR(255)        DEFAULT NULL
  COMMENT '备注说明',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='服务时间定义表';
-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `tag_id`      BIGINT(255)       DEFAULT NULL,
  `cat`         TINYINT(10)       DEFAULT NULL
  COMMENT '标签类别\n主要是标签应用的地方',
  `name`        VARCHAR(255)      DEFAULT NULL
  COMMENT '标签描述',
  `status`      TINYINT(10)       DEFAULT NULL
  COMMENT '标签状态',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `create_by`   VARCHAR(255)      DEFAULT NULL
  COMMENT '创建人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `update_by`   VARCHAR(255)      DEFAULT NULL
  COMMENT '更新人',
  `remarks`     VARCHAR(255)      DEFAULT NULL
  COMMENT '备注说明'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='标签表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id`     VARCHAR(50)  NOT NULL
  COMMENT '用户Id',
  `name`        VARCHAR(255)          DEFAULT NULL
  COMMENT '用户姓名',
  `password`    VARCHAR(255)          DEFAULT NULL
  COMMENT '用户密码',
  `id_num`      VARCHAR(25)           DEFAULT NULL
  COMMENT '用户身份证号',
  `phone`       VARCHAR(15)           DEFAULT NULL
  COMMENT '用户手机号码',
  `email`       VARCHAR(255)          DEFAULT NULL
  COMMENT '用户电子邮箱',
  `gender`      TINYINT(4)            DEFAULT '1'
  COMMENT '用户性别',
  `user_type`   TINYINT(4)   NOT NULL DEFAULT '0'
  COMMENT '1-系统用户，2-顾问，4-用户',
  `birthday`    DATE         NULL
  COMMENT '用户生日',
  `head_pic`    VARCHAR(255) NULL
  COMMENT '用户头像',
  `real_name`   VARCHAR(255) NULL
  COMMENT '用户真实姓名',
  `wx_open_id`  VARCHAR(20)           DEFAULT NULL
  COMMENT '微信openId',
  `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `create_by`   VARCHAR(255)          DEFAULT NULL
  COMMENT '创建人',
  `update_by`   VARCHAR(255)          DEFAULT NULL
  COMMENT '更新人',
  `status`      TINYINT(10)           DEFAULT NULL
  COMMENT '用户状态',
  `remarks`     VARCHAR(255)          DEFAULT NULL
  COMMENT '备注说明',
  `login_date`  DATETIME              DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_info_name` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='系统用户基本信息表';


CREATE TABLE family_member
(
  member_id        VARCHAR(50)                        NOT NULL
  COMMENT '家庭成员表'
    PRIMARY KEY,
  customer_user_id VARCHAR(50)                        NOT NULL,
  member_name      VARCHAR(255)                       NULL,
  member_birthday  DATETIME                           NULL,
  update_time      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP
  COMMENT '最后修改时间',
  create_time      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  create_by        VARCHAR(255)                       NULL
  COMMENT '创建人',
  update_by        VARCHAR(255)                       NULL
  COMMENT '更新人',
  status           TINYINT(10)                        NULL
  COMMENT '用户状态',
  remarks          VARCHAR(255)                       NULL
  COMMENT '备注说明'
)
  COMMENT '服务接收者-家庭成员表'
  ENGINE = InnoDB
  CHARSET = utf8;


DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id`          VARCHAR(45) NOT NULL,
  `name`        VARCHAR(100) DEFAULT NULL
  COMMENT '机构名称',
  `parent_id`   VARCHAR(45)  DEFAULT NULL
  COMMENT '父节点',
  `create_by`   VARCHAR(32)  DEFAULT NULL,
  `create_time` DATETIME     DEFAULT NULL,
  `update_by`   VARCHAR(32)  DEFAULT NULL,
  `update_time` DATETIME     DEFAULT NULL,
  `del_flag`    CHAR(1)      DEFAULT NULL
  COMMENT '删除标记',
  `remarks`     VARCHAR(255) DEFAULT NULL,
  `hierarchy`   INT(11)      DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_parent_id` (`parent_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_org_role`;
CREATE TABLE `sys_org_role` (
  `id`      VARCHAR(45) DEFAULT NULL,
  `org_id`  VARCHAR(45) DEFAULT NULL,
  `role_id` VARCHAR(45) DEFAULT NULL,
  UNIQUE KEY `sys_org_role_id_pk` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `sys_org_user`;
CREATE TABLE `sys_org_user` (
  `id`      VARCHAR(45) DEFAULT NULL,
  `org_id`  VARCHAR(45) NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  UNIQUE KEY `sys_org_user_id_pk` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id`          VARCHAR(45) NOT NULL,
  `name`        VARCHAR(100) DEFAULT NULL
  COMMENT '资源路径',
  `type`        VARCHAR(50)  DEFAULT NULL
  COMMENT '资源类型',
  `url`         VARCHAR(200) DEFAULT NULL
  COMMENT '点击后前往的地址',
  `parent_id`   VARCHAR(32)  DEFAULT NULL
  COMMENT '父编号',
  `permission`  VARCHAR(100) DEFAULT NULL
  COMMENT '权限字符串',
  `show`        TINYINT(1)   DEFAULT '0'
  COMMENT '是否显示',
  `sort`        INT(5)       DEFAULT '1'
  COMMENT '排序',
  `menu_icon`   VARCHAR(255) DEFAULT NULL
  COMMENT '图标',
  `remarks`     VARCHAR(255) DEFAULT NULL
  COMMENT '摘要',
  `create_by`   VARCHAR(32)  DEFAULT NULL,
  `create_time` DATETIME     DEFAULT NULL,
  `update_by`   VARCHAR(32)  DEFAULT NULL,
  `update_time` DATETIME     DEFAULT NULL,
  `del_flag`    CHAR(1)      DEFAULT NULL,
  `menu`        TINYINT(4)   DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


INSERT INTO `sys_permission` VALUES
  ('40281e815beda90f015bedcf7102000f', '计划任务', '', 'task/schedulejob',
                                       '40288ab85cf8276b015cf82debcb005b', 'task:schedulejob:list',
                                       1, 4, '', '', '2018-02-26 23:17:19', '2018-02-26 23:17:19',
   '', '2018-02-26 23:17:19', '0', 1),
  ('40281e815c097acf015c097bcaea0000', '用户最后在线情况', '', 'sys/lastOnline', '4028ea815a701416015a7075b4f9001f', 'sys:userlastonline', 1, 2, '', '用户最后在线情况', '2018-02-26 23:17:20', '2018-02-26 23:17:20', '', '2018-02-26 23:17:20', '0', 1),
  ('40281e815c547c32015c54a21e260038', '生成案列', '', '#', '4028ea815a78e9e6015a78f1dc9d0000', '', 0, 3, '', '生成案列', '2018-02-26 23:17:21', '2018-02-26 23:17:21', '82191015', '2018-02-28 00:09:23', '0', 1),
  ('40281e815c54d147015c54daf16c0001', '系统日志', '', 'sys/log', '4028ea815a701416015a7075b4f9001f', 'sys:log', 1, 6, 'fa-book', '系统日志', '2018-02-26 23:17:22', '2018-02-26 23:17:22', '', '2018-02-26 23:17:22', '0', 1),
  ('40281e815c580ea3015c58c8635d0037', '测试单表', '', 'test/singletable', '40281e815c547c32015c54a21e260038', 'test:singletable', 0, 1, '', '', '2018-02-26 23:17:23', '2018-02-26 23:17:23', '82191015', '2018-02-28 00:09:23', '0', 1),
  ('40281e815c912406015c9149f7b80044', '通知公告', '', '', '', '', 1, 5, 'fa-television', '', '2018-02-26 23:17:25', '2018-02-26 23:17:25', '', '2018-02-26 23:17:25', '0', 1),
  ('40281e815c912406015c914a1bc30045', '通知公告', '', 'oa/oanotification', '40281e815c912406015c9149f7b80044', 'oa:oanotification', 1, 1, '', '', '2018-02-26 23:17:26', '2018-02-26 23:17:26', '', '2018-02-26 23:17:26', '0', 1),
  ('40281e815cef1f76015cef268ff0000e', '测试树', '', 'test/testtree', '40281e815c547c32015c54a21e260038', 'test:testtree', 0, 1, '', '测试树', '2018-02-26 23:17:27', '2018-02-26 23:17:27', '82191015', '2018-02-28 00:09:23', '0', 1),
  ('40288ab85a5eecc6015a5ef22ad80001', '系统设置', '', '#', '', '', 1, 2, 'fa fa-lg fa-fw fa-rss', '', '2018-02-26 23:17:28', '2018-02-26 23:17:28', '82191015', '2018-02-26 23:17:28', '0', 1),
  ('40288ab85a5eecc6015a5ef6ce870002', '用户管理', '', '/sys/user/', '40288ab85a5eecc6015a5ef22ad80001', 'sys:user:list', 1, 1, '', 'sdfdsfsdfsdfsdfsdfsf', '2018-02-26 23:17:29', '2018-02-26 23:17:29', '82191015', '2018-02-26 23:17:29', '0', 1),
  ('40288ab85a5eecc6015a5ef8f2890003', '机构管理', '', '/sys/org/', '40288ab85a5eecc6015a5ef22ad80001', 'sys:organization:list', 1, 2, '', '', '2018-02-26 23:17:30', '2018-02-26 23:17:30', '82191015', '2018-02-28 00:26:07', '0', 1),
  ('40288ab85a5eecc6015a5ef95c700004', '角色管理', '', '/sys/role/', '40288ab85a5eecc6015a5ef22ad80001', 'sys:role:list', 1, 3, '', '', '2018-02-26 23:17:31', '2018-02-26 23:17:31', '82191015', '2018-02-26 23:17:31', '0', 1),
  ('40288ab85a5eecc6015a5ef9f6160005', '菜单管理', '', '/sys/menu/', '40288ab85a5eecc6015a5ef22ad80001', 'sys:menu:list', 1, 4, '', '', '2018-02-26 23:17:32', '2018-02-26 23:17:32', '82191015', '2018-03-06 16:04:49', '0', 1),
  ('40288ab85b604adf015b605023a70000', '在线用户', '', 'sys/online', '4028ea815a701416015a7075b4f9001f', 'sys:online:list', 1, 1, '', '在线用户', '2018-02-26 23:17:33', '2018-02-26 23:17:33', '', '2018-02-26 23:17:33', '0', 1),
  ('40288ab85bea9452015beaa9e7a70002', '服务列表', '', '/serviceInfo/list', '40288ab85c8593cd015c859f47960016', 'sys:setting:sms', 1, 1, '', '', '2018-02-26 23:17:34', '2018-02-26 23:17:34', '82191015', '2018-03-05 23:27:50', '0', 1),
  ('40288ab85befd65a015bf088333a0015', '多数据源管理', '', 'sys/datasource', '40288ab85cf8276b015cf82debcb005b', 'sys:datasource:list', 1, 5, '', '动态数据源', '2018-02-26 23:17:35', '2018-02-26 23:17:35', '', '2018-02-26 23:17:35', '0', 1),
  ('40288ab85c1ae76c015c1b12b68a0000', '代码案例', '', '', '', '', 1, 12, 'fa-code', '', '2018-02-26 23:17:36', '2018-02-26 23:17:36', '', '2018-02-26 23:17:36', '0', 1),
  ('40288ab85c1ae76c015c1b1316680001', '编辑器', '', 'demo/form/editor', '40288ab85c1ae76c015c1b12b68a0000', '', 1, 1, '', '', '2018-02-26 23:17:37', '2018-02-26 23:17:37', '', '2018-02-26 23:17:37', '0', 1),
  ('40288ab85c1e2442015c1e3246b70000', '文件上传', '', 'demo/form/upload', '40288ab85c1ae76c015c1b12b68a0000', '', 1, 2, '', '', '2018-02-26 23:17:38', '2018-02-26 23:17:38', '', '2018-02-26 23:17:38', '0', 1),
  ('40288ab85c33548d015c33cdc5a600f3', '附件信息', '', 'sys/attachment', '4028ea815a701416015a7075b4f9001f', 'sys:attachment:list', 1, 5, '', '', '2018-02-26 23:17:39', '2018-02-26 23:17:39', '', '2018-02-26 23:17:39', '0', 1),
  ('40288ab85c3df6b7015c3e3d1e770000', '搜索自动补全', '', 'demo/form/combox', '40288ab85c1ae76c015c1b12b68a0000', '', 1, 3, 'fa-500px', '测试菜单', '2018-02-26 23:17:40', '2018-02-26 23:17:40', '', '2018-02-26 23:17:40', '0', 1),
  ('40288ab85c8593cd015c859f47960016', '服务管理', '', '#', NULL, '', 1, 2, 'fa fa-lg fa-fw fa-magic', '', '2018-02-26 23:17:41', '2018-02-26 23:17:41', '82191015', '2018-03-05 23:26:52', '0', 1),
  ('40288ab85c85fa54015c860cee2e0023', '发送日志', '', 'sms/smssendlog', '40288ab85c8593cd015c859f47960016', 'sms:smssendlog', 1, 4, '', '短信发送日志', '2018-02-26 23:17:43', '2018-02-26 23:17:43', '82191015', '2018-03-05 23:26:52', '0', 1),
  ('40288ab85c9eeb5c015c9f4d1f8e0000', '统计报表', '', '', '', '', 1, 10, 'fa-bar-chart', '', '2018-02-26 23:17:44', '2018-02-26 23:17:44', '', '2018-02-26 23:17:44', '0', 1),
  ('40288ab85c9eeb5c015c9f4e1cd00001', '折线图', '', '/charts/echarts/chart/line', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 1, '', '', '2018-02-26 23:17:45', '2018-02-26 23:17:45', '', '2018-02-26 23:17:45', '0', 1),
  ('40288ab85c9eeb5c015c9f5fa02e002a', '柱状图', '', '/charts/echarts/chart/bar', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 2, '', '', '2018-02-26 23:17:46', '2018-02-26 23:17:46', '', '2018-02-26 23:17:46', '0', 1),
  ('40288ab85c9eeb5c015c9f626f58002d', '漏斗图', '', '/charts/echarts/chart/funnel', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 5, '', '', '2018-02-26 23:17:47', '2018-02-26 23:17:47', '', '2018-02-26 23:17:47', '0', 1),
  ('40288ab85c9eeb5c015c9f62e726002e', '仪表盘', '', '/charts/echarts/chart/gauge', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 6, '', '', '2018-02-26 23:17:48', '2018-02-26 23:17:48', '', '2018-02-26 23:17:48', '0', 1),
  ('40288ab85c9eeb5c015c9f6390fd002f', 'K线图', '', '/charts/echarts/chart/k', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 7, '', '', '2018-02-26 23:17:49', '2018-02-26 23:17:49', '', '2018-02-26 23:17:49', '0', 1),
  ('40288ab85c9eeb5c015c9f6572630030', '饼状图', '', '/charts/echarts/chart/pie', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 7, '', '', '2018-02-26 23:17:50', '2018-02-26 23:17:50', '', '2018-02-26 23:17:50', '0', 1),
  ('40288ab85c9eeb5c015c9f65e3a50031', '雷达图', '', '/charts/echarts/chart/radar', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 9, '', '', '2018-02-26 23:17:51', '2018-02-26 23:17:51', '', '2018-02-26 23:17:51', '0', 1),
  ('40288ab85c9eeb5c015c9f672f2e0032', '散点图', '', '/charts/echarts/chart/scatter', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 9, '', '', '2018-02-26 23:17:52', '2018-02-26 23:17:52', '', '2018-02-26 23:17:52', '0', 1),
  ('40288ab85c9eeb5c015c9f9694480064', '关系图', '', '/charts/echarts/chart/circular', '40288ab85c9eeb5c015c9f4d1f8e0000', '', 1, 11, '', '', '2018-02-26 23:17:53', '2018-02-26 23:17:53', '', '2018-02-26 23:17:53', '0', 1),
  ('40288ab85cf81b04015cf8213da10002', '一对多', '', 'test/testordermain', '40281e815c547c32015c54a21e260038', 'test:testordermain', 0, 1, '', '', '2018-02-26 23:17:54', '2018-02-26 23:17:54', '82191015', '2018-02-28 00:09:23', '0', 1),
  ('40288ab85cf8276b015cf82debcb005b', '常见工具', '', '', '', '', 1, 4, 'fa-binoculars', '', '2018-02-26 23:17:55', '2018-02-26 23:17:55', '', '2018-02-26 23:17:55', '0', 1),
  ('4028ea815a701416015a7075b4f9001f', '系统监控', '', '', '', '', 1, 3, 'fa-video-camera', '', '2018-02-26 23:17:56', '2018-02-26 23:17:56', '', '2018-02-26 23:17:56', '0', 1),
  ('4028ea815a701416015a70766a7a0020', '数据库监控', '', 'monitor/druid', '4028ea815a701416015a7075b4f9001f', 'monitor:druid:index', 1, 3, '', '', '2018-02-26 23:17:57', '2018-02-26 23:17:57', '', '2018-02-26 23:17:57', '0', 1),
  ('4028ea815a78e9e6015a78f1dc9d0000', '代码生成器', '', '#', NULL, '', 0, 10, 'fa fa-lg fa-fw fa-home', '', '2018-02-26 23:17:58', '2018-02-26 23:17:58', '82191015', '2018-02-28 00:09:23', '0', 1),
  ('4028ea815a78e9e6015a78f35cbe0001', '表单配置', '', 'codegen/table', '4028ea815a78e9e6015a78f1dc9d0000', 'codegen:table:list', 0, 1, '', '', '2018-02-26 23:17:59', '2018-02-26 23:17:59', '82191015', '2018-02-28 00:09:23', '0', 1),
  ('591e8b5241984bd8b67a7b2f9ac35649', '资源管理', NULL, '/sys/res/', '40288ab85a5eecc6015a5ef22ad80001', NULL, 1, 3, '', NULL, '82191015', '2018-02-28 00:21:37', NULL, NULL, NULL, 1),
  ('menu', '菜单管理', NULL, '#', NULL, NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu1', '菜单列表', NULL, '/sys/menu/', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu2', '菜单表单', NULL, '/sys/menu/form', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu3', '保存菜单', NULL, '/sys/menu/save', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu4', '删除菜单', NULL, '/sys/menu/delete', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu5', '左侧菜单列表', NULL, '/sys/menu/left', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu6', '保存菜单排序', NULL, '/sys/menu/updateSort', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('menu7', '获取菜单树', NULL, '/sys/menu/treeData', 'menu', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org', '机构管理', NULL, '#', NULL, NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org1', '机构列表页', NULL, '/sys/org/', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org10', '获取机构树', NULL, '/sys/org/treeData', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org2', '显示赋予机构角色页面', NULL, '/sys/org/showAssignRole', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org3', '赋予机构角色', NULL, '/sys/org/assignRole', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org4', '保存或更新机构', NULL, '/sys/org/save', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org5', '获取机构信息', NULL, '/sys/org/detail', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org6', '赋予机构用户', NULL, '/sys/org/assignUser', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org7', '赋予机构角色', NULL, '/sys/org/delAssignedUser', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org8', '调整机构树', NULL, '/sys/org/move', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('org9', '删除机构', NULL, '/sys/org/delete', 'org', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res', '资源管理', NULL, '#', NULL, NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res1', '资源列表', NULL, '/sys/res/', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res2', '资源树形数据', NULL, '/sys/res/tree', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res3', '展现新增资源表单', NULL, '/sys/res/showAdd', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res4', '赋予角色资源', NULL, '/sys/res/roleAssignRes', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res5', '删除资源', NULL, '/sys/res/delete', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res6', '保存资源', NULL, '/sys/res/save', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res7', '显示编辑资源', NULL, '/sys/res/showEdit', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('res8', '资源树结构调整', NULL, '/sys/res/move', 'res', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role', '角色管理', NULL, '#', NULL, NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role1', '显示角色列表页', NULL, '/sys/role/', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role10', '获取角色包含的菜单树', NULL, '/sys/role/menus', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role11', '获取角色包含的资源树', NULL, '/sys/role/resources', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role12', '获取角色未包含的菜单树', NULL, '/sys/role/excludeMenus', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role13', '获取角色未包含的资源树', NULL, '/sys/role/excludeResources', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role14', '现实权限或资源树', NULL, '/sys/role/permission_tree', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role2', '显示角色详细信息', NULL, '/sys/role/detail', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role3', '获取角色树', NULL, '/sys/role/tree', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role4', '调整角色树', NULL, '/sys/role/move', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role5', '保存或编辑角色', NULL, '/sys/role/save', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role6', '删除角色', NULL, '/sys/role/delete', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role7', '显示角色', NULL, '/sys/role/assign', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role8', '分配权限到角色', NULL, '/sys/role/assignIt', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('role9', '删除角色已分配权限', NULL, '/sys/role/deleteIt', 'role', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user', '用户管理', NULL, '#', NULL, NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user1', '显示用户列表页', NULL, '/sys/user/', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user10', '删除用户', NULL, '/sys/user/d', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user11', '锁定用户', NULL, '/sys/user/lock', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user12', '解锁用户', NULL, '/sys/user/unlock', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user3', '显示用户查询页面', NULL, '/sys/user/lookup', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user4', '显示新增用户页', NULL, '/sys/user/showAdd', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user5', '显示用户查询状态栏', NULL, '/sys/user/showToolbar', 'user', NULL, 1, 1, NULL, NULL, '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user6', '显示赋予用户角色页面', NULL, '/sys/user/showAssignRole', 'user', NULL, 1, 1, NULL, NULL,
            '82191015', '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user7', '赋予用户角色', NULL, '/sys/user/assignRole', 'user', NULL, 1, 1, NULL, NULL, '82191015',
   '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user8', '保存或修改用户', NULL, '/sys/user/save', 'user', NULL, 1, 1, NULL, NULL, '82191015',
   '2018-03-06 17:39:33', NULL, NULL, NULL, 0),
  ('user9', '显示用户编辑页面', NULL, '/sys/user/showEdit', 'user', NULL, 1, 1, NULL, NULL, '82191015',
   '2018-03-06 17:39:33', NULL, NULL, NULL, 0);

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id`          VARCHAR(45) NOT NULL,
  `name`        VARCHAR(100)         DEFAULT NULL,
  `parent_id`   VARCHAR(45)          DEFAULT NULL,
  `description` VARCHAR(200)         DEFAULT NULL,
  `type`        TINYINT(4)  NOT NULL DEFAULT '1'
  COMMENT '角色or分类',
  `create_by`   VARCHAR(64)          DEFAULT NULL
  COMMENT '创建者',
  `create_time` DATETIME             DEFAULT NULL
  COMMENT '创建时间',
  `update_by`   VARCHAR(64)          DEFAULT NULL
  COMMENT '更新者',
  `update_time` DATETIME             DEFAULT NULL
  COMMENT '更新时间',
  `remarks`     VARCHAR(255)         DEFAULT NULL
  COMMENT '备注信息',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='角色表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id`            VARCHAR(45) NOT NULL
  COMMENT '编号',
  `permission_id` VARCHAR(32) NOT NULL
  COMMENT '菜单或资源编号',
  `role_id`       VARCHAR(32) NOT NULL
  COMMENT '角色编号',
  PRIMARY KEY (`id`),
  KEY `sys_role_menu_menuid` (`permission_id`),
  KEY `sys_role_menu_roleid` (`role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='角色-菜单';


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id`      VARCHAR(45) NOT NULL
  COMMENT '编号',
  `user_id` VARCHAR(45) NOT NULL
  COMMENT '用户编号',
  `role_id` VARCHAR(45) NOT NULL
  COMMENT '角色编号',
  PRIMARY KEY (`id`),
  KEY `sys_user_role_userid` (`user_id`),
  KEY `sys_user_role_roleid` (`role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='用户-角色';

INSERT INTO `user_info` VALUES
  ('82191015', 'admin', '928bfd2577490322a6e19b793691467e', NULL, NULL, NULL, 1, 1, NULL, NULL,
               NULL, NULL, '2018-03-06 17:35:46', '2018-03-03 22:17:48', NULL, NULL, 1, NULL,
   '2018-03-06 17:35:46'),
  ('b7b7703dc34246618be20e7d70dbf87d', 'panye123', '5a1c9ad6bec0c44ff769ff78b8f39987', NULL, NULL,
                                       NULL, 1, 3, NULL, NULL, NULL, NULL, '2018-03-05 16:58:39',
   '2018-03-03 22:45:26', '82191015', NULL, 2, NULL, NULL);
