package com.simo.emos.wx.controller;

import com.simo.emos.wx.controller.form.LoginForm;
import com.simo.emos.wx.controller.form.RegisterForm;
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

    @GetMapping("/searchUserSummary")
    @ApiOperation("查询用户摘要信息")
    public RespBean searchUserSummary(){
        User user = SecurityUtil.getCurrentUser();
        HashMap map = userService.searchUserSummary(user);
        return RespBean.success(map);
    }

//    @PostMapping("/searchUserGroupByDept")
//    @ApiOperation("查询员工列表，按照部门分组排列")
//    @RequiresPermissions(value = {"ROOT","EMPLOYEE:SELECT"},logical = Logical.OR)
//    public R searchUserGroupByDept(@Valid @RequestBody SearchUserGroupByDeptForm form){
//        ArrayList<HashMap> list=userService.searchUserGroupByDept(form.getKeyword());
//        return R.ok().put("result",list);
//    }
//
//    @PostMapping("/searchMembers")
//    @ApiOperation("查询成员")
//    @RequiresPermissions(value = {"ROOT", "MEETING:INSERT", "MEETING:UPDATE"},logical = Logical.OR)
//    public R searchMembers(@Valid @RequestBody SearchMembersForm form){
//        if(!JSONUtil.isJsonArray(form.getMembers())){
//            throw new EmosException("members不是JSON数组");
//        }
//        List param=JSONUtil.parseArray(form.getMembers()).toList(Integer.class);
//        ArrayList list=userService.searchMembers(param);
//        return R.ok().put("result",list);
//    }
//
//    @PostMapping("/selectUserPhotoAndName")
//    @ApiOperation("查询用户姓名和头像")
//    @RequiresPermissions(value = {"WORKFLOW:APPROVAL"})
//    public R selectUserPhotoAndName(@Valid @RequestBody SelectUserPhotoAndNameForm form){
//        if(!JSONUtil.isJsonArray(form.getIds())){
//            throw new EmosException("参数不是JSON数组");
//        }
//        List<Integer> param=JSONUtil.parseArray(form.getIds()).toList(Integer.class);
//        List<HashMap> list=userService.selectUserPhotoAndName(param);
//        return R.ok().put("result",list);
//    }
//
//    @GetMapping("/genUserSig")
//    @ApiOperation("生成用户签名")
//    public R genUserSig(@RequestHeader("token") String token){
//        int id=jwtUtil.getUserId(token);
//        String email=userService.searchMemberEmail(id);
//        TLSSigAPIv2 api=new TLSSigAPIv2(appid,key);
//        String userSig=api.genUserSig(email,expire);
//        return R.ok().put("userSig",userSig).put("email",email);
//    }
//
//    private void saveCacheToken(String token,int userId){
//        redisTemplate.opsForValue().set(token,userId+"",cacheExpire, TimeUnit.DAYS);
//    }





}
