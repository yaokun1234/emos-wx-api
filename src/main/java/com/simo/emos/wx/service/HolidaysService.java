package com.simo.emos.wx.service;

import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.dao.entity.Holidays;
import com.simo.emos.wx.dao.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidaysService {

    @Autowired
    private HolidaysRepository holidaysRepository;


    public boolean searchTodayIsHolidays() {
        return holidaysRepository.existsWorkdayByDate(DateUtil.today());
    }

    public List<String> searchHolidaysInRange(String startDate, String endDate) {
        return holidaysRepository.findAllByDateBetween(startDate, endDate).stream().map(Holidays::getDate).collect(Collectors.toList());
    }
}
