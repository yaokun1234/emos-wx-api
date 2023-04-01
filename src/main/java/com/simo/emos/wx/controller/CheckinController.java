package com.simo.emos.wx.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public RespBean checkin(){
        return null;

    }

    @PostMapping("/createFaceModel")
    @ApiOperation("创建人脸模型")
    public RespBean createFaceModel(){
        return null;


    }

    @GetMapping("/searchTodayCheckin")
    @ApiOperation("查询用户当日签到数据")
    public RespBean searchTodayCheckin(){
        return null;

    }

    @PostMapping("/searchMonthCheckin")
    @ApiOperation("查询用户某月签到数据")
    public RespBean searchMonthCheckin(){
        return null;
    }
}
