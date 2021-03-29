-- --------------------------------------------------------
-- 服务器版本:                        5.7.26-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 uds_data 的数据库结构
CREATE DATABASE IF NOT EXISTS `uds_data` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `uds_data`;

-- 导出  表 uds_data.uds_constant 结构
CREATE TABLE IF NOT EXISTS `uds_constant` (
  `key` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '对应UdsConstant.java 里面的变量名 ',
  `value` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '对应的值',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='uds服务全局常量表，修改后需要load加载';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_error 结构
CREATE TABLE IF NOT EXISTS `uds_error` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '事件ID',
  `code` int(11) NOT NULL DEFAULT '0' COMMENT '错误码',
  `msg_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发生日期',
  `msg_level` tinyint(4) DEFAULT '0' COMMENT '重视程度：L-Low:1 M-Medium:2 H-High:3',
  `msg_par` varchar(512) COLLATE utf8mb4_bin DEFAULT '' COMMENT '错误参数（发送message需要的参数）',
  `msg` varchar(512) COLLATE utf8mb4_bin DEFAULT '' COMMENT '错误描述信息',
  `dispose` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否处理 ：0没有处理;1处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4641656 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='错误事件，报错信息表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job 结构
CREATE TABLE IF NOT EXISTS `uds_job` (
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台名',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '应用系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业名',
  `job_name` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '作业名中文名',
  `job_type` varchar(8) COLLATE utf8mb4_bin NOT NULL DEFAULT 'D' COMMENT '作业类型：D 每天执行(来信号文件就执行)，W 每周执行（触发里面检测信号文件来的星期几），M 每月执行（检测触发里面是否有对应的日期 0为月末）Y(具体执行年) S 触发执行 ，V 虚作业 ，C 自定义执行（多为定时执行）',
  `server_name` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT '服务器分配',
  `last_status` varchar(16) COLLATE utf8mb4_bin NOT NULL DEFAULT 'Ready' COMMENT '作业运行状态：Ready Done Runing Failed Pending',
  `job_date` varchar(16) COLLATE utf8mb4_bin NOT NULL DEFAULT '20180701' COMMENT '最后执行作业信号文件日期 yyyyMMdd',
  `batch` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '批次号： 0非多批次作业；非0多批次作业',
  `timewindow` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '00:01-23:59 ' COMMENT '窗口执行时间 小时单位 0|23 0-23 点为执行窗口时间',
  `pending_time` datetime DEFAULT NULL COMMENT '进入pending状态的时间',
  `dispatcher_time` datetime DEFAULT NULL COMMENT '进入dispatcher状态的时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间时间',
  `virtual_enable` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '作业是否执虚->1：虚作业 ，0：非虚作业 默认：1执虚作业,不再执行脚本。',
  `last_script_name` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT ' ' COMMENT '最后执行的脚本',
  `num_times` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '作业运行次数',
  `priority` smallint(6) NOT NULL DEFAULT '100' COMMENT '作业执行优先级 开发范围 0-1000 ；1000最高;',
  `call_again_max_num` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '自动重调次数',
  `call_again_num` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '当前重调次数',
  `is_enable` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否执行 1：执行 0：不执行 默认：1',
  `location` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '这里才用位标记地从右往左第一位为  上海，合肥； 1转换为2进制01，表示上海， 2转换二进制，10，表示合肥',
  `check_frequency` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否检测时间：检测来信号文件间隔时间 0：不检测，1检测  默认：0 针对D W M Y的作业类型检测 关联uds_job_frequency表',
  `check_time_trigger` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '检测是否采用时间触发：0：不检测，1检测  默认：0；关联uds_job_time_trigger表',
  `check_file_stream` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用stream file 检测触发 0不采用 1采用',
  `ignore_error` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否忽视错误现场 0，不忽视；1是忽视',
  `check_weight` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否检测权重',
  `des` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业信息表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_date_frequency 结构
CREATE TABLE IF NOT EXISTS `uds_job_date_frequency` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  `minute` varchar(4) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '分钟 0-59',
  `hour` varchar(4) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '执行时间检测 0-23',
  `day` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '执行天数检测  ',
  `month` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '月份检测',
  `week` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '执行的星期',
  `year` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '年份检测',
  `is_enable` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否使用 1使用 0不启用',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `job` (`job`)
) ENGINE=InnoDB AUTO_INCREMENT=145697 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='检测信号文件来的时间是否符合要求，作业的间隔时间，及频率控制';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_date_trigger 结构
CREATE TABLE IF NOT EXISTS `uds_job_date_trigger` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  `offset_day` tinyint(4) NOT NULL DEFAULT '0' COMMENT '偏移天数;+1开始时间日期加1天,-1开始时间日期减1天',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下次执行时间',
  `end_time` datetime NOT NULL DEFAULT '2099-01-01 00:00:01' COMMENT '停止时间',
  `second` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '秒  0-59  可使用:  " * "每秒触发 ;" , " 指定秒数触发如0,15,45;代表 0，15，45秒的时候触发 “-”区间触发 如25-45；在25秒到45秒，每隔一秒触发一次； “ / ”步进触发 如 */20 从0开始每隔20秒触发一次  ',
  `minute` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '分钟 0-59   可使用: - * /     */3 每隔3分钟  5-50/3 在5到50分钟，每隔3分钟',
  `hour` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '时  0-23 可使用: - * /        */3 每个3小时 ',
  `day` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '天数  1-31  可使用: - * /  ？ L W  C  L最后一天 W 工作日 1W 第一个工作日         /3 每隔3天 3号       ； ？与周互斥',
  `month` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '月 1-12或者JAN-DEC  - * /     /3每隔 3个月 3月',
  `week` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '周   1-7 或者 SUN-SAT   配置 ； ？与日配置互斥',
  `year` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '年 1970-2099. -*/ ',
  `is_enable` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用；0不启用 1启用',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT ' ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `job` (`job`)
) ENGINE=InnoDB AUTO_INCREMENT=90315 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='定时和触发作业表：模仿 crontab 的方式 ';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_dependency 结构
CREATE TABLE IF NOT EXISTS `uds_job_dependency` (
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  `dep_platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '依赖 平台',
  `dep_system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '依赖 系统',
  `dep_job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '依赖 作业',
  `dep_batch` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单批次触发多批次，依赖批次号',
  `is_enable` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否使用',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`dep_job`,`job`),
  KEY `idx_job_dep_job` (`job`,`dep_job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业依赖表 ';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_record 结构
CREATE TABLE IF NOT EXISTS `uds_job_record` (
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '应用',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业名',
  `job_type` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '作业类型',
  `last_status` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '作业最后状态 （ Done 和 Failed）',
  `server_name` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `pending_time` datetime DEFAULT NULL COMMENT '作业进入pending状态时间',
  `dispatcher_time` datetime DEFAULT NULL COMMENT '进入dispatcher状态的时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `job_date` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '作业时间',
  `virtual_enable` tinyint(2) NOT NULL DEFAULT '0',
  `batch` int(11) NOT NULL DEFAULT '0' COMMENT '批次号',
  `num_times` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行次数',
  KEY `job` (`job`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='uds 作业记录信息记录 与 etl_job';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_source 结构
CREATE TABLE IF NOT EXISTS `uds_job_source` (
  `src_signal` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '源新号文件',
  `conv_signal` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '转化后信号文件',
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  UNIQUE KEY `job` (`job`),
  UNIQUE KEY `src_signal` (`src_signal`),
  UNIQUE KEY `conv_signal` (`conv_signal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业转换表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_step 结构
CREATE TABLE IF NOT EXISTS `uds_job_step` (
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台名',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统名',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业名',
  `setp_num` tinyint(4) unsigned NOT NULL COMMENT '执行步骤',
  `oper_cmd` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '执行命令',
  `script_dir` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '脚本路径',
  `script_name` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '脚本名',
  `parameter` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '执行参数 空格 分割',
  `step_type` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT 'T' COMMENT '脚本类型(perl,python)',
  `environments` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '环境变量 KEY=VALUE;KEY=VALUE',
  `work_dir` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '工作路径',
  `is_enable` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否可以使用 0：不可以 1：可以；默认1',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`job`,`setp_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='执行脚本的配置表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_step_record 结构
CREATE TABLE IF NOT EXISTS `uds_job_step_record` (
  `id` bigint(20) unsigned NOT NULL,
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  `setp_num` tinyint(3) unsigned NOT NULL,
  `return_code` int(11) NOT NULL DEFAULT '0' COMMENT '返回CODE',
  `virtual_enable` tinyint(4) NOT NULL DEFAULT '0',
  `num_times` bigint(20) unsigned NOT NULL COMMENT '执行次数',
  `server_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `job_date` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '作业日期',
  `script_name` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '脚本名字',
  `log_path` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日志路径',
  `log_name` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日志名字',
  PRIMARY KEY (`id`),
  KEY `job` (`job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业脚本执行的记录';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_stream 结构
CREATE TABLE IF NOT EXISTS `uds_job_stream` (
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '系统',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  `stm_platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '触发_平台',
  `stm_system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '触发_系统',
  `stm_job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '触发_作业',
  `stm_batch` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '多批次触发单批次，触发批次号',
  `is_enable` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否使用',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT '',
  PRIMARY KEY (`job`,`stm_job`),
  UNIQUE KEY `stm_job` (`stm_job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='触发表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_tags 结构
CREATE TABLE IF NOT EXISTS `uds_job_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '应用',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业',
  `tag` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '标签',
  `tag_type` tinyint(4) NOT NULL COMMENT '标签类型',
  `desc` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `is_enable` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `platfrom` (`platform`,`system`,`job`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业，应用，平台 标签';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_weight 结构
CREATE TABLE IF NOT EXISTS `uds_job_weight` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '应用',
  `job` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '作业名',
  `limit_type` smallint(6) NOT NULL COMMENT '资源类型',
  `conf_value` int(11) NOT NULL DEFAULT '0' COMMENT '占用资源数',
  `desc` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `limit` (`platform`,`system`,`job`,`limit_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2739 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业权重配置表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_job_weight_limit 结构
CREATE TABLE IF NOT EXISTS `uds_job_weight_limit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `limit_type` smallint(6) NOT NULL COMMENT '类型类型',
  `limit_value` int(11) DEFAULT '0' COMMENT '资源上限',
  `desc` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `limit` (`limit_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作业权重表限制表';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_server 结构
CREATE TABLE IF NOT EXISTS `uds_server` (
  `server_name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '服务器名字',
  `order` smallint(6) unsigned NOT NULL COMMENT '服务器启动顺序，当为主服务器的时候谁先来，按照优先级启动服务器',
  `ip` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '服务器iP',
  `port` int(11) NOT NULL DEFAULT '9696' COMMENT '服务通讯端口',
  `agent_port` int(11) NOT NULL DEFAULT '6969' COMMENT 'telent后台连接端口',
  `http_port` int(11) NOT NULL DEFAULT '7878' COMMENT 'http访问端口',
  `server_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0为子节点，1为主节点',
  `max_job_num` smallint(6) NOT NULL DEFAULT '30' COMMENT '并发JOB数目，设置后需要后台 load',
  `performance_ratio` smallint(6) NOT NULL DEFAULT '1000' COMMENT '性能比值，千分比 ，涉及性能要乘以此比值',
  `last_start` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次启动时间',
  `last_end` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关闭时间',
  `location` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '这里才用位标记地从右往左第一位为  上海，合肥； 1转换为2进制01，表示上海， 2转换二进制，10，表示合肥',
  `tags` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '机器标签;分割',
  `is_enable` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否启用 需要加载',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT ' ' COMMENT '描述',
  PRIMARY KEY (`server_name`),
  UNIQUE KEY `oreder` (`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='服务节点信息，配置信息。';

-- 数据导出被取消选择。

-- 导出  表 uds_data.uds_system 结构
CREATE TABLE IF NOT EXISTS `uds_system` (
  `platform` varchar(4) COLLATE utf8mb4_bin NOT NULL COMMENT '平台',
  `system` varchar(4) COLLATE utf8mb4_bin NOT NULL DEFAULT '*' COMMENT '系统(*针对所有系统)',
  `max_run_job` smallint(6) unsigned NOT NULL DEFAULT '30' COMMENT '该应用的最大运行作业数',
  `strategy` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '分发策略; 0:普通策略；1.指定机器分发机器',
  `strategy_pro` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '策略参数（1.机器信息） !A!B;C,D',
  `use_platform` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:使用平台数据；0自己单独数据',
  `des` varchar(256) COLLATE utf8mb4_bin DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`system`,`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='记录平台系统';

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
