package com.student.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String role; // admin, teacher, student
    private Integer classId;
    private Date createdTime;
    private Date updatedTime;
    
    // 关联查询字段
    private String className;
}







