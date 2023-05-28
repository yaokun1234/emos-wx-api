package com.simo.emos.wx.service;

import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.controller.form.RegisterForm;
import com.simo.emos.wx.dao.entity.Dept;
import com.simo.emos.wx.dao.entity.MessageEntity;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.dao.entity.constant.UserConstant;
import com.simo.emos.wx.dao.repository.UserRepository;
import com.simo.emos.wx.task.MessageTask;
import com.simo.emos.wx.util.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DeptService deptService;

    @Autowired
    private MessageTask messageTask;
    @Value(value = "${token.expire}")
    private int expire;

    public RespBean addUser(User user) {
        checkUser(user);
        return null;
    }



    public User registerUser(RegisterForm registerForm) {

        //获取openid
        String openid = commonService.getOpenid(registerForm.getCode());

        User user = new User();
        user.setOpenId(openid);
        user.setNickname(registerForm.getNickname());
        user.setPhoto(registerForm.getPhoto());
        user.setStatus(Integer.valueOf(UserConstant.USER_STATUS_ON.getCode()));
        //判断超级管理员
        if(UserConstant.REGISTERCODE_ROOT.getCode().equals(registerForm.getRegisterCode())){
            User rootUser = userRepository.findByRoot(true);
            if(rootUser == null){
                //设置信息
                user.setRoot(true);
                user.setRole(UserConstant.ROLE_ROOT.getCode());
            }else{
                throw new ConditionException("已有超级管理员");
            }

        }else{
            //TODO 注册非管理员
            user.setRoot(false);
        }
        User dbUser = userRepository.save(user);
        MessageEntity entity=new MessageEntity();
        entity.setSenderId("0");
        entity.setSenderName("系统消息");
        entity.setSendTime(new Date());
        messageTask.sendAsync(openid,entity);
        return dbUser;
    }

    public Set<String> searchUserPermissions(String userId){
        return userRepository.searchUserPermissions(userId);
    }
    private void checkUser(User user) {

    }

    public void saveCacheToken(String token, String openId) {
        redisTemplate.opsForValue().set(token,openId+"",expire, TimeUnit.MINUTES);
    }

    public String login(String code) {
        String openid = commonService.getOpenid(code);
        User dbUser = userRepository.findByOpenId(openid);
        if(dbUser != null){
            return dbUser.getOpenId();
        }else {
            throw new ConditionException("账号不存在");
        }
    }

    public User findByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    public HashMap searchUserSummary(User user) {
        Dept dept = deptService.findById(user.getDeptId());
        HashMap<String, String> map = new HashMap<>();
        map.put("name",user.getName());
        map.put("photo",user.getPhoto());
        map.put("deptName",dept.getDeptName());
        return map;
    }


    public List<User> findByOpenIds(List<String> members) {
        return userRepository.findByOpenIdIn(members);
    }

    public List<Dept> searchUserGroupByDept(String keyword) {

        return deptService.searchUserGroupByDept(keyword);
    }

    public List<User> searchMembers(List param) {
        return userRepository.findByOpenIdIn(param);
    }
}
