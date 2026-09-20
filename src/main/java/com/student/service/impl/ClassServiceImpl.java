package com.student.service.impl;

import com.student.entity.Class;
import com.student.mapper.ClassMapper;
import com.student.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    
    @Autowired
    private ClassMapper classMapper;
    
    @Override
    public List<Class> findAll() {
        return classMapper.findAll();
    }
    
    @Override
    public Class findById(Integer id) {
        return classMapper.findById(id);
    }
    
    @Override
    public boolean save(Class clazz) {
        return classMapper.insert(clazz) > 0;
    }
    
    @Override
    public boolean update(Class clazz) {
        return classMapper.update(clazz) > 0;
    }
    
    @Override
    public boolean delete(Integer id) {
        return classMapper.deleteById(id) > 0;
    }
}







