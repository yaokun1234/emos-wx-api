package com.simo.emos.wx.service;

import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.dao.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HolidaysService {

    @Autowired
    private HolidaysRepository holidaysRepository;


    public boolean searchTodayIsHolidays() {
        return holidaysRepository.existsWorkdayByDate(DateUtil.beginOfDay(new Date()));
    }
}
