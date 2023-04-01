package com.simo.emos.wx.service;

import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.dao.repository.WorkdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WorkdayService {

    @Autowired
    private WorkdayRepository workdayRepository;


    public boolean searchTodayIsWorkday() {
        return workdayRepository.existsWorkdayByDate(DateUtil.beginOfDay(new Date()));
    }
}
