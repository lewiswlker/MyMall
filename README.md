<div align="center">

# MyMall 电商系统

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.1.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MyBatis](https://img.shields.io/badge/MyBatis-2.1.0-blue.svg)](http://www.mybatis.org/mybatis-3/zh/index.html)
[![MySQL](https://img.shields.io/badge/MySQL-5.7+-orange.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-Latest-red.svg)](https://redis.io/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

一个基于 Spring Boot 的企业级电商后端学习项目

[English](./README_EN.md) | 简体中文

</div>

---

## 📖 项目简介

MyMall 是一个模拟企业级电商平台的后端开发学习项目。该项目采用 Spring Boot 框架进行快速开发，使用 MySQL 作为主数据库存储商品、订单等核心数据，并利用 Redis 实现高性能的购物车功能，同时集成了 RabbitMQ 消息队列处理异步任务。

本项目适合正在学习 Java Web 开发的同学，可以帮助理解企业级项目的基本架构和开发流程。

## ✨ 主要特性

- 🔐 **用户管理** - 用户注册、登录、信息管理
- 🛍️ **商品管理** - 商品浏览、分类、详情查询
- 🛒 **购物车** - 基于 Redis 的高性能购物车实现
- 📦 **订单系统** - 订单创建、查询、状态管理
- 💳 **支付功能** - 支付信息处理和回调
- 📊 **分页查询** - 使用 PageHelper 实现数据分页
- 🔔 **消息队列** - RabbitMQ 实现异步消息处理

## 🛠️ 技术栈

### 后端框架
- **Spring Boot 2.1.7** - 核心框架
- **Spring Web** - RESTful API 开发
- **MyBatis 2.1.0** - ORM 框架
- **Spring Data Redis** - Redis 集成
- **Spring AMQP** - RabbitMQ 集成

### 数据库
- **MySQL 5.7+** - 主数据库
- **Redis** - 缓存与购物车存储

### 工具库
- **Lombok** - 简化 Java 代码
- **PageHelper 1.2.13** - MyBatis 分页插件
- **Gson** - JSON 处理
- **MyBatis Generator** - 代码生成器

### 开发工具
- **Maven** - 项目构建管理
- **JDK 1.8** - Java 开发环境

## 📁 项目结构

```
MyMall
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.hust.mymall
│   │   │       ├── controller      # 控制器层
│   │   │       ├── service         # 业务逻辑层
│   │   │       ├── dao             # 数据访问层
│   │   │       ├── pojo            # 实体类
│   │   │       ├── vo              # 视图对象
│   │   │       ├── form            # 表单对象
│   │   │       ├── enums           # 枚举类
│   │   │       ├── exception       # 异常处理
│   │   │       ├── consts          # 常量定义
│   │   │       ├── Interceptor     # 拦截器
│   │   │       └── listener        # 消息监听器
│   │   └── resources
│   │       ├── application.yml     # 应用配置
│   │       └── mapper              # MyBatis 映射文件
│   └── test                        # 测试代码
├── doc                             # 文档目录
├── mall.sql                        # 数据库脚本
└── pom.xml                         # Maven 配置
```

## 🚀 快速开始

### 环境要求

- JDK 1.8 或更高版本
- Maven 3.x
- MySQL 5.7+
- Redis 服务
- RabbitMQ 服务（可选）

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/lewiswlker/MyMall.git
   cd MyMall
   ```

2. **创建数据库**
   ```bash
   # 登录 MySQL
   mysql -u root -p
   
   # 创建数据库
   CREATE DATABASE mall;
   
   # 导入数据库脚本
   USE mall;
   SOURCE mall.sql;
   ```

3. **配置应用**
   
   修改 `src/main/resources/application.yml` 文件，配置数据库和 Redis 连接信息：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/mall?useUnicode=true&characterEncoding=utf-8
       username: your_username
       password: your_password
     redis:
       host: localhost
       port: 6379
   ```

4. **构建项目**
   ```bash
   mvn clean install
   ```

5. **运行应用**
   ```bash
   mvn spring-boot:run
   ```
   
   或者运行生成的 jar 包：
   ```bash
   java -jar target/mall.jar
   ```

6. **访问应用**
   
   应用默认运行在 `http://localhost:8080`

## 📝 配置说明

主要配置文件位于 `src/main/resources/application.yml`，需要配置以下内容：

- **数据库配置** - MySQL 连接信息
- **Redis 配置** - Redis 服务地址和端口
- **RabbitMQ 配置** - 消息队列配置（如需使用）
- **MyBatis 配置** - SQL 映射文件路径

**注意：** 开发过程中数据库结构可能有所更新，`mall.sql` 文件可能未完全同步最新改动，建议根据实际运行情况调整。

## 📚 API 文档

具体的 API 接口文档和开发资料请参考 `doc` 目录：

- [开发文档](./doc/README.md)
- [API 文档](./doc/api/)
- [资料文档](./doc/资料文档.md)
- [软件下载](./doc/软件下载.md)

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request 来改进项目！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 详见 [LICENSE](LICENSE) 文件

## 📧 联系方式

如有问题或建议，欢迎通过 GitHub Issues 与我们联系。

---

<div align="center">

**⭐ 如果这个项目对你有帮助，请给个星标支持一下！**

Made with ❤️ by [lewiswlker](https://github.com/lewiswlker)

</div>
