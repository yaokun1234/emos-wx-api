package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 */
@Data
@Entity
@ApiModel("用户表")
@Table(name = "tb_user")
public class User {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "open_id")
    @ApiModelProperty("长期授权字符串")
    private String openId;

    @ApiModelProperty("昵称")
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "photo")
    @ApiModelProperty("头像网址")
    private String photo;

    @Column(name = "name")
    @ApiModelProperty("姓名")
    private String name;

    @Column(name = "sex")
    @ApiModelProperty("性别")
    private String sex;

    @Column(name = "tel")
    @ApiModelProperty("手机号码")
    private String tel;

    @Column(name = "email")
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("入职日期")
    @Column(name = "hiredate")
    private Date hiredate;

    @ApiModelProperty("是否是超级管理员")
    @Column(name = "root", nullable = false)
    private Boolean root;

    @Column(name = "dept_id")
    @ApiModelProperty("部门编号")
    private Long deptId;

    @ApiModelProperty("状态")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @ApiModelProperty("角色")
    @Column(name = "role", nullable = false)
    private String role;

}
