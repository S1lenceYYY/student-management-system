package com.student.entity;

import lombok.Data;
import java.util.Date;

@Data
public class StudentCourse {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Date createdTime;
    
    // 关联查询字段
    private String studentName;
    private String courseName;
    private String className;
    private String semester;
}




