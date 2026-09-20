package com.student.service;

import com.student.entity.Score;
import java.util.List;

public interface ScoreService {
    List<Score> findAll();
    Score findById(Integer id);
    List<Score> findByStudentId(Integer studentId);
    List<Score> findByCourseId(Integer courseId);
    Score findByStudentIdAndCourseId(Integer studentId, Integer courseId);
    boolean save(Score score);
    boolean update(Score score);
    boolean delete(Integer id);
}







