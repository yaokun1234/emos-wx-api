package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;


}
