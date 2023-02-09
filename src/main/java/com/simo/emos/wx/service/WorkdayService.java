package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.repository.WorkdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkdayService {

    @Autowired
    private WorkdayRepository workdayRepository;


}
