package com.student.service;

import com.student.entity.StudentCourse;
import java.util.List;

public interface StudentCourseService {
    List<StudentCourse> findAll();
    StudentCourse findById(Integer id);
    List<StudentCourse> findByStudentId(Integer studentId);
    List<StudentCourse> findByCourseId(Integer courseId);
    StudentCourse findByStudentIdAndCourseId(Integer studentId, Integer courseId);
    boolean save(StudentCourse studentCourse);
    boolean delete(Integer id);
    boolean deleteByStudentIdAndCourseId(Integer studentId, Integer courseId);
}




