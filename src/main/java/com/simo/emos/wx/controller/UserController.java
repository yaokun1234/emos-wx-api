package com.simo.emos.wx.controller;

import com.simo.emos.wx.controller.form.LoginForm;
import com.simo.emos.wx.controller.form.RegisterForm;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.service.UserService;
import com.simo.emos.wx.util.RespBean;
import com.simo.emos.wx.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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







}
