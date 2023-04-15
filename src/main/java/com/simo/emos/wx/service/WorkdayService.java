package com.simo.emos.wx.service;

import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.dao.entity.Workday;
import com.simo.emos.wx.dao.repository.WorkdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkdayService {

    @Autowired
    private WorkdayRepository workdayRepository;


    public boolean searchTodayIsWorkday() {
        return workdayRepository.existsWorkdayByDate(DateUtil.today());
    }

    public List<String> searchWorkdayInRange(String startDate, String endDate) {
        return workdayRepository.findAllByDateBetween(startDate, endDate).stream().map(Workday::getDate).collect(Collectors.toList());
    }
}
