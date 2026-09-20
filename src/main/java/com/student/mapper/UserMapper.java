package com.student.mapper;

import com.student.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findById(@Param("id") Integer id);
    List<User> findAll();
    List<User> findByRole(@Param("role") String role);
    List<User> findByClassId(@Param("classId") Integer classId);
    int insert(User user);
    int update(User user);
    int deleteById(@Param("id") Integer id);
}







