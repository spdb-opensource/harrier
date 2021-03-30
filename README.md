​		

### 项目简介

2020年浦发银行大数据中心创立Data Ocean数据品牌体系，旨在面向金融行业各类业务主题和数据应用场景，为新业态下的金融业务发展和金融数据生态提供丰富的数据服务与解决方案。目前整个Data Ocean品牌下涵盖各类基础数据平台、金融数据服务产品、数据治理服务产品，形成了从基础数据平台建设到各类业务场景赋能的全套解决方案和实施方法论。

Harrier海鹞为Data Ocean品牌体系下的调度工具类产品，由Java开发，支持Linux,Windows,AIX环境部署，支持各类异构数据计算平台的作业调度（Hive,Spark,Teradata,Oracle,DB2,DataStage等），支持各类自定义任务作业调度（Java,Shell,Python,Perl等）。

------

### 项目特点

1.Harrier简化了作业流管理概念,不再以DAG模式处理任务流。

2.Harrier抽象出了计算平台、应用的概念，实现了单一调度系统覆盖全企业视图的数据处理平台，并支持丰富的权限管理功能，任务管理和监控功能。

3.Harrier支持每日多批次的微批模式，满足企业准实时，高时效作业流的配置要求。

#### **高可用**

​        Harrier作为基础技术支撑平台，具备大的高可用和容灾能力。整个调度体系中，监控集群，调度主节点集群，子节点集群和数据库集群高可用能力。极端情况下，可容忍任意一个模块任意一半节点的宕机。

#### **高并发**

​        单主节点具备上万信号文件的同时到达和作业分发能力，6个子节点具备最大1000+的作业同时并行执行的能力。同时可根据作业量级轻松扩容，轻松应对百万级作业调度任务。

#### **多场景**

​        支持多种场景的作业调度，如日作业、周作业、旬作业、月作业等场景。全面覆盖各计算平台的批量处理场景。

------

### 场景作业说明

#### 定时任务模式

uds_job_date_trigger

|    id | platform | system | job            | offset_day | start_time          | end_time            | second | minute | hour | day  | month | week | year | is_enable | des  |
| ----: | -------- | ------ | -------------- | ---------: | ------------------- | ------------------- | ------ | ------ | ---- | ---- | ----- | ---- | ---- | --------: | ---- |
| 90315 | UDS      | SYS    | UDS_SYS_TEST_C |          0 | 2021-03-24 16:00:00 | 2099-01-01 00:00:01 | 0      | 0      | 16   | *    | *     | ?    | *    |         1 |      |

UDS_SYS_TEST_C 将在每天下午四点被定时触发，进入pending状态等待分发执行。

Cron表达式
Cron的表达式为：秒 分 时 日 月 周 [年]

为了帮助您理解，下面介绍一些常用的Cron表达式示例。

| Contab表达式       | 说明                                                    |
| :----------------- | ------------------------------------------------------- |
| 0 */1 * * * ?      | 每隔1分钟触发一次                                       |
| 0 0 5-15 * * ?     | 每天5:00~15:00整点触发                                  |
| 0 0/3 * * * ?      | 每隔3分钟触发一次                                       |
| 0 0/5 14 * * ?     | 每天14:00~14:55期间每隔5分钟触发一次                    |
| 0 0/5 14,18 * * ?  | 每天14:00到14:55 和 18:00到18:55两个时间段内每5分钟触发一次 |
| 0 0 10,14,16 * * ? | 每天10:00、14:00和16:00触发                             |
| 0 0 23 10,20 * ?   | 每月10号及20号的23:00触发                               |
| 0 15 10 ? * 6#3    | 每月的第三个周五10:15触发                               |
| 0 15 10 * * ? 2005 | 2005年的每天10:15触发                                   |

harrier所使用时间表达式相比Contab表达式以下改进：

1、Contab中 1 代表周日，而在harrier中 1 代表周一。

因此 “每月的第三个周五10:15触发” 在harrier中应配置为 0 15 10 ? * 5#3

2、支持月末 L 和分割符共用。 

因此 “每月10号 20号 月末的23:00触发” 在harrier中可配置为0 0 23 10,20,L * ?

而Contab并不支持以上用法。

#### 外部信号触发

外部系统向调度服务器指定路径发送信号文件后，触发对应作业。

信号文件，如dir.0@UDS_SYS_TEST_D20210101，由三部分组成，固定前缀（dir.0@）
作业名（UDS_SYS_TEST_D）以及作业日期（yyyymmdd）

#### 作业间依赖配置

uds_job_dependency

| platform | system | job             | dep_platform | dep_system | dep_job        | dep_batch | is_enable | des  |
| -------- | ------ | --------------- | ------------ | ---------- | -------------- | --------: | --------: | ---- |
| UDS      | SYS    | UDS_SYS_TEST_D1 | UDS          | SYS        | UDS_SYS_TEST_D |         0 |         1 | \N   |

UDS_SYS_TEST_D1依赖UDS_SYS_TEST_D

只有当UDS_SYS_TEST_D处于Done状态且其下次执行的job_date-1 >= UDS_SYS_TEST_D1当前的job_date，UDS_SYS_TEST_D1才可由pending状态转变为dispatcher状态。

dep_batch用于单批次作业依赖多批次作业的场景，dep_batch字段值表示依赖该多批次作业的第几批次。

#### 作业相互触发

uds_job_stream

| platform | system | job            | stm_platform | stm_system | stm_job         | stm_batch | is_enable | des  |
| -------- | ------ | -------------- | ------------ | ---------- | --------------- | --------: | --------: | ---- |
| UDS      | SYS    | UDS_SYS_TEST_D | UDS          | SYS        | UDS_SYS_TEST_D1 |         0 |         1 | \N   |

UDS_SYS_TEST_D执行成功后触发UDS_SYS_TEST_D1进入pending状态等待分发执行。

stm_batch用于单批次作业触发多批次作业的场景，stm_batch字段值表示触发该多批次作业的第几批次。

#### 应用并发控制

uds_system

| platform | system | max_run_job | strategy | strategy_pro | use_platform | des  |
| -------- | ------ | ----------: | -------: | ------------ | -----------: | ---- |
| UDS      | *      |         100 |        0 |              |            1 |      |
| UDS      | SYS    |           0 |        0 |              |            1 |      |

应用SYS的uds_platform字段值为1，即SYS使用平台并发控制，最大运行作业数为100

### 部署安装

harrier调度系统支持单机部署以及（一主多子）集群部署,详见[harrier部署文档](./deployment.md)

### 快速入门

请参考官方文档:[harrier部署文档](./deployment.md)

### 许可证

[Apache License 2.0](./LICENSE)

### 联系反馈
luzl2@sdpb.com.cn;zhangsh12@spdb.com.cn;liur25@spdb.com.cn;guon2@spdb.com.cn;zhoub11@spdb.com.cn;zhangzh12@spdb.com.cn


##### 

