-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE student_management;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `role` VARCHAR(20) NOT NULL COMMENT '角色：admin-管理员, teacher-教师, student-学生',
    `class_id` INT COMMENT '班级ID（学生和教师关联）',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_role` (`role`),
    INDEX `idx_class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 班级表
CREATE TABLE IF NOT EXISTS `class` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '班级ID',
    `class_name` VARCHAR(50) NOT NULL COMMENT '班级名称',
    `class_code` VARCHAR(20) NOT NULL UNIQUE COMMENT '班级编号',
    `description` VARCHAR(200) COMMENT '班级描述',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 课程表（增加学期字段）
CREATE TABLE IF NOT EXISTS `course` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '课程ID',
    `course_name` VARCHAR(50) NOT NULL COMMENT '课程名称',
    `course_code` VARCHAR(20) NOT NULL COMMENT '课程编号',
    `teacher_id` INT NOT NULL COMMENT '授课教师ID',
    `class_id` INT NOT NULL COMMENT '所属班级ID',
    `semester` VARCHAR(20) NOT NULL COMMENT '学期（如：2024-2025-1）',
    `description` VARCHAR(200) COMMENT '课程描述',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_teacher_id` (`teacher_id`),
    INDEX `idx_class_id` (`class_id`),
    INDEX `idx_semester` (`semester`),
    UNIQUE KEY `uk_course_code_semester` (`course_code`, `semester`, `teacher_id`, `class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 学生选课表
CREATE TABLE IF NOT EXISTS `student_course` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '选课ID',
    `student_id` INT NOT NULL COMMENT '学生ID',
    `course_id` INT NOT NULL COMMENT '课程ID',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
    UNIQUE KEY `uk_student_course` (`student_id`, `course_id`),
    INDEX `idx_student_id` (`student_id`),
    INDEX `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生选课表';

-- 成绩表
CREATE TABLE IF NOT EXISTS `score` (
    `id` INT PRIMARY KEY AUTO_INCREMENT COMMENT '成绩ID',
    `student_id` INT NOT NULL COMMENT '学生ID',
    `course_id` INT NOT NULL COMMENT '课程ID',
    `score` DECIMAL(5,1) NOT NULL COMMENT '成绩（0-100）',
    `remark` VARCHAR(200) COMMENT '备注',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_student_course` (`student_id`, `course_id`),
    INDEX `idx_student_id` (`student_id`),
    INDEX `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成绩表';

-- 添加外键约束
ALTER TABLE `user` ADD CONSTRAINT `fk_user_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE SET NULL;
ALTER TABLE `course` ADD CONSTRAINT `fk_course_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
ALTER TABLE `course` ADD CONSTRAINT `fk_course_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE CASCADE;
ALTER TABLE `student_course` ADD CONSTRAINT `fk_student_course_student` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
ALTER TABLE `student_course` ADD CONSTRAINT `fk_student_course_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE;
ALTER TABLE `score` ADD CONSTRAINT `fk_score_student` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
ALTER TABLE `score` ADD CONSTRAINT `fk_score_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE;

-- 插入初始数据
-- 默认管理员账号：admin/admin123
INSERT INTO `user` (`username`, `password`, `name`, `role`) VALUES 
('admin', 'admin123', '系统管理员', 'admin');

-- 插入示例班级
INSERT INTO `class` (`class_name`, `class_code`, `description`) VALUES 
('计算机科学与技术1班', 'CS001', '计算机专业1班'),
('软件工程1班', 'SE001', '软件工程专业1班'),
('计算机科学与技术2班', 'CS002', '计算机专业2班');

-- 插入示例教师（密码：teacher123）
INSERT INTO `user` (`username`, `password`, `name`, `role`, `class_id`) VALUES 
('teacher1', 'teacher123', '张老师', 'teacher', 1),
('teacher2', 'teacher123', '李老师', 'teacher', 2);

-- 插入示例学生（密码：student123）
INSERT INTO `user` (`username`, `password`, `name`, `role`, `class_id`) VALUES 
('student1', 'student123', '张三', 'student', 1),
('student2', 'student123', '李四', 'student', 1),
('student3', 'student123', '王五', 'student', 2),
('student4', 'student123', '赵六', 'student', 3);

-- 插入示例课程（带学期）
INSERT INTO `course` (`course_name`, `course_code`, `teacher_id`, `class_id`, `semester`, `description`) VALUES 
('Java程序设计', 'JAVA001', 2, 1, '2024-2025-1', 'Java基础编程课程'),
('Java程序设计', 'JAVA001', 2, 3, '2024-2025-1', 'Java基础编程课程（2班）'),
('数据库原理', 'DB001', 2, 1, '2024-2025-1', '数据库设计与应用'),
('Web开发技术', 'WEB001', 3, 2, '2024-2025-1', '前端与后端开发技术'),
('Java程序设计', 'JAVA001', 2, 1, '2024-2025-2', 'Java基础编程课程（第二学期）');

-- 插入示例选课记录
INSERT INTO `student_course` (`student_id`, `course_id`) VALUES 
(4, 1),  -- 张三选Java程序设计
(4, 3),  -- 张三选数据库原理
(5, 1),  -- 李四选Java程序设计
(5, 3),  -- 李四选数据库原理
(6, 4),  -- 王五选Web开发技术
(7, 2);  -- 赵六选Java程序设计（2班）

-- 插入示例成绩（只能录入已选课学生的成绩）
INSERT INTO `score` (`student_id`, `course_id`, `score`, `remark`) VALUES 
(4, 1, 85.5, '表现良好'),
(4, 3, 90.0, '优秀'),
(5, 1, 78.0, '继续努力'),
(6, 4, 92.5, '非常优秀');
