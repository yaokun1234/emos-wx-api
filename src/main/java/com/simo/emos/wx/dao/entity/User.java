package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

/**
 * 用户表
 */
@Data
@Entity
@ApiModel("用户表")
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;

    @Column(name = "open_id")
    @ApiModelProperty("长期授权字符串")
    private String openId;


    @ApiModelProperty("用户名")
    @Column(name = "username")
    private String username;


    @ApiModelProperty("密码")
    @Column(name = "password")
    private String password;

    @ApiModelProperty("昵称")
    @Column(name = "nickname")
    @NotBlank
    private String nickname;

    @Column(name = "photo")
    @ApiModelProperty("头像网址")
    private String photo;

    @Column(name = "name")
    @ApiModelProperty("姓名")
    private String name;

    @Column(name = "sex")
    @ApiModelProperty("性别 1_男 0_女")
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
    private String deptId;

    @ApiModelProperty("状态 0_禁用 1_在职 2_离职")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @ApiModelProperty("角色")
    @Column(name = "role", nullable = false)
    private String role;

//    @ApiModelProperty("部门")
//    @ManyToOne()
//    @JoinColumn(name = "dept_id")
//    @JsonIgnoreProperties("members")
//    private Dept dept;


    public User() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == 0;
    }

    @Override
    public boolean isEnabled() {
        return status == 0;
    }
}
