CREATE TABLE `s_admin_role` (
  `id` varchar(32) NOT NULL COMMENT '角色ID',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '角色名称',
  `permissions` char(64) NOT NULL DEFAULT '' COMMENT '权限',
  `menus` mediumtext NOT NULL COMMENT '菜单',
  `notes` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `updated_at` datetime NOT NULL COMMENT '更新时间',
  `updater_id` varchar(32) NOT NULL COMMENT '更新者ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `s_admin_role` VALUES ('3e2b98f9f0724b2187865427b21f935e','管理员','n000000000000000007oL3`60`00000000000000000000000000000000000000','[]','n/a','2017-02-08 14:22:46','0d52284f8469464ea820e45f46b927e3');


CREATE TABLE `s_admin_user` (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `hash` varchar(8) DEFAULT NULL COMMENT 'Hash',
  `password` varchar(64) NOT NULL COMMENT '加密后口令',
  `secure_password` varchar(64) NOT NULL DEFAULT '' COMMENT '安全口令',
  `email` varchar(200) DEFAULT NULL COMMENT 'email',
  `real_name` varchar(200) DEFAULT NULL COMMENT '真实姓名',
  `cellphone` varchar(32) NOT NULL COMMENT '手机号',
  `role_id` varchar(32) NOT NULL COMMENT '用户角色ID',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '状态, 0:正常, 1:锁定',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) NOT NULL COMMENT '创建者ID',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新者ID',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本用于乐观锁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cellphone` (`cellphone`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `email` (`email`),
  KEY `created_at` (`created_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `s_admin_user` VALUES ('0d52284f8469464ea820e45f46b927e3',0,'admin','9723ac6b','ebe64284b51862be9986d2cf5d7f71f5dd1d9be2f5fcc6834fe20984808da3c8','ebe64284b51862be9986d2cf5d7f71f5dd1d9be2f5fcc6834fe20984808da3c8','sys@rockbb.com','管理员','13810291102','3e2b98f9f0724b2187865427b21f935e',0,'2016-08-05 16:03:29','445adc19a9474666977316716afcd8a0','2017-01-04 20:41:45','0d52284f8469464ea820e45f46b927e3',3);


CREATE TABLE `s_auth_rule` (
  `id` varchar(32) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '权限名称, 用于显示',
  `name` varchar(255) NOT NULL DEFAULT '',
  `regex` varchar(255) NOT NULL DEFAULT '',
  `auth_index` mediumint(4) unsigned NOT NULL DEFAULT '0',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `index` (`auth_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `s_auth_rule` VALUES ('bef6543571594daeb27844b1bba49d61','访问首页','index','^\\/(index|file|region|my_\\w+).*$',0,0);


CREATE TABLE `s_config` (
  `category` varchar(32) NOT NULL COMMENT '对于多个子系统, 相同的配置使用不同的category区分',
  `title` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `value` mediumtext NOT NULL,
  `type` tinyint(2) NOT NULL DEFAULT '0',
  `default_value` mediumtext NOT NULL,
  `notes` varchar(1024) NOT NULL DEFAULT '',
  PRIMARY KEY (`category`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `s_config` VALUES ('sys','APP启动令牌','app_boot_token','433a79cc028a4e7d9278294eb86a91e6',0,'455a79cc028a4e7d9278294eb86a91e6',''),('sys','APP配置信息','app_config','{\"apiVersion\":2, \"apiMinVersion\":1}',6,'{\"apiVersion\":2, \"apiMinVersion\":1}','');

CREATE TABLE `s_session` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `app` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) NOT NULL DEFAULT '',
  `started_at` bigint(19) unsigned NOT NULL DEFAULT '0',
  `updated_at` bigint(19) unsigned NOT NULL DEFAULT '0',
  `language` tinyint(3) unsigned NOT NULL,
  `autologin` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `token` varchar(32) NOT NULL DEFAULT '',
  `secure1` varchar(255) NOT NULL DEFAULT '',
  `secure2` varchar(255) NOT NULL DEFAULT '',
  `secure3` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `updated_at` (`updated_at`),
  KEY `user_id` (`user_id`),
  KEY `started_at` (`started_at`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8;

CREATE TABLE `s_session_log` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `session_id` varchar(32) NOT NULL DEFAULT '',
  `app` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) NOT NULL DEFAULT '',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `autologin` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `language` tinyint(3) NOT NULL,
  `secure1` varchar(255) NOT NULL DEFAULT '',
  `secure2` varchar(255) NOT NULL DEFAULT '',
  `secure3` varchar(255) NOT NULL DEFAULT '',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `created_at` (`created_at`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `u_user` (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `type` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `hash` varchar(8) DEFAULT NULL COMMENT 'hash salt',
  `password` varchar(64) DEFAULT NULL COMMENT '登录口令',
  `secure_password` varchar(64) DEFAULT NULL COMMENT '安全口令',
  `email` varchar(200) DEFAULT NULL COMMENT 'email',
  `real_name` varchar(200) DEFAULT NULL COMMENT '真实姓名',
  `cellphone` varchar(32) NOT NULL COMMENT '手机号',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '状态, 0:正常, 1:系统锁定, 2:人工锁定',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) NOT NULL COMMENT '创建者ID',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `updater_id` varchar(32) DEFAULT NULL COMMENT '更新者ID',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本用于乐观锁',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cellphone` (`cellphone`),
  UNIQUE KEY `name` (`name`),
  KEY `created_at` (`created_at`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `u_user` VALUES ('53d2284f8469464ea820e45f46b927f9',0,'milton','9723ac6b','ebe64284b51862be9986d2cf5d7f71f5dd1d9be2f5fcc6834fe20984808da3c8','ebe64284b51862be9986d2cf5d7f71f5dd1d9be2f5fcc6834fe20984808da3c8',NULL,'Milton','13810291103',0,'2016-08-05 16:03:29','445adc19a9474666977316716afcd8a0','2017-01-04 20:41:45','0d52284f8469464ea820e45f46b927e3',3);

CREATE TABLE `u_user_log` (
  `id` varchar(32) NOT NULL,
  `session_id` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) NOT NULL DEFAULT '',
  `ip` varchar(32) NOT NULL DEFAULT '' COMMENT 'IP',
  `action_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:验证码错, 1:登录密码错, 2:交易密码错',
  `secure1` varchar(255) DEFAULT NULL,
  `secure2` varchar(255) DEFAULT NULL,
  `secure3` varchar(255) DEFAULT NULL,
  `notes` mediumtext NOT NULL COMMENT '备注信息',
  `created_at` datetime NOT NULL COMMENT '日志时间',
  PRIMARY KEY (`id`),
  KEY `created_at` (`created_at`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

