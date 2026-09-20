# 学生成绩管理系统

基于 Spring Boot + MyBatis + MySQL 的学生成绩管理系统，支持管理员、教师、学生三种角色。

## 技术栈

- **后端**: JDK 1.8, Spring Boot 2.7.18, Spring MVC, MyBatis, MySQL 8.0
- **前端**: HTML, CSS, jQuery, Bootstrap 4.6.2
- **构建工具**: Maven

## 功能特性

### 管理员功能
- 班级管理：增删改查
- 课程管理：增删改查
- 学生管理：增删改查
- 成绩管理：增删改查（分数校验 0-100 分）

### 教师功能
- 查询自身授课课程
- 对对应学生成绩进行增删改查操作（分数校验 0-100 分）

### 学生功能
- 查询个人所属班级的课程
- 查询自身对应成绩

## 数据库设计

系统包含四张核心表：
1. **user** - 用户表（通过 role 字段区分角色：admin、teacher、student）
2. **class** - 班级表
3. **course** - 课程表
4. **score** - 成绩表

## 快速开始

### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- IntelliJ IDEA

### 2. 数据库配置

1. 创建数据库并执行 SQL 脚本：
```bash
mysql -u root -p < src/main/resources/db/schema.sql
```

2. 修改数据库连接配置（`src/main/resources/application.yml`）：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_management?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: root  # 修改为你的数据库密码
```

### 3. 运行项目

1. 在 IntelliJ IDEA 中导入项目
2. 等待 Maven 依赖下载完成
3. 运行 `StudentManagementApplication` 主类
4. 访问：http://localhost:8080/student-system/login.html

### 4. 默认账号

- **管理员**: admin / admin123
- **教师**: teacher1 / teacher123
- **学生**: student1 / student123

## 项目结构

```
src/main/java/com/student/
├── StudentManagementApplication.java    # Spring Boot 主类
├── common/                              # 通用类
│   ├── Result.java                     # 统一响应结果
│   └── SessionUtil.java                # Session 工具类
├── config/                              # 配置类
│   └── WebConfig.java                  # Web 配置（拦截器）
├── controller/                          # 控制器层
│   ├── LoginController.java            # 登录控制器
│   ├── AdminController.java            # 管理员控制器
│   ├── TeacherController.java          # 教师控制器
│   └── StudentController.java          # 学生控制器
├── entity/                              # 实体类
│   ├── User.java
│   ├── Class.java
│   ├── Course.java
│   └── Score.java
├── interceptor/                         # 拦截器
│   └── LoginInterceptor.java          # 登录拦截器
├── mapper/                              # Mapper 接口
│   ├── UserMapper.java
│   ├── ClassMapper.java
│   ├── CourseMapper.java
│   └── ScoreMapper.java
└── service/                             # 服务层
    ├── impl/                            # 服务实现
    └── [各Service接口和实现]

src/main/resources/
├── application.yml                      # 应用配置
├── db/
│   └── schema.sql                      # 数据库脚本
├── mapper/                              # MyBatis XML 映射文件
├── static/                              # 静态资源
│   └── js/                              # JavaScript 文件
└── templates/                           # 前端页面
    ├── login.html                      # 登录页
    ├── admin/
    │   └── index.html                  # 管理员首页
    ├── teacher/
    │   └── index.html                  # 教师首页
    └── student/
        └── index.html                  # 学生首页
```

## 权限说明

- 系统通过 Session 进行登录状态校验
- 登录拦截器会拦截所有请求（除登录相关页面）
- 各角色只能访问对应权限的功能
- 教师只能操作自己授课课程的成绩
- 学生只能查看自己的课程和成绩

## 注意事项

1. 确保 MySQL 服务已启动
2. 确保数据库连接配置正确
3. 首次运行前需要执行数据库初始化脚本
4. 密码采用明文存储（生产环境建议加密）

## 开发规范

- 后端采用分层架构：Controller -> Service -> Mapper
- 前端按角色划分页面目录
- 统一使用 Result 类返回响应结果
- 使用 Session 管理用户登录状态

## 故障排查

### 数据库连接问题

如果应用启动时出现以下提示：
```
Consider the following:
If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.
If you have database settings to be loaded from a particular profile you may need to activate it (no profiles are currently active).
```

**可能的原因和解决方案：**

1. **MySQL 服务未启动**
   - Windows: 检查服务管理器中 MySQL 服务是否运行
   - 命令行: `net start MySQL` (Windows) 或 `sudo systemctl start mysql` (Linux)

2. **数据库未创建**
   - 执行数据库初始化脚本：
   ```bash
   mysql -u root -p < src/main/resources/db/schema.sql
   ```
   - 或者在 MySQL 客户端中手动执行 `schema.sql` 文件

3. **数据库配置错误**
   - 检查 `application.yml` 中的配置：
     - `url`: 确认数据库名称是 `student_management`
     - `username`: 确认用户名正确（默认 root）
     - `password`: 确认密码正确（你已设置为 123456）

4. **端口冲突**
   - 确认 MySQL 默认端口 3306 未被占用
   - 如果 MySQL 使用其他端口，修改 `application.yml` 中的 URL

5. **防火墙或网络问题**
   - 确认防火墙允许访问 MySQL 端口
   - 如果是远程数据库，确认网络连接正常

### 验证数据库连接

应用启动时会自动测试数据库连接，如果连接成功，控制台会显示：
```
========================================
数据库连接成功！
用户表记录数: X
========================================
```

如果连接失败，会显示详细的错误信息和排查建议。

### 手动测试数据库连接

可以使用以下 SQL 命令测试数据库：

```sql
-- 连接到 MySQL
mysql -u root -p

-- 查看数据库
SHOW DATABASES;

-- 使用数据库
USE student_management;

-- 查看表
SHOW TABLES;

-- 查看用户表数据
SELECT * FROM user;
```

### 常见错误

1. **Access denied for user 'root'@'localhost'**
   - 密码错误，检查 `application.yml` 中的密码配置

2. **Unknown database 'student_management'**
   - 数据库未创建，执行 `schema.sql` 脚本

3. **Table 'xxx' doesn't exist**
   - 表未创建，检查是否完整执行了 `schema.sql` 脚本

4. **Communications link failure**
   - MySQL 服务未启动或网络问题

