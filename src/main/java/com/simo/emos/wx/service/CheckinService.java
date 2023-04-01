package com.simo.emos.wx.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.dao.entity.constant.SystemConstants;
import com.simo.emos.wx.dao.repository.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private HolidaysService holidaysService;

    @Autowired
    private WorkdayService workdayService;

    @Autowired
    private SystemConstants systemConstants;

    /**
     * @Author simo
     * @Description 1、结合考勤表判断当前日前是否需要考勤
     *              2、判断该用户是否已签到
     *              3、判断当前是否在签到时间内
     * @Date 16:25 2023/4/1
     * @Param [openId]
     * @return boolean
     **/
    public String validCanCheckIn(String openId) {
        //结合考勤表判断当前日前是否需要考勤
        String type = "工作日";
        boolean weekend = DateUtil.date().isWeekend();
        if(weekend){
            boolean workday = workdayService.searchTodayIsWorkday();
            if(!workday){
                type = "假期中";
            }
        }else{
            boolean Holidays = holidaysService.searchTodayIsHolidays();
            if(Holidays){
                type = "假期中";
            }
        }
        //判断该用户是否已签到
        boolean has = checkinRepository.existsByUserIdAndCheckinTypeAndDate(
                openId,1, DateUtil.beginOfDay(new Date()));
        if(has){
            return "今日已经考勤，不用重复考勤";
        }
        //判断当前是否在签到时间内
        DateTime now = DateUtil.date();
        if("假期中".equals(type)){
            return "节假日不需要考勤";
        }else{
            DateTime attendanceStartTime = DateUtil.parseTimeToday(systemConstants.attendanceStartTime);
            DateTime attendanceEndTime = DateUtil.parseTimeToday(systemConstants.attendanceEndTime);
            if (now.isBefore(attendanceStartTime)) {
                return "没到上班考勤开始时间";
            }else if(now.isAfter(attendanceEndTime)){
                return "超过了上班考勤结束时间";
            }else {
                return "可以签到";
            }
        }



    }
}
