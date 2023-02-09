package com.simo.emos.wx.controller;

import com.simo.emos.wx.util.RespBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author simo
 * @Date 2022/12/29 01:06
 * @Version 1.0
 **/

@RestController
@Api("测试")
public class TestController {

    @GetMapping("/test")
    public RespBean a(String s){
        return RespBean.success();
    }
}
