package com.student.mapper;

import com.student.entity.StudentCourse;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface StudentCourseMapper {
    List<StudentCourse> findAll();
    StudentCourse findById(Integer id);
    List<StudentCourse> findByStudentId(@Param("studentId") Integer studentId);
    List<StudentCourse> findByCourseId(@Param("courseId") Integer courseId);
    StudentCourse findByStudentIdAndCourseId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
    int insert(StudentCourse studentCourse);
    int deleteById(Integer id);
    int deleteByStudentIdAndCourseId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
}




