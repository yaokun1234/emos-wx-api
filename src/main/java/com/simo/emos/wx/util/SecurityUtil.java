package com.simo.emos.wx.util;

import com.simo.emos.wx.dao.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description
 * @Author simo
 * @Date 2023/4/1 16:20
 * @Version 1.0
 **/

public class SecurityUtil {


    public static User getCurrentUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
