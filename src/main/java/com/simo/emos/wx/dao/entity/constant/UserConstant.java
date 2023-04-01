package com.simo.emos.wx.dao.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserConstant {

    REGISTERCODE_ROOT("000000","超级管理员注册码"),
    ROLE_ROOT("[0]","超级管理员权限"),
    USER_STATUS_ON("0","正常"),
    USER_STATUS_OFF("1","停用");


    private final String code;
    private final String mes;
}
