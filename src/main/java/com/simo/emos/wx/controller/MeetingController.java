package com.simo.emos.wx.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.controller.form.*;
import com.simo.emos.wx.dao.entity.Meeting;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.service.MeetingService;
import com.simo.emos.wx.service.UserService;
import com.simo.emos.wx.util.RespBean;
import com.simo.emos.wx.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/meeting")
@Slf4j
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @PostMapping("/searchMyMeetingListByPage")
    @ApiOperation("查询会议列表分页数据")
    public RespBean searchMyMeetingListByPage(@Valid @RequestBody SearchMyMeetingListByPageForm form){
        User user = SecurityUtil.getCurrentUser();
        int page=form.getPage();
        int length=form.getLength();
        int start=(page-1)*length;

        ArrayList list=meetingService.searchMyMeetingListByPage(user.getOpenId(),start,length);
        return RespBean.success(list);
    }

    @PostMapping("/insertMeeting")
    @ApiOperation("添加会议")
    public RespBean insertMeeting(@Valid @RequestBody InsertMeetingForm form){
        User user = SecurityUtil.getCurrentUser();
        if(form.getType()==2&&(form.getPlace()==null||form.getPlace().length()==0)){
            throw new ConditionException("线下会议地点不能为空");
        }
        DateTime d1= DateUtil.parse(form.getDate()+" "+form.getStart()+":00");
        DateTime d2= DateUtil.parse(form.getDate()+" "+form.getEnd()+":00");
        if(d2.isBeforeOrEquals(d1)){
            throw new ConditionException("结束时间必须大于开始时间");
        }
        Meeting entity=new Meeting();
        entity.setUuid(UUID.randomUUID().toString());
        entity.setTitle(form.getTitle());
        entity.setCreatorId(user.getOpenId());
        entity.setDate(form.getDate());
        entity.setPlace(form.getPlace());
        entity.setStart(form.getStart() + ":00");
        entity.setEnd(form.getEnd() + ":00");
        entity.setType(form.getType());
        entity.setDesc(form.getDesc());
        entity.setStatus(1);
        List<String> memberIds = JSONUtil.toList(form.getMembers(), String.class);
        meetingService.insertMeeting(entity,memberIds);

        return RespBean.success();
    }
    @PostMapping("/searchMeetingById")
    @ApiOperation("根据ID查询会议")
    public RespBean searchMeetingById(@Valid @RequestBody SearchMeetingByIdFrom form){
        Meeting meeting = meetingService.searchMeetingById(form.getId());
        return RespBean.success(meeting);
    }

    @PostMapping("/updateMeetingInfo")
    @ApiOperation("更新会议")
    public RespBean updateMeetingInfo(@Valid @RequestBody UpdateMeetingInfoForm form){
        if(form.getType()==2&&(form.getPlace()==null||form.getPlace().length()==0)){
            throw new ConditionException("线下会议地点不能为空");
        }
        DateTime d1= DateUtil.parse(form.getDate()+" "+form.getStart());
        DateTime d2= DateUtil.parse(form.getDate()+" "+form.getEnd());
        if(d2.isBeforeOrEquals(d1)){
            throw new ConditionException("结束时间必须大于开始时间");
        }
        if(!JSONUtil.isJsonArray(form.getMembers())){
            throw new ConditionException("members不是JSON数组");
        }
        Meeting meeting = meetingService.findById(form.getId());
        meeting.setTitle(form.getTitle());
        meeting.setDate(form.getDate());
        meeting.setPlace(form.getPlace());
        meeting.setStart(form.getStart());
        meeting.setEnd(form.getEnd());
        meeting.setType(form.getType());
        meeting.setDesc(form.getDesc());
        meeting.setStatus(1);
        meeting.setUpdateTime(new Date() );
        meetingService.updateMeetingInfo(meeting,form.getMembers());
        return RespBean.success();
    }
    @PostMapping("/deleteMeetingById")
    @ApiOperation("根据ID删除会议")
    public RespBean deleteMeetingById(@Valid @RequestBody DeleteMeetingByIdForm form){
        meetingService.deleteMeetingById(form.getId());
        return RespBean.success();
    }

    @PostMapping("/finishMeetingById")
    @ApiOperation("结束会议")
    public RespBean finishMeetingById(@Valid @RequestBody DeleteMeetingByIdForm form){
        meetingService.finishMeetingById(form.getId());
        return RespBean.success();
    }
}
