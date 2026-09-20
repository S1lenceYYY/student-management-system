package com.student.mapper;

import com.student.entity.Class;
import java.util.List;

public interface ClassMapper {
    List<Class> findAll();
    Class findById(Integer id);
    int insert(Class clazz);
    int update(Class clazz);
    int deleteById(Integer id);
}







