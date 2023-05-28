package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.entity.Dept;
import com.simo.emos.wx.dao.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;


    public Dept findById(String deptId) {
        return deptRepository.findById(deptId).get();
    }

    public List<Dept> searchUserGroupByDept(String keyword) {
        return deptRepository.findAll();
    }
}
