<div align="center">

# MyMall E-commerce System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.1.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MyBatis](https://img.shields.io/badge/MyBatis-2.1.0-blue.svg)](http://www.mybatis.org/mybatis-3/zh/index.html)
[![MySQL](https://img.shields.io/badge/MySQL-5.7+-orange.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-Latest-red.svg)](https://redis.io/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

An enterprise-level e-commerce backend learning project based on Spring Boot

English | [简体中文](./README.md)

</div>

---

## 📖 Introduction

MyMall is a learning project that simulates an enterprise-level e-commerce platform backend. The project uses Spring Boot framework for rapid development, MySQL as the primary database for storing core data such as products and orders, and leverages Redis for high-performance shopping cart functionality, while integrating RabbitMQ message queue for asynchronous task processing.

This project is suitable for students learning Java Web development and helps understand the basic architecture and development process of enterprise-level projects.

## ✨ Features

- 🔐 **User Management** - User registration, login, and profile management
- 🛍️ **Product Management** - Product browsing, categorization, and detail queries
- 🛒 **Shopping Cart** - High-performance shopping cart implementation based on Redis
- 📦 **Order System** - Order creation, querying, and status management
- 💳 **Payment Processing** - Payment information handling and callbacks
- 📊 **Pagination** - Data pagination using PageHelper
- 🔔 **Message Queue** - Asynchronous message processing with RabbitMQ

## 🛠️ Tech Stack

### Backend Framework
- **Spring Boot 2.1.7** - Core framework
- **Spring Web** - RESTful API development
- **MyBatis 2.1.0** - ORM framework
- **Spring Data Redis** - Redis integration
- **Spring AMQP** - RabbitMQ integration

### Database
- **MySQL 5.7+** - Primary database
- **Redis** - Cache and shopping cart storage

### Utilities
- **Lombok** - Java code simplification
- **PageHelper 1.2.13** - MyBatis pagination plugin
- **Gson** - JSON processing
- **MyBatis Generator** - Code generator

### Development Tools
- **Maven** - Project build management
- **JDK 1.8** - Java development environment

## 📁 Project Structure

```
MyMall
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.hust.mymall
│   │   │       ├── controller      # Controller layer
│   │   │       ├── service         # Business logic layer
│   │   │       ├── dao             # Data access layer
│   │   │       ├── pojo            # Entity classes
│   │   │       ├── vo              # View objects
│   │   │       ├── form            # Form objects
│   │   │       ├── enums           # Enumerations
│   │   │       ├── exception       # Exception handling
│   │   │       ├── consts          # Constants
│   │   │       ├── Interceptor     # Interceptors
│   │   │       └── listener        # Message listeners
│   │   └── resources
│   │       ├── application.yml     # Application configuration
│   │       └── mapper              # MyBatis mapping files
│   └── test                        # Test code
├── doc                             # Documentation directory
├── mall.sql                        # Database script
└── pom.xml                         # Maven configuration
```

## 🚀 Quick Start

### Prerequisites

- JDK 1.8 or higher
- Maven 3.x
- MySQL 5.7+
- Redis service
- RabbitMQ service (optional)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/lewiswlker/MyMall.git
   cd MyMall
   ```

2. **Set up the database**
   ```bash
   # Login to MySQL
   mysql -u root -p
   
   # Create database
   CREATE DATABASE mall;
   
   # Import database script
   USE mall;
   SOURCE mall.sql;
   ```

3. **Configure the application**
   
   Modify the `src/main/resources/application.yml` file to configure database and Redis connection:
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

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
   Or run the generated jar file:
   ```bash
   java -jar target/mall.jar
   ```

6. **Access the application**
   
   The application runs on `http://localhost:8080` by default

## 📝 Configuration

The main configuration file is located at `src/main/resources/application.yml`, and you need to configure:

- **Database Configuration** - MySQL connection information
- **Redis Configuration** - Redis service address and port
- **RabbitMQ Configuration** - Message queue configuration (if needed)
- **MyBatis Configuration** - SQL mapping file paths

**Note:** The database structure may have been updated during development, and the `mall.sql` file may not be fully synchronized with the latest changes. Please adjust according to actual runtime conditions.

## 📚 API Documentation

For detailed API documentation and development materials, please refer to the `doc` directory:

- [Development Documentation](./doc/README.md)
- [API Documentation](./doc/api/)
- [Reference Materials](./doc/资料文档.md)
- [Software Downloads](./doc/软件下载.md)

## 🤝 Contributing

Contributions are welcome! Feel free to submit Issues and Pull Requests.

1. Fork this project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Submit a Pull Request

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details

## 📧 Contact

If you have any questions or suggestions, please feel free to contact us through GitHub Issues.

---

<div align="center">

**⭐ If this project helps you, please give it a star!**

Made with ❤️ by [lewiswlker](https://github.com/lewiswlker)

</div>
