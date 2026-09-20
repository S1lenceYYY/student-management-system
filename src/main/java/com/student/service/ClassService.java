package com.student.service;

import com.student.entity.Class;
import java.util.List;

public interface ClassService {
    List<Class> findAll();
    Class findById(Integer id);
    boolean save(Class clazz);
    boolean update(Class clazz);
    boolean delete(Integer id);
}







