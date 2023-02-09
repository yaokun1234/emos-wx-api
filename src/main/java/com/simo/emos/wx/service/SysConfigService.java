package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.repository.SysConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConfigService {

    @Autowired
    private SysConfigRepository sysConfigRepository;


}
