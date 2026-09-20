package com.student.controller;

import com.student.common.Result;
import com.student.common.SessionUtil;
import com.student.entity.User;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login.html")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            SessionUtil.setUser(session, user);
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtil.removeUser(session);
        return "redirect:/login.html";
    }
    
    @GetMapping("/")
    public String index(HttpSession session) {
        User user = SessionUtil.getUser(session);
        if (user == null) {
            return "redirect:/login.html";
        }
        
        String role = user.getRole();
        if ("admin".equals(role)) {
            return "redirect:/admin/index.html";
        } else if ("teacher".equals(role)) {
            return "redirect:/teacher/index.html";
        } else if ("student".equals(role)) {
            return "redirect:/student/index.html";
        }
        return "redirect:/login.html";
    }
}







