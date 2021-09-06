# oneManage-Cloud 后台管理系统

####  :tw-1f4cb: 项目介绍
oneManage微服务版本，基于SpringCloud+vue，是一套简单通用前后端分离的后台管理框架，可以作为SpringCloud和vue的入门学习框架，亦可以作为一个基础脚手架进行二次开发。

####  :tw-1f4fa: 系统演示

开发文档：http://www.oneyuanma.com

演示地址：http://www.oneyuanma.com

账号密码： admin / admin


####  :tw-26ea: 系统架构

![RUNOOB 图标](https://bj.bcebos.com/v1/oym-img/oneManage/oneManage架构图.jpg "oneManage-Cloud总体架构图")

####  :tw-1f4da: 内置模块

|  功能名 |  简介 | 完成进度  |
|---|---|---|
|  用户管理 |  内部人员的管理和维护 |  :tw-2705:   |
|  角色管理 |  用于对系统角色进行维护，角色权限分配等 |  :tw-2705: |
|  资源管理 |  对系统菜单，按钮，权限进行管理 |  :tw-2705: |
|  KV管理 |  键值对管理，相当于提供给用户的配置管理 |  :tw-2705: |
|  登录日志 |  详细的记录系统日常登录、登出日志数据 | :tw-2705:  |
|  操作日志 |  记录用户的操作行为，记录了参数，以便数据补偿 | :tw-2705:  |
|  任务管理 |  定时任务调用的增删改查和预执行 | :tw-2705:  |
|  字典管理|  系统中常规的常量进行管理和维护 | :tw-1f6a7:  |
|  代码生成 |  简单配置快速生成后台和前端代码，免去重复工作 | :tw-2705:  |
|  流程审批|  流程审批管理，OA必备 | :tw-1f6a7: |
|  服务监控 |  监控服务的健康，运行等状态 | :tw-2705:  |
|  注册中心 |  consul注册中心，方便查看服务的注册情况 | :tw-2705:  |
|  kafka监控 |  监控kafka状态，使用情况等 |  :tw-1f6a7:   |
|  redis监控 |  监控redis状态，使用情况等  | :tw-1f6a7:  |
|  rocketMQ监控 |  监控rocketMQ状态，使用情况等 | :tw-1f6a7:  |
|  连接池监控|  监控数据库连接池状态，优化SQL | :tw-1f6a7:  |


####  :tw-1f4a0: 项目结构


```
- oneManage-gateway   网关模块
- oneManage-auth      登录权限认证模块
- oneManage-apis      微服务调用api接口模块
- oneManage-modules   业务模块
    - oneManage-generate    代码生成模块
    - oneManage-log         系统日志模块
    - oneManage-quratz      定时任务模块
    - oneManage-system      系统内置功能模块
- oneManage-monitor    系统监控模块
- oneManage-starter    系统启动模块
    - commons-spring-boot-starter       通用项目启动模块
    - db-spring-boot-starter            db项目启动模块
    - es-spring-boot-starter            es项目启动模块
    - kafka-spring-boot-starter         kafka项目启动模块
    - microservice-spring-boot-starter  微服务项目启动模块
    - redis-spring-boot-starter         redis项目启动模块

```



####  :tw-1f4f7: 系统截图


