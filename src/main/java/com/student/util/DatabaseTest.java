package com.student.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 数据库连接测试工具
 * 应用启动时自动测试数据库连接
 */
@Component
@Order(1)
public class DatabaseTest implements CommandLineRunner {
    
    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... args) {
        if (jdbcTemplate != null) {
            try {
                // 测试数据库连接
                Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
                System.out.println("========================================");
                System.out.println("数据库连接成功！");
                System.out.println("用户表记录数: " + count);
                System.out.println("========================================");
            } catch (Exception e) {
                System.err.println("========================================");
                System.err.println("数据库连接失败！");
                System.err.println("错误信息: " + e.getMessage());
                System.err.println("请检查：");
                System.err.println("1. MySQL 服务是否已启动");
                System.err.println("2. 数据库 'student_management' 是否已创建");
                System.err.println("3. application.yml 中的数据库配置是否正确");
                System.err.println("4. 是否已执行 schema.sql 初始化脚本");
                System.err.println("========================================");
            }
        } else {
            System.err.println("========================================");
            System.err.println("警告: 无法获取数据库连接，请检查配置");
            System.err.println("========================================");
        }
    }
}







