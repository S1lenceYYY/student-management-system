package com.student.service.impl;

import com.student.entity.Score;
import com.student.mapper.ScoreMapper;
import com.student.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    
    @Autowired
    private ScoreMapper scoreMapper;
    
    @Override
    public List<Score> findAll() {
        return scoreMapper.findAll();
    }
    
    @Override
    public Score findById(Integer id) {
        return scoreMapper.findById(id);
    }
    
    @Override
    public List<Score> findByStudentId(Integer studentId) {
        return scoreMapper.findByStudentId(studentId);
    }
    
    @Override
    public List<Score> findByCourseId(Integer courseId) {
        return scoreMapper.findByCourseId(courseId);
    }
    
    @Override
    public Score findByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return scoreMapper.findByStudentIdAndCourseId(studentId, courseId);
    }
    
    @Override
    public boolean save(Score score) {
        return scoreMapper.insert(score) > 0;
    }
    
    @Override
    public boolean update(Score score) {
        return scoreMapper.update(score) > 0;
    }
    
    @Override
    public boolean delete(Integer id) {
        return scoreMapper.deleteById(id) > 0;
    }
}







