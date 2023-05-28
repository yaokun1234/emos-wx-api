package com.simo.emos.wx.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.simo.emos.wx.dao.entity.Dept;
import com.simo.emos.wx.dao.entity.Meeting;
import com.simo.emos.wx.dao.entity.MessageEntity;
import com.simo.emos.wx.dao.entity.MessageRefEntity;
import com.simo.emos.wx.dao.repository.DeptRepository;
import com.simo.emos.wx.dao.repository.MeetingRepository;
import com.simo.emos.wx.dao.repository.MessageEntityRepository;
import com.simo.emos.wx.dao.repository.MessageRefEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MessageEntityRepository messageEntityRepository;

    @Autowired
    private MessageRefEntityRepository messageRefEntityRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private MeetingRepository meetingRepository;
    @Test
    void addUser() {

        ArrayList<MessageEntity> messageEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntities.add(messageEntity);
        }
        messageEntityRepository.saveAll(messageEntities);

        ArrayList<MessageRefEntity> messageRefEntitiesEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageRefEntity messageRefEntityEntity = new MessageRefEntity();
            messageRefEntitiesEntities.add(messageRefEntityEntity);
        }
        messageRefEntityRepository.saveAll(messageRefEntitiesEntities);

    }

    @Test
    void registerUser() {
        for (int i=1;i<=10;i++){
            Meeting meeting=new Meeting();
            meeting.setId(UUID.randomUUID().toString());
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议"+i);
            meeting.setCreatorId("4028818b86895f4f01868964de170000"); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("线上会议室");
            meeting.setStart("08:30");
            meeting.setEnd("10:30");
            meeting.setType(1);
//            meeting.setMembers("[15,16]");
            meeting.setDesc("会议研讨Emos项目上线测试");
            meeting.setInstanceId(IdUtil.simpleUUID());
            meeting.setStatus(3);
            meeting.setCreateTime(new Date());
            meetingRepository.save(meeting);
        }

    }

    @Test
    void test2(){
        List<Dept> all = deptRepository.findAll();
        System.out.println(all);
    }
}