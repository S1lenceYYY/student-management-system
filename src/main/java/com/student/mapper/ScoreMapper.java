package com.student.mapper;

import com.student.entity.Score;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ScoreMapper {
    List<Score> findAll();
    Score findById(Integer id);
    List<Score> findByStudentId(@Param("studentId") Integer studentId);
    List<Score> findByCourseId(@Param("courseId") Integer courseId);
    Score findByStudentIdAndCourseId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
    int insert(Score score);
    int update(Score score);
    int deleteById(Integer id);
}







