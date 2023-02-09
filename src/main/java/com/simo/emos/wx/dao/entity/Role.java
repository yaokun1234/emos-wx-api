package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 角色表
 */
@Data
@Entity
@ApiModel("角色表")
@Table(name = "tb_role")
public class Role {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("角色名称")
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ApiModelProperty("权限集合")
    @Column(name = "permissions", nullable = false)
    private String permissions;

}
