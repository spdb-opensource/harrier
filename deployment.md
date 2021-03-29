# 一 系统简介

Harrier采用主从架构设计，且主从节点所运行的调度服务源码完全一致，仅通过数据库uds_server表中server_type字段进行区分（1为主节点，0为字子节点），主节点负责调度任务的收集和分发，子节点负责周期性向主节点发送心跳和负载情况及执行主节点分配的调度任务。

调度系统主节点负责检测作业是否满足执行条件，作业的执行条件主要通过以下三种方法：

1、信号文件触发。

外部系统向调度服务器指定路径发送信号文件后，触发对应作业。信号文件dir.0@UDS_SYS_TEST_D20210101，由三部分组成，固定前缀（dir.0@） 作业名（UDS_SYS_TEST_D）作业日期（yyyymmdd）。

2、定时触发。

通过作业自身的定时配置（uds_job_date_trigger表）来触发作业的运行。

3、作业触发。

通过uds_job_stream表中配置的触发关系来实现作业触发。

作业执行完成后，系统会根据数据库中配置的作业间的触发关系（uds_job_stream表），将下游作业置入pending队列（等待条件满足的队列）；系统将会遍历pending队列中的作业，依赖满足（uds_job_dependency表）或资源满足后分发该作业。

子节点由多台服务器组成，是调度任务的真正执行者。主节点将内存队列中的作业通过RPC协议分发至各个子节点，子节点执行作业对应的作业脚本。

# 二 部署文档

## 2.1 软硬件环境建议配置

| 名称      | 版本                                                     |
| --------- | -------------------------------------------------------- |
| CentOS7.6 | 7.6及以上（必选，操作系统可换其他Linux，但不推荐）       |
| MySQL     | 5.7.25及以上（必选，数据库可替换为其他数据库，但不推荐） |
| Python    | 3.7.3及以上（必选，可选其他版本，但不推荐）              |
| Perl      | 5.16及以上（必选，可选择其他版本，但不推荐）             |
| Open JDK  | 1.8.0_191及以上（必选）                                  |

## 2.2单机部署(StandAlone)

### 2.2.1部署前准备

1、  准备一台满足[软硬环境建议配置](#_软硬件环境建议配置)的节点服务器。

2、  处于稳定运行状态的MySQL数据库，执行uds_data.sql 以及 uds_init_job.sql 导入库表结构及示例作业。

3、  更新uds_server表中节点服务器server_name字段及ip字段，并且server_type=1 max_job_num=100,表示最大执行作业数量为100的主节点，单机部署下需打开主节点并发，例如：

```sql
replace into `uds_server` (`server_name`, `order`, `ip`, `port`, `agent_port`, `http_port`, `server_type`, `max_job_num`, `performance_ratio`, `last_start`, `last_end`, `location`, `tags`, `is_enable`, `des`) values ('udsmaster01', 1, '127.0.0.1', 9696, 6969, 7878, 1, 100, 1000, '2021-03-18 10:52:05', '2021-03-18 10:46:34', 1, '', 1, ' ');
```

4、 将uds.zip压缩包上传至节点服务器根目录下，执行unzip uds.zip 解压缩至根目录。

### 2.2.2基础环境配置

```shell
#永久关闭selinux
sed -i s/SELINUX=*/SELINUX=disabled/g /etc/selinux/config
#关闭防火墙
systemctl stop firewalld.service
systemctl disable firewalld.service
#uds用户及用户组配置
groupadd -g 3001 udsg
useradd -u 4001 -d /home/uds -g 3001 uds
echo " pwd "  | passwd  --stdin uds
#权限授予
chown -R uds:udsg /uds
```

### 2.2.3节点配置

```shell
1、修改配置文件

vi /uds/server/config/uds.configuration.properties

#更新jdbc配置，包括jdbc_url及jdbc.username jdbc.password

#更新SERVER_NAME和uds_server表中server_name字段保持一致。

2、uds用户启动调度服务

sh /uds/server/start.sh 

3、查看调度服务运行日志

cat /uds/server/logs/app.log 

#以下日志信息表示主节点启动成功。

#[main] [INFO][com.spdb.uds.Server]- udsmaster01 start MasterManager

4、启动本地部署

telnet 127.0.0.1 6969 

在telnet命令行下执行SendLocate启动本地部署模式
```



### 2.2.4服务验证

调度系统中预设四个示例作业，分别为

日作业：UDS_SYS_TEST_D  UDS_SYS_TEST_D1

UDS_SYS_TEST_D执行完成后触发UDS_SYS_TEST_D1

月作业：UDS_SYS_TEST_M 

定时作业：UDS_SYS_TEST_C

作业所执行的脚本均为bin目录下的UDS_SYS_TEST_SLEEP.pl

#### 验证信号文件触发日作业：

1、 上传信号文件dir.0@UDS_SYS_TEST_D20210113 (日期应为uds_job表中job_date + 1)至/uds/data/receive目录下，调度服务将间隔20s不断扫描receive目录，处理信号文件，扫描到信号文件后，会将信号文件对应的作业添加到pending队列中，满足条件后，该作业将被分发到子节点执行对应脚本（uds_job_step表配置脚本路径和步骤顺序）。

2、查看uds_job表中作业最新状态last_status字段，作业状态可能是

Ready Done Running Failed Pending Unknown

3、cd /uds/log 查看作业执行日志。

#### 验证定时作业的定时触发功能：

定时作业UDS_SYS_TEST_C的初始状态为20210112 Done，调度服务启动后，该作业会不断被触发执行，直到为当前日期的Done。

#### 验证作业触发功能：

使用信号文件方式触发UDS_SYS_TEST_D成功运行后，UDS_SYS_TEST_D1会自动被触发执行。

## 2.3集群部署(Cluster)

### 2.3.1部署前准备

1、准备两台以上满足[软硬环境建议配置](#_软硬件环境建议配置)的节点服务器，将其中一台作为主节点，其余作为子节点部署服务，准备一块NAS盘作为共享存储资源，节点间共享信号文件、作业脚本、作业日志等资源。

2、  处于稳定运行状态的MySQL数据库，执行uds_data.sql 以及 uds_init_job.sql导入库表结构及示例作业。

3、更新uds_server表中各个节点服务器的server_name字段及ip字段，server_type字段用于区分主子节点，1为主节点0为子节点，max_job_num=0集群部署下无需打开主节点并发，例如

```sql
REPLACE INTO `uds_server` (`server_name`, `order`, `ip`, `port`, `agent_port`, `http_port`, `server_type`, `max_job_num`, `performance_ratio`, `last_start`, `last_end`, `location`, `tags`, `is_enable`, `des`) VALUES ('udsmaster01', 1, '127.0.0.1', 9696, 6969, 7878, 1, 0, 1000, '2021-03-18 10:52:05', '2021-03-18 10:46:34', 1, '', 1, '主节点服务器');

REPLACE INTO `uds_server` (`server_name`, `order`, `ip`, `port`, `agent_port`, `http_port`, `server_type`, `max_job_num`, `performance_ratio`, `last_start`, `last_end`, `location`, `tags`, `is_enable`, `des`) VALUES ('udsslave01', 10, '10.137.104.130', 6666, 7777, 8888, 0, 100, 1000, '2021-03-18 11:09:41', '2021-03-18 11:09:32', 1, '', 1, '子节点服务器');
```

4、  将uds.zip压缩包上传至各个节点服务器根目录下，执行unzip uds.zip 解压缩至根目录。

5、在各个节点服务器根目录下创建/udsnas，并挂载NAS盘,同时执行以下命令

```shell
mv /uds/bin /udsnas/uds

chown -R uds:udsg /udsnas
```

### 2.3.2基础环境配置

同单机部署模式[2.2.2](#_2.2.2基础环境配置)。

### 2.3.3节点配置

```shell
1、修改配置文件

vi /uds/server/config/uds.configuration.properties

#更新jdbc配置，包括jdbc_url及jdbc.username jdbc.password

#更新SERVER_NAME和uds_server表中server_name字段保持一致。

#更新AUTO路径为/udsnas/uds，如下：

AUTO_HOME="/udsnas/uds"

AUTO_TMP_DIR_PATH="/udsnas/uds/tmp"

AUTO_BIN_DIR_PATH="/udsnas/uds/bin"

AUTO_ETC_DIR_PATH="/udsnas/uds/etc"

AUTO_JOB_LOG_DIR_PATH="/udsnas/uds/log"

AUTO_APP_DIR_PATH="/udsnas/uds/APP"

AUTO_RECEIVER_DIR_PATH="/udsnas/uds/data/receive"

AUTO_COMPLETE_DIR_PATH="/udsnas/uds/data/complete"

AUTO_FAIL_DIR_PATH="/udsnas /uds/data/fail"

AUTO_ERROR_DIR_PATH="/udsnas/uds/data/error"

AUTO_UNKNOW_DIR_PATH="/udsnas/uds/data/unknown"

AUTO_STREAM_DIR_PATH="/udsnas/uds/data/stream"

2、uds用户启动调度服务

#首先启动主节点服务，然后分别启动子节点服务。

sh /uds/server/start.sh 

3、查看调度服务运行日志

cat /uds/server/logs/app.log

#主节点日志信息：

#[main] [INFO][com.spdb.uds.Server]- udsmaster01 start MasterManager

#代表主节点启动成功。

#子节点日志信息：

#register Success 代表子节点注册成功。
```

### 2.3.4服务验证

同单机部署模式[2.2.4](#_2.2.4服务验证)。

# 三 作业配置说明

本节将以定时作业触发日作业的场景为例，阐述配置作业的基本方法。

## 3.1 平台、系统配置方法：

在uds_system表中进行如下配置即可：

```sql
#平台配置

insert into uds_data.uds_system 

(platform,system,max_run_job,des) 

values

('UDS','*','100','');

#应用配置

insert into uds_data.uds_system 

(platform,system,max_run_job,des) 

values 

('UDS','SYS','0','');
 
```

现在harrier中已经存在最大运行作业数（并发限制）为100的UDS平台以及使用平台并发限制的SYS应用。由于初始化SQL中已经创建UDS-SYS，所以无需重复创建。

## 3.2定时作业配置方法：

\#作业信息表配置

```sql
Insert into             

uds_data.uds_job

(platform,system,job,job_name,job_type,last_status,job_date,batch,timewindow,virtual_enable,num_times,priority,call_again_max_num,call_again_num,check_frequency,check_time_trigger,is_enable,des) 

values 

('UDS','SYS','UDS_SYS_TEST_TIME','示例定时作业','C','Done','20210112',0,'00:01-23:59',0,0,10,0,0,0,1,1,'20201124');
```

\#作业信号转换表配置

```sql
insert into uds_data.uds_job_source 

(src_signal,conv_signal,platform,system,job)  

values 

('UDS_SYS_TEST_TIME','UDS_SYS_TEST_TIME','UDS','SYS','UDS_SYS_TEST_TIME');
```

\#作业脚本表配置

```sql
Insert into uds_data.uds_job_step

(platform,system,job,setp_num,oper_cmd,script_dir,script_name,step_type,is_enable,des)  

values ('UDS','SYS','UDS_SYS_TEST_TIME',1,'perl','/uds/bin','UDS_SYS_TEST_SLEEP.pl','PL',1,'20210112');
```

\#定时触发表配置

```sql
insert into uds_data.uds_job_date_trigger 

(`platform`, `system`, `job`, `offset_day`, `start_time`, `end_time`, `second`, `minute`, `hour`, `day`, `month`, `week`, `year`, `is_enable`, `des`) 

values 

('UDS', 'SYS', 'UDS_SYS_TEST_C', 0, '2021-01-12 16:00:00', '2099-01-01 00:00:01', '0', '0', '16', '*', '*', '?', '*', 1, ' ');
```

(`platform`, `system`, `job`, `offset_day`, `start_time`, `end_time`, `second`, `minute`, `hour`, `day`, `month`, `week`, `year`, `is_enable`, `des`) 

values 

('UDS', 'SYS', 'UDS_SYS_TEST_C', 0, '2021-01-12 16:00:00', '2099-01-01 00:00:01', '0', '0', '16', '*', '*', '?', '*', 1, ' ');

现在harrier中已经存在名为UDS_SYS_TEST_TIME的定时作业，将在每天16:00 被触发去执行/uds/bin 目录下的UDS_SYS_TEST_SLEEP.pl 脚本。

## 3.3日作业配置方法：

\##作业信息表配置

```sql
Insert into uds_data.uds_job

(platform,system,job,job_name,job_type,last_status,job_date,batch,timewindow,virtual_enable,num_times,priority,call_again_max_num,call_again_num,check_frequency,check_time_trigger,is_enable,des) 

values 

('UDS','SYS','UDS_SYS_TEST_DAY','示例日作业','D','Done','20210112',0,'00:01-23:59',0,0,10,0,0,0,0,1,'20201124');
```

\#作业信号转换表配置

```sql
insert into uds_data.uds_job_source

(src_signal,conv_signal,platform,system,job)  

values

('UDS_SYS_TEST_DAY','UDS_SYS_TEST_DAY','UDS','SYS','UDS_SYS_TEST_DAY');
```

(src_signal,conv_signal,platform,system,job)  

values

('UDS_SYS_TEST_DAY','UDS_SYS_TEST_DAY','UDS','SYS','UDS_SYS_TEST_DAY');

\#作业脚本表配置

```sql
Insert into uds_data.uds_job_step 

(platform,system,job,setp_num,oper_cmd,script_dir,script_name,step_type,is_enable,des)  values ('UDS','SYS','UDS_SYS_TEST_DAY',1,'perl','/uds/bin','UDS_SYS_TEST_SLEEP.pl','PL',1,'20210112');
```

现在harrier中已经存在名为UDS_SYS_TEST_DAY 的日作业，被触发后会去执行/uds/bin 目录下的UDS_SYS_TEST_SLEEP.pl 脚本。

## 3.4 依赖触发配置方法：

\#依赖表配置

```sql
insert into uds_data.uds_job_dependency 

(platform, system, job, dep_platform, dep_system, dep_job, dep_batch, is_enable, des) values

('UDS', 'SYS', 'UDS_SYS_TEST_DAY', 'UDS', 'SYS', 'UDS_SYS_TEST_TIME', 0, 1, NULL);
```

\#触发表配置

```sql
insert into uds_data.uds_job_stream

(platform,system,job,stm_platform,stm_system,stm_job,stm_batch,is_enable,des) 

values 

('UDS', 'SYS', 'UDS_SYS_TEST_TIME', 'UDS', 'SYS', 'UDS_SYS_TEST_DAY', 0, 1, NULL);
```

这样，harrier中存在了定时作业UDS_SYS_TEST_TIME 和日作业UDS_SYS_TEST_DAY 之间的依赖触发关系，于是，每天16:00 将定时触发UDS_SYS_TEST_TIME，在其运行成功后，UDS_SYS_TEST_DAY将被触发执行。

## 3.5加载系统设置

系统设置常量的修改需要重新加载使生效，在主节点执行以下命令即可：

telnet 127.0.0.1 6969 

在telnet命令行下执行LoadUdsConstant重新加载常量。

# 四 附录

**uds_job**

| platform           | varchar  | 平台名                                                       |
| ------------------ | -------- | ------------------------------------------------------------ |
| system             | varchar  | 应用系统                                                     |
| job                | varchar  | 作业名                                                       |
| job_name           | varchar  | 作业名中文名                                                 |
| job_type           | varchar  | 作业类型：D 每天执行(来信号文件就执行)，W 每周执行（触发里面检测信号文件来的星期几），M 每月执行（检测触发里面是否有对应的日期 0为月末）Y(具体执行年) S 触发执行 ，V   虚作业 ，C 自定义执行（多为定时执行） |
| server_name        | varchar  | 服务器分配                                                   |
| last_status        | varchar  | 作业运行状态：Ready Done Runing Failed Pending               |
| job_date           | varchar  | 最后执行作业信号文件日期 yyyyMMdd                            |
| batch              | int      | 批次号： 0非多批次作业；非0多批次作业                        |
| timewindow         | varchar  | 窗口执行时间 小时单位 0\|23 0-23 点为执行窗口时间            |
| pending_time       | datetime | 进入pending状态的时间                                        |
| dispatcher_time    | datetime | 进入dispatcher状态的时间                                     |
| start_time         | datetime | 开始执行时间                                                 |
| end_time           | datetime | 结束时间时间                                                 |
| virtual_enable     | tinyint  | 作业是否执虚->1：虚作业 ，0：非虚作业   默认：1执虚作业,不再执行脚本 |
| last_script_name   | varchar  | 最后执行的脚本                                               |
| num_times          | bigint   | 作业运行次数                                                 |
| priority           | smallint | 作业执行优先级 开发范围 0-1000 ；1000最高;                   |
| call_again_max_num | tinyint  | 自动重调次数                                                 |
| call_again_num     | tinyint  | 当前重调次数                                                 |
| is_enable          | tinyint  | 是否执行 1：执行 0：不执行 默认：1'                          |
| location           | int      | 这里才用位标记地从右往左第一位为  上海，合肥； 1转换为2进制01，表示上海， 2转换二进制，10，表示合肥' |
| check_frequency    | tinyint  | 是否检测时间：检测来信号文件间隔时间 0：不检测，1检测    默认：0 针对D W M Y的作业类型检测   关联uds_job_frequency表 |
| check_time_trigger | tinyint  | 检测是否采用时间触发：0：不检测，1检测  默认：0；关联uds_job_time_trigger表 |
| check_file_stream  | tinyint  | 是否启用stream file 检测触发 0不采用 1采用                   |
| ignore_error       | tinyint  | 是否忽视错误现场 0，不忽视；1是忽视                          |
| check_weight       | tinyint  | 是否检测权重                                                 |
| des                | varchar  | 描述                                                         |

 

**uds_job_date_frequency**

| id        | bigint  | 唯一ID                 |
| --------- | ------- | ---------------------- |
| platform  | varchar | 平台                   |
| system    | varchar | 系统                   |
| job       | varchar | 作业                   |
| minute    | varchar | 分钟 0-59              |
| hour      | varchar | 执行时间检测 0-23      |
| day       | varchar | 执行天数检测           |
| month     | varchar | 月份检测               |
| week      | varchar | 执行的星期             |
| year      | varchar | 年份检测               |
| is_enable | tinyint | 是否使用 1使用 0不启用 |
| des       | varchar | 描述                   |

 

**uds_job_date_trigger**

| id         | bigint   | 唯一标识                                                     |
| ---------- | -------- | ------------------------------------------------------------ |
| platform   | varchar  | 平台                                                         |
| system     | varchar  | 系统                                                         |
| job        | varchar  | 作业                                                         |
| offset_day | tinyint  | 偏移天数;+1开始时间日期加1天,-1开始时间日期减1天             |
| start_time | datetime | 下次执行时间                                                 |
| end_time   | datetime | 停止时间                                                     |
| second     | varchar  | 秒    0-59  可使用:  " * "每秒触发 ;" , " 指定秒数触发如0,15,45;代表 0，15，45秒的时候触发 “-”区间触发 如25-45；在25秒到45秒，每隔一秒触发一次； “ / ”步进触发 如 */20 从0开始每隔20秒触发一次 |
| minute     | varchar  | 分钟 0-59     可使用: - * /       */3 每隔3分钟  5-50/3 在5到50分钟，每隔3分钟 |
| hour       | varchar  | 时  0-23 可使用: - * /        */3 每个3小时                  |
| day        | varchar  | 天数    1-31  可使用: - * /  ？ L W  C  L最后一天 W 工作日 1W 第一个工作日         /3 每隔3天 3号         ； ？与周互斥 |
| month      | varchar  | 月 1-12或者JAN-DEC  - * /       /3每隔 3个月 3月             |
| week       | varchar  | 周   1-7 或者 SUN-SAT   配置 ； ？与日配置互斥               |
| year       | varchar  | 年 1970-2099. -*/                                            |
| is_enable  | tinyint  | 是否启用；0不启用 1启用                                      |
| des        | varchar  | 描述                                                         |

 

**uds_job_dependency**

| platform     | varchar | 平台                         |
| ------------ | ------- | ---------------------------- |
| system       | varchar | 系统                         |
| job          | varchar | 作业                         |
| dep_platform | varchar | 依赖 平台                    |
| dep_system   | varchar | 依赖 系统                    |
| dep_job      | varchar | 依赖 作业                    |
| dep_batch    | int     | 单批次触发多批次，依赖批次号 |
| is_enable    | tinyint | 是否使用                     |
| des          | varchar | 描述                         |

 

**uds_job_record**

| platform        | varchar  | 平台                     |
| --------------- | -------- | ------------------------ |
| system          | varchar  | 应用                     |
| job             | varchar  | 作业名                   |
| job_type        | varchar  | 作业类型                 |
| last_status     | varchar  | 作业最后状态             |
| server_name     | varchar  | 节点名                   |
| pending_time    | datetime | 作业进入pending状态时间  |
| dispatcher_time | datetime | 进入dispatcher状态的时间 |
| start_time      | datetime | 开始时间                 |
| end_time        | datetime | 结束时间                 |
| job_date        | varchar  | 作业时间                 |
| virtual_enable  | tinyint  | 是否置虚                 |
| batch           | int      | 批次号                   |
| num_times       | bigint   | 执行次数                 |

 

 

**uds_job_source**

| src_signal  | varchar | 源信号文件     |
| ----------- | ------- | -------------- |
| conv_signal | varchar | 转化后信号文件 |
| platform    | varchar | 平台           |
| system      | varchar | 系统           |
| job         | varchar | 作业           |

 

**uds_error**

| id        | bigint   | 事件ID                                |
| --------- | -------- | ------------------------------------- |
| code      | int      | 错误码                                |
| msg_time  | datetime | 发生日期                              |
| msg_level | tinyint  | 重视程度：L-Low:1 M-Medium:2 H-High:3 |
| msg_par   | varchar  | 错误参数（发送message需要的参数）     |
| msg       | varchar  | 错误描述信息                          |
| dispose   | tinyint  | 是否处理 ：0没有处理;1处理            |

 

**uds_job_step**

| platform     | varchar | 平台名                       |
| ------------ | ------- | ---------------------------- |
| system       | varchar | 系统名                       |
| job          | varchar | 作业名                       |
| setp_num     | tinyint | 执行步骤                     |
| oper_cmd     | varchar | 执行命令                     |
| script_dir   | varchar | 脚本路径                     |
| script_name  | varchar | 脚本名                       |
| parameter    | varchar | 执行参数 空格 分割           |
| step_type    | varchar | 脚本类型(perl,python)        |
| environments | varchar | 环境变量 KEY=VALUE;KEY=VALUE |
| work_dir     | varchar | 工作路径                     |
| is_enable    | tinyint | 是否启用                     |
| des          | varchar | 备注信息                     |

 

**uds_job_step_record**

| id             | bigint   | 唯一标识 |
| -------------- | -------- | -------- |
| platform       | varchar  | 平台     |
| system         | varchar  | 系统     |
| job            | varchar  | 作业     |
| setp_num       | tinyint  | 脚本名   |
| return_code    | int      | 返回CODE |
| virtual_enable | tinyint  | 0        |
| num_times      | bigint   | 执行次数 |
| server_name    | varchar  | 节点名   |
| start_time     | datetime | 开始时间 |
| end_time       | datetime | 结束时间 |
| job_date       | varchar  | 作业日期 |
| script_name    | varchar  | 脚本名字 |
| log_path       | varchar  | 日志路径 |
| log_name       | varchar  | 日志名字 |

 

**uds_job_stream**

| platform     | varchar | 平台                         |
| ------------ | ------- | ---------------------------- |
| system       | varchar | 系统                         |
| job          | varchar | 作业                         |
| stm_platform | varchar | 触发_平台                    |
| stm_system   | varchar | 触发_系统                    |
| stm_job      | varchar | 触发_作业                    |
| stm_batch    | int     | 多批次触发单批次，触发批次号 |
| is_enable    | tinyint | 是否启用                     |
| des          | varchar | 描述                         |

 

**uds_job_tags**

| id        | bigint  | 唯一标识 |
| --------- | ------- | -------- |
| platform  | varchar | 平台     |
| system    | varchar | 应用     |
| job       | varchar | 作业     |
| tag       | varchar | 标签     |
| tag_type  | tinyint | 标签类型 |
| desc      | varchar | 描述     |
| is_enable | tinyint | 是否启用 |

 

 

**uds_job_weight**

| id         | bigint   | 唯一标识   |
| ---------- | -------- | ---------- |
| platform   | varchar  | 平台       |
| system     | varchar  | 应用       |
| job        | varchar  | 作业名     |
| limit_type | smallint | 资源类型   |
| conf_value | int      | 占用资源数 |
| desc       | varchar  | 描述       |

 

**uds_job_weight_limit**

| id          | bigint   | 唯一标识 |
| ----------- | -------- | -------- |
| limit_type  | smallint | 资源类型 |
| limit_value | int      | 资源上限 |
| desc        | varchar  | 描述     |

 

 

**uds_server**

| server_name       | varchar  | 服务器名字                                                   |
| ----------------- | -------- | ------------------------------------------------------------ |
| order             | smallint | 服务器启动顺序，当为主服务器的时候谁先来，按照优先级启动服务器 |
| ip                | varchar  | 服务器iP                                                     |
| port              | int      | 服务通讯端口                                                 |
| agent_port        | int      | telent后台连接端口                                           |
| http_port         | int      | http访问端口                                                 |
| server_type       | tinyint  | 0为子节点，1为主节点                                         |
| max_job_num       | smallint | 并发JOB数目，设置后需要后台load                              |
| performance_ratio | smallint | 性能比值，千分比 ，涉及性能要乘以此比值                      |
| last_start        | datetime | 上次启动时间                                                 |
| last_end          | datetime | 关闭时间                                                     |
| location          | int      | 位置标记                                                     |
| tags              | varchar  | 机器标签;分割                                                |
| is_enable         | tinyint  | 是否启用 需要加载                                            |
| des               | varchar  | 描述                                                         |

 

**uds_system**

| platform     | varchar  | 平台                                     |
| ------------ | -------- | ---------------------------------------- |
| system       | varchar  | 系统(*针对所有系统)                      |
| max_run_job  | smallint | 该应用的最大运行作业数                   |
| strategy     | smallint | 分发策略; 0:普通策略；1.指定机器分发机器 |
| strategy_pro | varchar  | 策略参数（1.机器信息）                   |
| use_platform | tinyint  | 1:使用平台数据；0:自己单独数据           |
| des          | varchar  | 描述                                     |

 