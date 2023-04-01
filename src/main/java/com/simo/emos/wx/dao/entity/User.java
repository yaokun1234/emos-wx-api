package com.simo.emos.wx.dao.entity;

import com.simo.emos.wx.util.TokenUtil;
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
    private Long deptId;

    @ApiModelProperty("状态 0_可用 1_不可用")
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
        return TokenUtil.generateToken(openId);
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
