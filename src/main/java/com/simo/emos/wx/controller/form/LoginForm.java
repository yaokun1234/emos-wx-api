package com.simo.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author simo
 * @Date 2023/2/28 00:24
 * @Version 1.0
 **/
@ApiModel
@Data
public class LoginForm {
    @NotBlank(message = "临时授权不能为空")
    private String code;
}
