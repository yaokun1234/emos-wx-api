package com.simo.emos.wx.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.controller.form.CheckinForm;
import com.simo.emos.wx.controller.form.SearchMonthCheckinForm;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.dao.entity.constant.SystemConstants;
import com.simo.emos.wx.service.CheckinService;
import com.simo.emos.wx.service.UserService;
import com.simo.emos.wx.util.RespBean;
import com.simo.emos.wx.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@RequestMapping("/checkin")
@RestController
@Api("签到模块Web接口")
@Slf4j
public class CheckinController {

    @Value("${emos.image-folder:null}")
    private String imageFolder;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConstants constants;

    @Autowired
    private CheckinService checkinService;

    @GetMapping("/validCanCheckIn")
    @ApiOperation("查看用户今天是否可以签到")
    public RespBean validCanCheckIn(){
        User user = SecurityUtil.getCurrentUser();
        String checkin = checkinService.validCanCheckIn(user.getOpenId());
        return RespBean.success(checkin);
    }

    @PostMapping("/checkin")
    @ApiOperation("签到")
    public RespBean checkin(CheckinForm form, @RequestParam("photo") MultipartFile file){
        if(file==null){
            return RespBean.error("没有上传文件");
        }
        User user = SecurityUtil.getCurrentUser();
        String fileName=file.getOriginalFilename().toLowerCase();
        if(!fileName.endsWith(".jpg")){
            return RespBean.error("必须提交JPG格式图片");
        } else{
            String path=imageFolder+"/"+fileName;
            try{
                file.transferTo(Paths.get(path));
                HashMap param=new HashMap();
                param.put("openId",user.getOpenId());
                param.put("path",path);
                param.put("city",form.getCity());
                param.put("district",form.getDistrict());
                param.put("address",form.getAddress());
                param.put("country",form.getCountry());
                param.put("province",form.getProvince());
                checkinService.checkin(param);
                return RespBean.success("签到成功");
            }catch (IOException e){
                log.error(e.getMessage(),e);
                throw new ConditionException("图片保存错误");
            }
        }
    }


    @GetMapping("/searchTodayCheckin")
    @ApiOperation("查询用户当日签到数据")
    public RespBean searchTodayCheckin(){
        User user = SecurityUtil.getCurrentUser();
        HashMap map = checkinService.searchTodayCheckin(user);
        map.put("attendanceTime",constants.attendanceTime);
        map.put("closingTime",constants.closingTime);
        long days=checkinService.searchCheckinDays(user.getOpenId());
        map.put("checkinDays",days);

        DateTime hiredate= DateTime.of(user.getHiredate());
        DateTime startDate=DateUtil.beginOfWeek(DateUtil.date());
        if(startDate.isBefore(hiredate)){
            startDate=hiredate;
        }
        DateTime endDate=DateUtil.endOfWeek(DateUtil.date());
        HashMap param=new HashMap();
        param.put("startDate",startDate.toString());
        param.put("endDate",endDate.toString());
        param.put("openId",user.getOpenId());
        ArrayList<HashMap> list=checkinService.searchWeekCheckin(user.getOpenId(),startDate.toString(),endDate.toString());
        map.put("weekCheckin",list);
        return RespBean.success(map);

    }

    @PostMapping("/searchMonthCheckin")
    @ApiOperation("查询用户某月签到数据")
    public RespBean searchMonthCheckin(@Valid @RequestBody SearchMonthCheckinForm form){
        User user = SecurityUtil.getCurrentUser();
        DateTime hiredate = DateTime.of(user.getHiredate());
        String month = form.getMonth()<10?"0"+form.getMonth():form.getMonth().toString();
        DateTime startDate=DateUtil.parse(form.getYear()+"-"+month+"-01");
        if(startDate.isBefore(DateUtil.beginOfMonth(hiredate))){
            throw new ConditionException("只能查询入职之后日期的数据");
        }
        if(startDate.isBefore(hiredate)){
            startDate = hiredate;
        }
        DateTime endDate = DateUtil.endOfMonth(startDate);

        ArrayList<HashMap> list=checkinService.searchMonthCheckin(user.getOpenId(),startDate.toDateStr(),endDate.toDateStr());

        int sum_1=0,sum_2=0,sum_3=0;
        for(HashMap<String,String> one:list){
            String type=one.get("type");
            String status=one.get("status");
            if("工作日".equals(type)){
                if("正常".equals(status)){
                    sum_1++;
                }
                else if("迟到".equals(status)){
                    sum_2++;
                }
                else if("缺勤".equals(status)){
                    sum_3++;
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        map.put("sum_1",sum_1);
        map.put("sum_2",sum_2);
        map.put("sum_3",sum_3);
        return RespBean.success(map);
    }
}
