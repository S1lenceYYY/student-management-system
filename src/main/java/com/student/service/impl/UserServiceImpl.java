package com.student.service.impl;

import com.student.entity.User;
import com.student.mapper.UserMapper;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
    
    @Override
    public List<User> findByRole(String role) {
        return userMapper.findByRole(role);
    }
    
    @Override
    public List<User> findByClassId(Integer classId) {
        return userMapper.findByClassId(classId);
    }
    
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
    
    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }
    
    @Override
    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean delete(Integer id) {
        return userMapper.deleteById(id) > 0;
    }
}







