package com.student.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Score {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private BigDecimal score;
    private String remark;
    private Date createdTime;
    private Date updatedTime;
    
    // 关联查询字段
    private String studentName;
    private String courseName;
    private String className;
}







