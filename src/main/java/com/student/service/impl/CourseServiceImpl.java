package com.student.service.impl;

import com.student.entity.Course;
import com.student.mapper.CourseMapper;
import com.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }
    
    @Override
    public Course findById(Integer id) {
        return courseMapper.findById(id);
    }
    
    @Override
    public List<Course> findByTeacherId(Integer teacherId) {
        return courseMapper.findByTeacherId(teacherId);
    }
    
    @Override
    public List<Course> findByClassId(Integer classId) {
        return courseMapper.findByClassId(classId);
    }
    
    @Override
    public List<Course> findByStudentId(Integer studentId) {
        return courseMapper.findByStudentId(studentId);
    }
    
    @Override
    public boolean save(Course course) {
        return courseMapper.insert(course) > 0;
    }
    
    @Override
    public boolean update(Course course) {
        return courseMapper.update(course) > 0;
    }
    
    @Override
    public boolean delete(Integer id) {
        return courseMapper.deleteById(id) > 0;
    }
}







