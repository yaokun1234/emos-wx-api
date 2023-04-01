package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_permission")
public class Permission {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("权限")
    @Column(name = "permission_name", nullable = false)
    private String permissionName;

    @ApiModelProperty("模块ID")
    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @ApiModelProperty("行为ID")
    @Column(name = "action_id", nullable = false)
    private Long actionId;

}
