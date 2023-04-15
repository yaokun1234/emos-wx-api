package com.simo.emos.wx.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.dao.entity.Checkin;
import com.simo.emos.wx.dao.entity.Dept;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.dao.entity.constant.SystemConstants;
import com.simo.emos.wx.dao.repository.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private DeptService deptService;

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
        boolean has = checkinRepository.existsByOpenIdAndCheckinTypeAndDate(
                openId,1, DateUtil.beginOfDay(new Date()).toDateStr());
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

    public void checkin(HashMap param) {

        Date nowDate =DateUtil.date();
        DateTime attendanceTime =DateUtil.parseTimeToday(systemConstants.attendanceTime);
        DateTime attendanceStartTime = DateUtil.parseTimeToday(systemConstants.attendanceStartTime);
        DateTime attendanceEndTime = DateUtil.parseTimeToday(systemConstants.attendanceEndTime);
        int status=1;
        //正常
        if(nowDate.compareTo(attendanceStartTime) >=0 && nowDate.compareTo(attendanceTime) <=0 ){
            status=1;
        //迟到
        } else if(nowDate.compareTo(attendanceEndTime)< 0 && nowDate.compareTo(attendanceTime) > 0){
            status=2;
        } else{
            throw new ConditionException("超出考勤时间段，无法考勤");
        }
        String openId = (String) param.get("openId");
        String city= (String) param.get("city");
        String district= (String) param.get("district");
        String address= (String) param.get("address");
        String country= (String) param.get("country");
        String province= (String) param.get("province");

        //保存签到记录
        Checkin checkinTable = new Checkin();
        checkinTable.setOpenId(openId);
        checkinTable.setAddress(address);
        checkinTable.setCountry(country);
        checkinTable.setProvince(province);
        checkinTable.setCity(city);
        checkinTable.setDistrict(district);
        checkinTable.setStatus(status);
        checkinTable.setDate(DateUtil.today());
        checkinTable.setCreateTime(nowDate);
        checkinTable.setCheckinType(1);
        checkinRepository.save(checkinTable);
    }

    public HashMap searchTodayCheckin(User user) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name",user.getName());
        map.put("photo",user.getPhoto());

        Dept dept = deptService.findById(user.getDeptId());
        map.put("deptName",dept.getDeptName());

        Checkin checkin = checkinRepository.findFirstByDateAndCheckinType(DateUtil.today(), 1);
        map.put("address",checkin.getAddress());
        if(1 == checkin.getStatus()){
            map.put("status","正常");
        }else if(2 == checkin.getStatus()){
            map.put("status","迟到");
        }
        map.put("checkinTime",DateUtil.formatTime(checkin.getCreateTime()));
        map.put("date",checkin.getDate());

        return map;
    }

    public long searchCheckinDays(String openId) {
        return checkinRepository.countByOpenIdAndCheckinType(openId,1);
    }

    public ArrayList<HashMap> searchWeekCheckin(String openId, String startDate, String endDate) {

        List<Checkin> checkinList = checkinRepository.findAllByOpenIdAndDateBetween(openId,startDate,endDate);
        List<String> holidaysList = holidaysService.searchHolidaysInRange(startDate,endDate);
        List<String> workdayList = workdayService.searchWorkdayInRange(startDate,endDate);
        DateRange range = DateUtil.range(DateUtil.parseDate(startDate),DateUtil.parseDate(endDate), DateField.DAY_OF_MONTH);
        ArrayList<HashMap> list=new ArrayList<>();
        range.forEach(one->{
            String date=one.toString("yyyy-MM-dd");
            String type="工作日";
            if(one.isWeekend()){
                type="节假日";
            }
            if(holidaysList != null && holidaysList.contains(date)){
                type="节假日";
            }
            else if(workdayList!=null&&workdayList.contains(date)){
                type="工作日";
            }
            String status="";
            if(type.equals("工作日") && DateUtil.compare(one,DateUtil.date())<=0){
                status="缺勤";
                boolean flag=false;
                for (Checkin checkin:checkinList){
                    if(checkin.getDate().equals(date)){
                        if(1 == checkin.getStatus()){
                            status = "正常";
                        }else if(2 == checkin.getStatus()){
                            status = "迟到";
                        }
                        flag=true;
                        break;
                    }
                }
                DateTime endTime = DateUtil.parseTimeToday(systemConstants.attendanceEndTime);
                String today = DateUtil.today();
                if(date.equals(today) && DateUtil.date().isBefore(endTime) && flag==false){
                    status="";
                }
            }
            HashMap map=new HashMap();
            map.put("date",date);
            map.put("status",status);
            map.put("type",type);
            map.put("day",one.dayOfWeekEnum().toChinese("周"));
            list.add(map);
        });
        return list;

    }


    public ArrayList<HashMap> searchMonthCheckin(String openId, String startDate, String endDate) {
        return this.searchWeekCheckin(openId, startDate, endDate);

    }
}
