package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 模块资源表
 */
@Data
@Entity
@ApiModel("模块资源表")
@Table(name = "tb_module")
public class Module {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("模块编号")
    @Column(name = "module_code", nullable = false)
    private String moduleCode;

    @ApiModelProperty("模块名称")
    @Column(name = "module_name", nullable = false)
    private String moduleName;

}
