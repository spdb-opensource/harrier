![HARRIER](https://user-images.githubusercontent.com/38176315/133361205-b2d987de-11e1-40fa-a848-cbc1bf1889cd.jpg)
![](https://img.shields.io/badge/language-java-blue.svg)
![](https://img.shields.io/badge/release-v2.3-green.svg)
![](https://img.shields.io/tokei/lines/github/spdb-opensource/harrier?color=orange)
![](https://img.shields.io/github/license/spdb-opensource/harrier?color=purple)
<a href="https://app.circleci.com/pipelines/github/spdb-opensource/harrier"><img src="https://img.shields.io/circleci/project/github/spdb-opensource/harrier?sanitize=true" alt="Build Status"></a>


------

​
### 项目简介

2020年浦发银行大数据中心创立Data Ocean数据品牌体系，旨在面向金融行业各类业务主题和数据应用场景，为新业态下的金融业务发展和金融数据生态提供丰富的数据服务与解决方案。目前整个Data Ocean品牌下涵盖各类基础数据平台、金融数据服务产品、数据治理服务产品，形成了从基础数据平台建设到各类业务场景赋能的全套解决方案和实施方法论。

Harrier海鹞为Data Ocean品牌体系下的调度工具类产品，由Java开发，支持Linux,Windows,AIX环境部署，支持各类异构数据计算平台的作业调度（Hive,Spark,Teradata,Oracle,DB2,DataStage等），支持各类自定义任务作业调度（Java,Shell,Python,Perl等）

2022年8月，Harrier 迎来发版以来的最大更新，Harrier 3.0 正式发布，此次版本更新，为Harrier带来大量新功能、新特性，为用户带来更好的使用体验。

------

### 项目特点

- 以DAG图的方式将Task按照任务的依赖关系关联起来，可实时可视化监控任务的运行状态。

- 支持丰富的任务类型：Shell、JAVA、SQL，Procedure等。

- 支持工作流定时调度、依赖调度、手动调度、手动暂停/停止/恢复，同时支持失败重试/告警、从指定节点恢复失败、Kill任务等操作。

- 支持工作流优先级、任务优先级及任务超时告警/失败。

- 支持工作流全局参数及节点自定义参数设置。

- 支持资源文件的在线上传/下载，管理等。

- 支持任务日志在线查看、在线下载日志等。

- 实现集群HA，实现Master集群和Worker集群去中心化。

- 支持对Master/Worker cpu load，memory，cpu在线查看。

- 支持补数。

- 支持多租户。
- 支持准实时模式下的微批场景。

------

### 3.0版本新特性

- 全新UI界面

  前端代码方面，系统更健壮，响应速度更快，稳定性也更强。
  视觉效果上，对比旧版 UI，新 UI 趋于扁平化设计，更加清晰美观。

- 作业触发关系去除

  作业流去除触发关系，依赖满足自动触发，降低作业流配置复杂度，提升作业流调度处理效率。

- 集群资源实时监控

  首页可实时监控集群资源使用情况，及时发现节点异常情况。

- 支持多租户模式

  集群支持多租户，多个租户共享同一个数据库，每个租户拥有独立的命名空间，可以简便的满足不同租户的特定需求，灵活性高。
  集群为每个租户分配一个id，并把每个租户的所有操作限制在为其分配的命名空间之中，在数据逻辑上实现相互隔离，以保障租户间数据安全。

- 作业生命周期管理功能

  3.0版本监控系统新增作业生命周期管理及作业版本控制功能，优化用户在小规模作业场景下的使用体验。

- 作业执行相关

  支持作业种类更加丰富，支持多样化作业调用方式。

- 存储方案新增

  脚本及日志的存储支持更加丰富的存储方案，S3对象存储、宿主机本地存储，避免对于NAS的单一依赖。

- 后端服务拆分

  3.0版本后端以微服务形式进行重构，提升服务高可用性，同时降低项目开发难度，提升项目更新效率。

------

### 近期研发计划

- 新UI全面开发

  Harrier监控页面完成重设计优化。

- 3.0版本监控系统将支持无代码式作业开发,适用于小规模作业开发场景。

  3.0版本监控系统将支持无代码式作业开发,适用于小规模作业开发场景。

- 新增对企业微信接口的支持。

  目前已有邮件发送接口，支持SMTP协议邮件服务器，后续版本会开发微信发送接口，以保障告警信息多渠道、实时触达。

- 新增集群文件管理功能

  通过监控页面实现统一管理所有节点文件。

------

### 参与贡献

欢迎大家参与贡献，共建Harrier开源社区，贡献流程请参考：参与贡献

------

### 部署使用

Harrier支持单机部署以及集群部署,详见 Harrier 3.0 部署使用说明书

------

### 快速试用 Docker

请参考官方文档：快速试用Docker部署



------

### 许可证

[Apache License 2.0](./LICENSE)

------

### 联系反馈

luzl2@spdb.com.cn;zhangsh12@spdb.com.cn;

##### 

