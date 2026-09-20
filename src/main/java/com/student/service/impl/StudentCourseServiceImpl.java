package com.student.service.impl;

import com.student.entity.StudentCourse;
import com.student.mapper.StudentCourseMapper;
import com.student.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;
    
    @Override
    public List<StudentCourse> findAll() {
        return studentCourseMapper.findAll();
    }
    
    @Override
    public StudentCourse findById(Integer id) {
        return studentCourseMapper.findById(id);
    }
    
    @Override
    public List<StudentCourse> findByStudentId(Integer studentId) {
        return studentCourseMapper.findByStudentId(studentId);
    }
    
    @Override
    public List<StudentCourse> findByCourseId(Integer courseId) {
        return studentCourseMapper.findByCourseId(courseId);
    }
    
    @Override
    public StudentCourse findByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return studentCourseMapper.findByStudentIdAndCourseId(studentId, courseId);
    }
    
    @Override
    public boolean save(StudentCourse studentCourse) {
        return studentCourseMapper.insert(studentCourse) > 0;
    }
    
    @Override
    public boolean delete(Integer id) {
        return studentCourseMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean deleteByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return studentCourseMapper.deleteByStudentIdAndCourseId(studentId, courseId) > 0;
    }
}




