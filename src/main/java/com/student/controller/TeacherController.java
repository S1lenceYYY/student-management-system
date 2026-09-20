package com.student.controller;

import com.student.common.Result;
import com.student.common.SessionUtil;
import com.student.entity.Course;
import com.student.entity.Score;
import com.student.entity.StudentCourse;
import com.student.entity.User;
import com.student.service.CourseService;
import com.student.service.ScoreService;
import com.student.service.StudentCourseService;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private ScoreService scoreService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentCourseService studentCourseService;
    
    @GetMapping("/index.html")
    public String index() {
        return "teacher/index";
    }
    
    // 查询自身授课课程（可按学期筛选）
    @GetMapping("/course/list")
    @ResponseBody
    public Result courseList(String semester, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        List<Course> list = courseService.findByTeacherId(user.getId());
        // 如果指定了学期，进行筛选
        if (semester != null && !semester.isEmpty()) {
            list.removeIf(course -> !semester.equals(course.getSemester()));
        }
        return Result.success(list);
    }
    
    // 查询课程对应的学生成绩
    @GetMapping("/score/list")
    @ResponseBody
    public Result scoreList(Integer courseId, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(courseId);
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限访问该课程");
        }
        
        List<Score> list = scoreService.findByCourseId(courseId);
        return Result.success(list);
    }
    
    @PostMapping("/score/save")
    @ResponseBody
    public Result scoreSave(Score score, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(score.getCourseId());
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限操作该课程");
        }
        
        // 验证学生是否选课了该课程
        StudentCourse studentCourse = studentCourseService.findByStudentIdAndCourseId(
            score.getStudentId(), score.getCourseId());
        if (studentCourse == null) {
            return Result.error("该学生未选此课程，无法录入成绩");
        }
        
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
    public Result scoreUpdate( Score score, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Score existingScore = scoreService.findById(score.getId());
        if (existingScore == null) {
            return Result.error("成绩记录不存在");
        }
        
        Course course = courseService.findById(existingScore.getCourseId());
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限操作该课程");
        }
        
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
    public Result scoreDelete(Integer id, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Score score = scoreService.findById(id);
        if (score == null) {
            return Result.error("成绩记录不存在");
        }
        
        Course course = courseService.findById(score.getCourseId());
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限操作该课程");
        }
        
        boolean success = scoreService.delete(id);
        return success ? Result.success() : Result.error("删除失败");
    }
    
    // 获取课程下拉选项（仅限该教师的课程，可按学期筛选）
    @GetMapping("/course/options")
    @ResponseBody
    public Result courseOptions(String semester, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        List<Course> list = courseService.findByTeacherId(user.getId());
        // 如果指定了学期，进行筛选
        if (semester != null && !semester.isEmpty()) {
            list.removeIf(course -> !semester.equals(course.getSemester()));
        }
        return Result.success(list);
    }
    
    // 根据课程ID获取已选课的学生列表（只能录入选修了课程的学生成绩）
    @GetMapping("/course/students")
    @ResponseBody
    public Result courseStudents(Integer courseId, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(courseId);
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限访问该课程");
        }
        
        // 获取已选课的学生列表
        List<StudentCourse> studentCourses = studentCourseService.findByCourseId(courseId);
        List<User> students = new java.util.ArrayList<>();
        for (StudentCourse sc : studentCourses) {
            User student = userService.findById(sc.getStudentId());
            if (student != null) {
                students.add(student);
            }
        }
        return Result.success(students);
    }
    
    // 获取课程所属班级的所有学生（用于选课）
    @GetMapping("/course/class-students")
    @ResponseBody
    public Result courseClassStudents(Integer courseId, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(courseId);
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限访问该课程");
        }
        
        // 获取该课程所属班级的所有学生
        List<User> students = userService.findByClassId(course.getClassId());
        return Result.success(students);
    }
    
    // 获取课程的学生选课情况
    @GetMapping("/course/student-courses")
    @ResponseBody
    public Result courseStudentCourses(Integer courseId, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(courseId);
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限访问该课程");
        }
        
        List<StudentCourse> list = studentCourseService.findByCourseId(courseId);
        return Result.success(list);
    }
    
    // 为学生选课
    @PostMapping("/course/select")
    @ResponseBody
    public Result selectCourse(Integer courseId, Integer studentId, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(courseId);
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限操作该课程");
        }
        
        // 检查是否已选课
        StudentCourse existing = studentCourseService.findByStudentIdAndCourseId(studentId, courseId);
        if (existing != null) {
            return Result.error("该学生已选此课程");
        }
        
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        boolean success = studentCourseService.save(studentCourse);
        return success ? Result.success() : Result.error("选课失败");
    }
    
    // 取消选课
    @PostMapping("/course/unselect")
    @ResponseBody
    public Result unselectCourse(Integer courseId, Integer studentId, HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        // 验证课程是否属于该教师
        Course course = courseService.findById(courseId);
        if (course == null || !course.getTeacherId().equals(user.getId())) {
            return Result.error(403, "无权限操作该课程");
        }
        
        boolean success = studentCourseService.deleteByStudentIdAndCourseId(studentId, courseId);
        return success ? Result.success() : Result.error("取消选课失败");
    }
    
    // 获取所有学期列表（用于筛选）
    @GetMapping("/semesters")
    @ResponseBody
    public Result semesters(HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null || !SessionUtil.isTeacher(session)) {
            return Result.error(403, "无权限访问");
        }
        
        List<Course> courses = courseService.findByTeacherId(user.getId());
        java.util.Set<String> semesterSet = new java.util.HashSet<>();
        for (Course course : courses) {
            if (course.getSemester() != null && !course.getSemester().isEmpty()) {
                semesterSet.add(course.getSemester());
            }
        }
        List<String> semesters = new java.util.ArrayList<>(semesterSet);
        java.util.Collections.sort(semesters, java.util.Collections.reverseOrder());
        return Result.success(semesters);
    }
}

