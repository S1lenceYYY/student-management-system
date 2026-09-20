package com.student.service;

import com.student.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    List<User> findAll();
    List<User> findByRole(String role);
    List<User> findByClassId(Integer classId);
    User findById(Integer id);
    boolean save(User user);
    boolean update(User user);
    boolean delete(Integer id);
}







