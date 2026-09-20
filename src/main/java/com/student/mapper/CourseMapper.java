package com.student.mapper;

import com.student.entity.Course;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CourseMapper {
    List<Course> findAll();
    Course findById(Integer id);
    List<Course> findByTeacherId(@Param("teacherId") Integer teacherId);
    List<Course> findByClassId(@Param("classId") Integer classId);
    List<Course> findByStudentId(@Param("studentId") Integer studentId);
    int insert(Course course);
    int update(Course course);
    int deleteById(Integer id);
}







