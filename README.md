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
|  KV管理 |   |   |
|   |   |   |
|   |   |   |


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

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
