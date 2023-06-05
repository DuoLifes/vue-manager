# SpringSecurity-scaffolding

#### 介绍
基于springSecurity的权限认证脚手架 
相关博客地址：[https://blog.csdn.net/grd_java/article/details/121925792](https://blog.csdn.net/grd_java/article/details/121925792)

#### 软件架构
需要redis


#### 安装教程

1.  下载源码到idea
2.  设置JDK为1.8版本，11版本需要引入额外jar包
3.  执行sql语句，默认数据库名字为dd
4.  启动redis
5.  redis配置位置
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/190203_c57d4bef_7827212.png "屏幕截图.png")
6.  启动测试微服务service_animation
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/190354_908b56c4_7827212.png "屏幕截图.png")
7.  访问http://localhost:8001/swagger-ui.html进行测试（1. 获取验证码，2.通过admin，密码pwd登录获取token和token指定前缀Bearer，将其设置到Swagger请求头中）
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/190708_9e52c4b2_7827212.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/190744_2f85b775_7827212.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/190802_ae138515_7827212.png "屏幕截图.png")
8.   获取到前缀Bearer和token字符串后，以Bearer token字符串的格式，设置到请求头
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/190933_0e2d0826_7827212.png "屏幕截图.png")
9.   接下来即可开始测试需要认证的接口
![输入图片说明](https://images.gitee.com/uploads/images/2021/1217/191020_bda76bb8_7827212.png "屏幕截图.png")
