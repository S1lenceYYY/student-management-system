package com.student.controller;

import com.student.common.Result;
import com.student.common.SessionUtil;
import com.student.entity.Class;
import com.student.entity.Course;
import com.student.entity.Score;
import com.student.entity.User;
import com.student.service.ClassService;
import com.student.service.CourseService;
import com.student.service.ScoreService;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClassService classService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private ScoreService scoreService;
    
    @GetMapping("/index.html")
    public String index() {
        return "admin/index";
    }
    
    // ========== 班级管理 ==========
    @GetMapping("/class/list")
    @ResponseBody
    public Result classList() {
        List<Class> list = classService.findAll();
        return Result.success(list);
    }
    
    @PostMapping("/class/save")
    @ResponseBody
    public Result classSave(Class clazz) {
        boolean success = classService.save(clazz);
        return success ? Result.success() : Result.error("保存失败");
    }
    
    @PostMapping("/class/update")
    @ResponseBody
    public Result classUpdate(Class clazz) {
        boolean success = classService.update(clazz);
        return success ? Result.success() : Result.error("更新失败");
    }
    
    @PostMapping("/class/delete")
    @ResponseBody
    public Result classDelete(Integer id) {
        boolean success = classService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
    
    // ========== 课程管理 ==========
    @GetMapping("/course/list")
    @ResponseBody
    public Result courseList() {
        List<Course> list = courseService.findAll();
        return Result.success(list);
    }
    
    @PostMapping("/course/save")
    @ResponseBody
    public Result courseSave(Course course) {
        boolean success = courseService.save(course);
        return success ? Result.success() : Result.error("保存失败");
    }
    
    @PostMapping("/course/update")
    @ResponseBody
    public Result courseUpdate(Course course) {
        boolean success = courseService.update(course);
        return success ? Result.success() : Result.error("更新失败");
    }
    
    @PostMapping("/course/delete")
    @ResponseBody
    public Result courseDelete(Integer id) {
        boolean success = courseService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
    
    // ========== 学生管理 ==========
    @GetMapping("/student/list")
    @ResponseBody
    public Result studentList() {
        List<User> list = userService.findByRole("student");
        return Result.success(list);
    }
    
    @PostMapping("/student/save")
    @ResponseBody
    public Result studentSave(User user) {
        user.setRole("student");
        boolean success = userService.save(user);
        return success ? Result.success() : Result.error("保存失败");
    }
    
    @PostMapping("/student/update")
    @ResponseBody
    public Result studentUpdate(User user) {
        boolean success = userService.update(user);
        return success ? Result.success() : Result.error("更新失败");
    }
    
    @PostMapping("/student/delete")
    @ResponseBody
    public Result studentDelete(Integer id) {
        boolean success = userService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
    
    // ========== 成绩管理 ==========
    @GetMapping("/score/list")
    @ResponseBody
    public Result scoreList() {
        List<Score> list = scoreService.findAll();
        return Result.success(list);
    }
    
    @PostMapping("/score/save")
    @ResponseBody
    public Result scoreSave(Score score) {
        // 分数校验 0-100
        if (score.getScore() == null || 
            score.getScore().compareTo(BigDecimal.ZERO) < 0 || 
            score.getScore().compareTo(new BigDecimal("100")) > 0) {
            return Result.error("分数必须在0-100之间");
        }
        boolean success = scoreService.save(score);
        return success ? Result.success() : Result.error("保存失败");
    }
    
    @PostMapping("/score/update")
    @ResponseBody
    public Result scoreUpdate(Score score) {
        // 分数校验 0-100
        if (score.getScore() == null || 
            score.getScore().compareTo(BigDecimal.ZERO) < 0 || 
            score.getScore().compareTo(new BigDecimal("100")) > 0) {
            return Result.error("分数必须在0-100之间");
        }
        boolean success = scoreService.update(score);
        return success ? Result.success() : Result.error("更新失败");
    }
    
    @PostMapping("/score/delete")
    @ResponseBody
    public Result scoreDelete(Integer id) {
        boolean success = scoreService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
    
    // ========== 获取下拉选项数据 ==========
    @GetMapping("/class/options")
    @ResponseBody
    public Result classOptions() {
        return Result.success(classService.findAll());
    }
    
    @GetMapping("/teacher/options")
    @ResponseBody
    public Result teacherOptions() {
        return Result.success(userService.findByRole("teacher"));
    }
    
    @GetMapping("/student/options")
    @ResponseBody
    public Result studentOptions() {
        return Result.success(userService.findByRole("student"));
    }
    
    @GetMapping("/course/options")
    @ResponseBody
    public Result courseOptions() {
        return Result.success(courseService.findAll());
    }
}







