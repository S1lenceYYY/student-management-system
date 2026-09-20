package com.student.common;

import com.student.entity.User;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    
    public static final String USER_SESSION_KEY = "user";
    
    public static void setUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }
    
    public static User getUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
    
    public static void removeUser(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
    
    public static boolean isAdmin(HttpSession session) {
        User user = getUser(session);
        return user != null && "admin".equals(user.getRole());
    }
    
    public static boolean isTeacher(HttpSession session) {
        User user = getUser(session);
        return user != null && "teacher".equals(user.getRole());
    }
    
    public static boolean isStudent(HttpSession session) {
        User user = getUser(session);
        return user != null && "student".equals(user.getRole());
    }
}







