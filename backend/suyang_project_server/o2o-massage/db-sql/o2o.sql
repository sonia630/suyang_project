CREATE TABLE `usf_customer_info` (
`customer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户Id',
`customer_user_id` bigint NULL COMMENT '系统用户Id',
`customer_name` varchar(255) NULL COMMENT '客户姓名',
`customer_email` varchar(255) NULL COMMENT '客户电子邮箱',
`customer_birthday` datetime NULL COMMENT '客户出生日期',
`create_time` datetime NULL COMMENT '客户创建时间',
`update_time` datetime NULL COMMENT '客户信息更新时间',
`create_by` varchar(255) NULL COMMENT '创建人',
`update_by` varchar NULL COMMENT '更新人',
`status` tinyint(4) NULL COMMENT '客户状态 1-有效 0-无效',
`remarks` varchar(255) NULL COMMENT '备注说明',
PRIMARY KEY (`customer_id`)
)
COMMENT='被服务者（客户）基本信息表'
;

CREATE TABLE `usf_provider_info` (
`provider_user_id` bigint NOT NULL COMMENT '服务提供者ID,关联usf_user_info.user_id',
`provider_edu_lev` varchar(255) NULL COMMENT '服务者教育程度',
`provider_birth_place` varchar(255) NULL COMMENT '服务者籍贯',
`provider_introduction` varchar(255) NULL COMMENT '服务者简介',
`certification_id` bigint NULL COMMENT '资质证书',
`health` varchar(255) NULL COMMENT '健康状况',
`health_report` varchar(255) NULL COMMENT '健康报告',
`health_report_time` datetime NULL COMMENT '健康报告时间',
`status` tinyint(4) NULL COMMENT '服务者状态',
`create_time` datetime NULL COMMENT '服务者创建时间',
`create_by` varchar(255) NULL COMMENT '创建者',
`update_time` datetime NULL COMMENT '更新时间',
`update_by` datetime NULL COMMENT '更新人',
`remarks` varchar(255) NULL COMMENT '备注说明',
PRIMARY KEY (`provider_user_id`)
)
COMMENT='服务提供者基本信息表'
;

CREATE TABLE `svf_service_info` (
`service_Id` int(45) NOT NULL AUTO_INCREMENT COMMENT '服务定义ID',
`service_description` varchar(100) NULL DEFAULT NULL COMMENT '服务定义描述',
`parent_id` bigint(45) NULL DEFAULT NULL COMMENT '上级服务ID',
`create_time` smallint(2) NULL DEFAULT NULL COMMENT '服务创建时间',
`create_by` double(10,3) NULL DEFAULT NULL COMMENT '服务创建人',
`update_time` varchar(45) NULL DEFAULT NULL COMMENT '服务更新时间',
`update_by` varchar(100) NULL DEFAULT NULL COMMENT '服务更新人',
`remarks` datetime NULL DEFAULT NULL COMMENT '备注说明',
`service_mode` datetime NULL DEFAULT NULL COMMENT '服务模式\n1. 一次性服务\n2.周期性服务',
`occupation_id` varchar(45) NULL DEFAULT NULL COMMENT '服务所属职业ID',
`service_code` varchar(255) NULL COMMENT '服务代码',
`estimate_time` datetime NULL COMMENT '预估服务时间',
`rate_price` decimal(10,8) NULL COMMENT '服务费率\n1.flat rate 一次费用\n2.by unit 按件，按面积等\n3.default 按小时收费\n\n',
`default_price` decimal(10,8) NULL COMMENT '默认费用',
PRIMARY KEY (`service_Id`)
)
COMMENT='服务定义表'
;

CREATE TABLE `svf_occupation_info` (
`occupation_id` varchar(45) NOT NULL DEFAULT '0' COMMENT '职业ID',
`occupation_Description` varchar(100) NULL DEFAULT NULL COMMENT '职业定义描述',
`create_time` varchar(45) NULL DEFAULT NULL COMMENT '职业创建时间',
`create_by` smallint(2) NULL DEFAULT 0 COMMENT '职业创建人',
`update_time` smallint(2) NULL DEFAULT 0 COMMENT '职业更新时间',
`remarks` varchar(10) NULL DEFAULT NULL COMMENT '职业删除标记',
`update_by` varchar(250) NULL DEFAULT NULL COMMENT '职业删除时间',
`occupation_cat_id` datetime NULL DEFAULT NULL COMMENT '职业定义类别',
PRIMARY KEY (`occupation_id`) ,
INDEX `def_inidata_ix4` (`occupation_id`)
)
COMMENT='指标计算源数据项定义表'
;

CREATE TABLE `svf_service_pakage_info` (
`packageDefId` varchar(45) NOT NULL DEFAULT '0' COMMENT '服务包定义',
`pakageDefDescription` varchar(45) NULL DEFAULT NULL COMMENT '服务包描述',
`createTime` varchar(45) NULL DEFAULT NULL COMMENT '服务包创建时间',
`createUser` datetime NULL DEFAULT NULL COMMENT '服务包创建人',
`updateTime` varchar(45) NULL DEFAULT NULL COMMENT '服务包更新时间',
`delMark` int(11) NULL DEFAULT NULL COMMENT '服务包删除标记',
`delTime` varchar(45) NULL DEFAULT NULL COMMENT '服务包删除时间',
`occupationDefId` float(20,2) NULL DEFAULT NULL COMMENT '职业定义ID',
`serviceCatDefId` date NULL DEFAULT NULL COMMENT '服务类别ID',
`pakageCode` varchar(255) NULL COMMENT '服务包代码',
PRIMARY KEY (`packageDefId`) ,
INDEX `fk_value_class_value_cat_1` (`createTime`),
INDEX `fk_value_class_def_distr_1` (`delMark`),
INDEX `dclassId` (`pakageDefDescription`),
INDEX `value_class_ix4` (`packageDefId`)
)
COMMENT='KPI值表'
;

CREATE TABLE `usf_user_info` (
`user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户Id',
`user_name` varchar(255) NULL COMMENT '用户姓名',
`user_password` varchar(50) NULL COMMENT '用户密码',
`update_by` datetime NULL COMMENT '更新人',
`user_id_num` varchar(50) NULL COMMENT '用户身份证号',
`user_tel_num` varchar(20) NULL COMMENT '用户手机号码',
`user_email` varchar(255) NULL COMMENT '用户电子邮箱',
`user_gender` tinyint(2) NULL COMMENT '用户性别',
`user_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '1-普通用户，2-服务提供者',
`user_birthday` datetime NULL,
`user_head_pic` varchar(255) NULL,
`user_real_name` varchar(255) NULL,
`create_time` datetime NULL COMMENT '用户创建时间',
`update_time` datetime NULL COMMENT '用户更新时间',
`create_by` varchar(255) NULL COMMENT '创建人',
`status` tinyint(4) NULL DEFAULT 1-valid,0-invalid COMMENT '用户状态',
`remarks` varchar(255) NULL COMMENT '备注说明',
PRIMARY KEY (`user_id`)
)
COMMENT='系统用户基本信息表'
;

CREATE TABLE `usf_roles_class_info` (
`roleId` int NOT NULL COMMENT '角色Id',
`roleName` varchar(255) NULL COMMENT '角色名称',
`roleDescription` varchar(255) NULL COMMENT '角色说明',
`rolePrivilege` varchar(255) NULL COMMENT '角色权限',
`roleTableName` varchar(255) NULL,
PRIMARY KEY (`roleId`)
)
COMMENT='用户角色类别表'
;

CREATE TABLE `usf_user_role_map` (
`roleId` int NOT NULL,
`userId` int NULL,
PRIMARY KEY (`roleId`)
)
COMMENT='角色和用户映射表'
;

CREATE TABLE `svf_service_category_info` (
`service_cat_id` int NOT NULL COMMENT '服务类别ID',
`service_cat_description` varchar(255) NULL COMMENT '服务类别描述',
`create_time` date NULL COMMENT '服务类别创建时间',
`create_by` varchar(255) NULL COMMENT '服务类别创建人',
`update_time` date NULL COMMENT '服务类别更新时间',
`remarks` varchar(255) NULL COMMENT '服务类别删除标记',
`update_by` date NULL COMMENT '服务类别删除时间',
`occupation_id` varchar(255) NULL COMMENT '服务类别所属职业ID',
PRIMARY KEY (`service_cat_id`)
);

CREATE TABLE `svf_service_pakage_map` (
`serviceDefId` int NOT NULL COMMENT '服务定义ID',
`pakageDefId` int NULL COMMENT '服务包ID',
PRIMARY KEY (`serviceDefId`)
);

CREATE TABLE `usf_csr_basic_info` (
`csrId` int NOT NULL,
PRIMARY KEY (`csrId`)
);

CREATE TABLE `usf_orgnization_basic_info` (
`orgnizationId` int NOT NULL,
PRIMARY KEY (`orgnizationId`)
)
COMMENT='服务组织，可以包含多个客服人员和家政服务人员，并对家政服务人员进行考核评分（考核评分最好能由客户来进行，或者组织服务进行。'
;

CREATE TABLE `usf_sales_basic_info` (
`salesId` int NOT NULL,
PRIMARY KEY (`salesId`)
)
COMMENT='销售基本信息表'
;

CREATE TABLE `usf_crew_basic_info` (
`crewId` int NOT NULL,
PRIMARY KEY (`crewId`)
)
COMMENT='团队信息'
;

CREATE TABLE `svf_product_info` (
`product_id` int NOT NULL,
`product_name` varchar(255) NULL,
`product_description` varchar(255) NULL,
`service_id` int NULL,
`provider_id` int NULL,
`create_time` date NULL,
`create_by` varchar(255) NULL,
`update_time` date NULL,
`update_by` varchar(255) NULL,
`product_pakage_id` int NULL,
`service_pakage_id` int NULL,
`product_cat_Id` int NULL,
`product_code` varchar(255) NULL,
PRIMARY KEY (`product_id`)
);

CREATE TABLE `svf_product_category_info` (
`productCatDefId` int NOT NULL,
`productCatDefDescription` varchar(255) NULL,
`productCatDefCreateTime` date NULL,
`productCatDefCreateUser` varchar(255) NULL,
`productCatDelMark` varchar(255) NULL,
`productCatDelTime` date NULL,
`productCatUpdateTime` date NULL,
PRIMARY KEY (`productCatDefId`)
);

CREATE TABLE `sbsf_order_base_info` (
`order_id` int NOT NULL,
`create_time` date NULL,
`order_status` varchar(255) NULL,
`customer_id` int NULL,
`provider_id` int NULL,
`subscription_time` date NULL,
`service_id` int NULL,
`estimate_price` decimal(10,2) NULL,
`real_price` decimal(10,2) NULL,
`start_time` date NULL,
`end_time` date NULL,
`departure_time` date NULL,
`estimate_duration` date NULL,
`real_serv_duration` date NULL,
`order_number` longtext NULL,
PRIMARY KEY (`order_id`)
);

CREATE TABLE `sbsf_payment_info` (
`payment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '交易流水ID',
`expense_amount` decimal(255,0) NULL COMMENT '交易流水费用',
`expense_description` varchar(255) NULL COMMENT '交易流水说明',
`create_time` datetime NULL COMMENT '交易流水创建时间',
`create_by` varchar(255) NULL COMMENT '创建人',
`update_time` datetime NULL COMMENT '更新时间',
`update_by` varchar NULL COMMENT '更新人',
`withdraw_time` datetime NULL COMMENT '回退时间',
`withdraw_by` varchar(255) NULL COMMENT '回退发起人',
`status` varchar(255) NULL COMMENT '交易流水状态',
PRIMARY KEY (`payment_id`)
)
COMMENT='交易流水主表\n'
;

CREATE TABLE `sbsf_order_addon_info` (

);

CREATE TABLE `evlf_evaluation_info` (
`evaluation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
`evalucation_description` varchar(255) NULL COMMENT '评价内容',
`evaluation_score` varchar(255) NULL COMMENT '评价分数',
`create_time` datetime NULL COMMENT '创建时间',
`create_by` varchar(255) NULL COMMENT '创建人',
`update_time` datetime NULL COMMENT '更新时间',
`update_by` varchar NULL COMMENT '更新人',
`satus` varchar(255) NULL COMMENT '状态',
`remarks` varchar(255) NULL COMMENT '备注说明',
`provider_id` bigint(11) NULL COMMENT '服务提供者ID',
`order_id` bigint(11) NULL COMMENT '订单ID',
PRIMARY KEY (`evaluation_id`)
)
COMMENT='评价信息表'
;

CREATE TABLE `trtf_info` (

);

CREATE TABLE `usf_provider_certification_info` (
`certification_id` bigint NOT NULL AUTO_INCREMENT COMMENT '证书id',
`certification_descr` varchar(255) NULL COMMENT '证书说明',
`certification_name` varchar(255) NULL COMMENT '证书名称',
`provider_id` bigint NULL COMMENT '服务提供者id',
`status` varchar(255) NULL COMMENT '证书状态',
`create_time` datetime NULL COMMENT '创建时间',
`create_by` varchar(255) NULL COMMENT '创建人',
`update_time` datetime NULL COMMENT '更新时间',
`update_by` datetime NULL COMMENT '更新人',
`remarks` varchar(255) NULL COMMENT '备注说明',
`expire_time` datetime(255) NULL COMMENT '证书过期时间',
PRIMARY KEY (`certification_id`)
)
COMMENT='服务提供者证书信息表'
;

CREATE TABLE `sbsf_open_duration` (
`openDurationId` int NOT NULL,
`providerId` int NULL,
`openDate` date NULL,
`startTime` date NULL,
`endTime` date NULL,
`createTime` date NULL,
`updateTime` date NULL,
`delMark` varchar(255) NULL,
`delTime` date NULL,
PRIMARY KEY (`openDurationId`)
)
COMMENT='开放预约表'
;

CREATE TABLE `sbsf_payment_detail` (
`payment_detail_id` bigint NOT NULL COMMENT '交易流水明细ID',
`payment_id` bigint NULL COMMENT '交易流水ID',
`detail_info` varchar(255) NULL COMMENT '流水明细详细信息',
`detail_amount` decimal(10,8) NULL,
`create_time` datetime NULL COMMENT '流水明细创建时间',
`create_by` varchar(255) NULL COMMENT '流水明细创建人',
`update_time` datetime NULL COMMENT '更新时间',
`update_by` varchar NULL COMMENT '更新人',
`remarks` varchar(255) NULL COMMENT '备注说明',
`status` varchar(255) NULL COMMENT '流水明细状态',
`withdraw_time` datetime(10) NULL COMMENT '回退时间',
`withdraw_amount` decimal(10,8) NULL COMMENT '回退金额',
PRIMARY KEY (`payment_detail_id`)
)
COMMENT='交易流水明细表'
;

CREATE TABLE `csf_medical_case_info` (
`medical_case_id` bigint NOT NULL COMMENT '病案ID',
`symptom_description` varchar(255) NULL COMMENT '病例描述',
`customer_id` bigint NULL COMMENT '客户ID',
`provider_id` bigint(11) NULL,
`order_id` bigint(11) NULL COMMENT '订单ID',
PRIMARY KEY (`medical_case_id`)
)
COMMENT='病案表'
;

CREATE TABLE `svf_service_usf_provider_map` (
`map_id` bigint NOT NULL COMMENT '服务者提供服务的映射ID',
`provider_id` bigint NULL COMMENT '服务提供者ID',
`service_id` bigint NULL COMMENT '服务ID',
`price_id` bigint NULL COMMENT '服务定价ID',
`evaluation_id` bigint(11) NULL COMMENT '评价ID',
PRIMARY KEY (`map_id`)
)
COMMENT='服务者能够提供的服务'
;

