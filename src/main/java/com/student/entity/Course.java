package com.student.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Course {
    private Integer id;
    private String courseName;
    private String courseCode;
    private Integer teacherId;
    private Integer classId;
    private String semester;  // 学期（如：2024-2025-1）
    private String description;
    private Date createdTime;
    private Date updatedTime;
    
    // 关联查询字段
    private String teacherName;
    private String className;
}




