package com.student.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        
        if (user == null) {
            // 未登录，跳转到登录页
            response.sendRedirect(request.getContextPath() + "/login.html");
            return false;
        }
        
        return true;
    }
}







