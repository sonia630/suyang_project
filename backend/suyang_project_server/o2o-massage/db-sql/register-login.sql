-- 验证码信息表
DROP TABLE IF EXISTS `verify_code_info`;
CREATE TABLE `verify_code_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `phone_no` varchar(100) NOT NULL COMMENT '手机号',
  `verify_code` smallint NOT NULL COMMENT '4位验证码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `code_state` tinyint  DEFAULT '0' COMMENT '状态，0-未使用,1-已使用',
  `scenario` tinyint  DEFAULT '1' COMMENT '1-注册,2-忘记密码,3-更换手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证码信息表';

-- Token信息表
DROP TABLE IF EXISTS `login_token`;
CREATE TABLE `login_token` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(20) NOT NULL COMMENT '用户唯一ID',
  `device_id` varchar(50) DEFAULT 'default' COMMENT '设备ID',
  `login_token` varchar(255) NOT NULL COMMENT 'token信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `expire_time` datetime NOT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`),
  KEY `idx_token` (`login_token`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COMMENT='TOKEN表';
