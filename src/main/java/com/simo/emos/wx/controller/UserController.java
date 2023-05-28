package com.simo.emos.wx.controller;

import cn.hutool.json.JSONUtil;
import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.controller.form.LoginForm;
import com.simo.emos.wx.controller.form.RegisterForm;
import com.simo.emos.wx.controller.form.SearchMembersForm;
import com.simo.emos.wx.controller.form.SearchUserGroupByDeptForm;
import com.simo.emos.wx.dao.entity.Dept;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.service.UserService;
import com.simo.emos.wx.util.RespBean;
import com.simo.emos.wx.util.SecurityUtil;
import com.simo.emos.wx.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author simo
 * @Date 2023/2/21 10:53
 * @Version 1.0
 **/

@RestController
@Api("用户管理APi")
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation("用户注册")
    @PostMapping("/register")
    public RespBean registerUser(@RequestBody @Validated RegisterForm registerForm){

        //添加用户
        User dbUser = userService.registerUser(registerForm);
        //获取token,保存
        String token = TokenUtil.generateToken(dbUser.getOpenId());
        userService.saveCacheToken(token,dbUser.getOpenId());
        //获取权限
        Set<String> permissions = userService.searchUserPermissions(dbUser.getOpenId());
        //返回
        HashMap<String, Object> data = new HashMap<>();
        data.put("token",token);
        data.put("permissions",permissions);
        return RespBean.success("恭喜您，注册成功",data);
    }

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public RespBean login(@RequestBody LoginForm loginForm ){
        String openId = userService.login(loginForm.getCode());
        String token = TokenUtil.generateToken(openId);
        userService.saveCacheToken(token,openId);
        Set<String> permissions = userService.searchUserPermissions(openId);
        //返回
        HashMap<String, Object> data = new HashMap<>();
        data.put("token",token);
        data.put("permissions",permissions);
        return RespBean.success("登陆成功",data);
    }

    @GetMapping("/searchUserSummary")
    @ApiOperation("查询用户摘要信息")
    public RespBean searchUserSummary(){
        User user = SecurityUtil.getCurrentUser();
        HashMap map = userService.searchUserSummary(user);
        return RespBean.success(map);
    }

    @PostMapping("/searchUserGroupByDept")
    @ApiOperation("查询员工列表，按照部门分组排列")
    public RespBean searchUserGroupByDept(@Valid @RequestBody SearchUserGroupByDeptForm form){
        List<Dept> list=userService.searchUserGroupByDept(form.getKeyword());
        return RespBean.success(list);
    }

    @PostMapping("/searchMembers")
    @ApiOperation("查询成员")
    public RespBean searchMembers(@Valid @RequestBody SearchMembersForm form){
        if(!JSONUtil.isJsonArray(form.getMembers())){
            throw new ConditionException("members不是JSON数组");
        }
        List<String> param=JSONUtil.parseArray(form.getMembers()).toList(String.class);
        List<User> list=userService.searchMembers(param);
        return RespBean.success(list);
    }



}
