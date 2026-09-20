package com.student.controller;

import com.student.common.Result;
import com.student.common.SessionUtil;
import com.student.entity.Course;
import com.student.entity.Score;
import com.student.entity.User;
import com.student.service.CourseService;
import com.student.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private ScoreService scoreService;
    
    @GetMapping("/index.html")
    public String index() {
        return "student/index";
    }
    
    // 查询个人所属班级的课程
    @GetMapping("/course/list")
    @ResponseBody
    public Result courseList(HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isStudent(session)) {
            return Result.error(403, "无权限访问");
        }
        List<Course> list = courseService.findByStudentId(user.getId());
        return Result.success(list);
    }
    
    // 查询自身对应成绩
    @GetMapping("/score/list")
    @ResponseBody
    public Result scoreList(HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isStudent(session)) {
            return Result.error(403, "无权限访问");
        }
        List<Score> list = scoreService.findByStudentId(user.getId());
        return Result.success(list);
    }
}







