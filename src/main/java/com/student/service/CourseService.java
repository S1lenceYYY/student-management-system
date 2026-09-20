package com.student.service;

import com.student.entity.Course;
import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findById(Integer id);
    List<Course> findByTeacherId(Integer teacherId);
    List<Course> findByClassId(Integer classId);
    List<Course> findByStudentId(Integer studentId);
    boolean save(Course course);
    boolean update(Course course);
    boolean delete(Integer id);
}







