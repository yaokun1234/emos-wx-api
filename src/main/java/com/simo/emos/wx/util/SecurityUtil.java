package com.simo.emos.wx.util;

import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.dao.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description
 * @Author simo
 * @Date 2023/4/1 16:20
 * @Version 1.0
 **/

public class SecurityUtil {


    public static User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return (User)authentication.getPrincipal();
        }else{
            throw new ConditionException("请先登陆！！");
        }

    }
}
