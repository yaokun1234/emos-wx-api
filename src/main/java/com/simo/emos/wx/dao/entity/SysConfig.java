package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_config")
public class SysConfig {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("参数名")
    @Column(name = "param_key", nullable = false)
    private String paramKey;

    @ApiModelProperty("参数值")
    @Column(name = "param_value")
    private String paramValue;

    @ApiModelProperty("状态")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty("备注")
    @Column(name = "remark")
    private String remark;

}
