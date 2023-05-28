package com.simo.emos.wx.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.dao.entity.Meeting;
import com.simo.emos.wx.dao.entity.MeetingMembers;
import com.simo.emos.wx.dao.entity.MessageEntity;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.dao.repository.MeetingMembersRepository;
import com.simo.emos.wx.dao.repository.MeetingRepository;
import com.simo.emos.wx.dao.repository.MessageRefEntityRepository;
import com.simo.emos.wx.dao.repository.UserRepository;
import com.simo.emos.wx.task.MessageTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingMembersRepository meetingMembersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRefEntityRepository messageRefEntityRepository;

    @Autowired
    private MessageTask messageTask;


    public ArrayList<HashMap> searchMyMeetingListByPage(String openId, int start, int length) {

        List<MeetingMembers> meetingMembersList = meetingMembersRepository.findAllByMemberId(openId);

        List<String> meetingIds = meetingMembersList.stream().map(MeetingMembers::getMeetingId).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(start, length, Sort.by("date").descending());

        Page<Meeting> meetingPage = meetingRepository.findByIdIn(meetingIds,pageRequest);
        List<Meeting> meetingList = meetingPage.getContent();
        List<Meeting> meetingList1 = meetingList.stream().map(one -> {
            User user = userRepository.findByOpenId(one.getCreatorId());
            one.setCreator(user);
            String date = one.getDate();
            String startTime = one.getStart();
            String time =  date+" "+startTime;
            DateTime parse = DateUtil.parse(time);
            DateTime now = DateUtil.date();
            if (now.isAfter(parse) && one.getStatus() != 3) {
                one.setStatus(2);
            }

            return one;
        }).collect(Collectors.toList());

        String date = null;
        ArrayList resultList = new ArrayList();
        HashMap resultMap = null;
        JSONArray array = null;
        for (Meeting meeting : meetingList1) {
            String temp = meeting.getDate();
            if (!temp.equals(date)) {
                date = temp;
                resultMap = new HashMap();
                resultMap.put("date", date);
                array = new JSONArray();
                resultMap.put("list", array);
                resultList.add(resultMap);
            }
            array.put(meeting);
        }
        return resultList;
    }

    public void insertMeeting(Meeting entity, List<String> members) {
        meetingRepository.save(entity);
        List<MeetingMembers> meetingMembersList = members.stream().map(one -> {
            MeetingMembers meetingMembers = new MeetingMembers();
            meetingMembers.setMeetingId(entity.getId());
            meetingMembers.setMemberId(one);
            return meetingMembers;
        }).collect(Collectors.toList());
        meetingMembersRepository.saveAll(meetingMembersList);

        User user = userRepository.findByOpenId(entity.getCreatorId());
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMsg(user.getName()+"邀请您参加会议");
        messageEntity.setSenderName(user.getName());
        messageEntity.setSenderPhoto(user.getPhoto());
        messageEntity.setSenderId(user.getOpenId());
        for (String member : members) {
            messageTask.sendAsync(member,messageEntity);
        }
    }

    public Meeting searchMeetingById(String id) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(id);
        if(optionalMeeting.isPresent()){
            Meeting meeting = optionalMeeting.get();
            List<MeetingMembers> meetingMembersList = meetingMembersRepository.findAllByMeetingId(id);
            List<String> memberIds = meetingMembersList.stream().map(MeetingMembers::getMemberId).collect(Collectors.toList());
            List<User> userList = userRepository.findByOpenIdIn(memberIds);
            meeting.setMembers(userList);
            return meeting;

        }else{
            throw new ConditionException("会议不存在");
        }
    }

    @Transactional
    public void updateMeetingInfo(Meeting meeting, @NotBlank String members) {
        meetingRepository.save(meeting);
        List<String> memberIds = JSONUtil.toList(members, String.class);
        meetingMembersRepository.deleteAllByMeetingId(meeting.getId());
        List<MeetingMembers> meetingMembersList = memberIds.stream().map(one -> {
            MeetingMembers meetingMembers = new MeetingMembers();
            meetingMembers.setMeetingId(meeting.getId());
            meetingMembers.setMemberId(one);
            return meetingMembers;
        }).collect(Collectors.toList());
        meetingMembersRepository.saveAll(meetingMembersList);
    }

    public Meeting findById(String id) {
        return meetingRepository.findById(id).get();
    }

    @Transactional()
    public void deleteMeetingById(String id) {
        meetingRepository.deleteById(id);
        meetingMembersRepository.deleteAllByMeetingId(id);
    }

    public void finishMeetingById(String id) {
        Optional<Meeting> byId = meetingRepository.findById(id);
        if(byId.isPresent()){
            Meeting meeting = byId.get();
            meeting.setStatus(3);
            meetingRepository.save(meeting);
        }
    }
}
