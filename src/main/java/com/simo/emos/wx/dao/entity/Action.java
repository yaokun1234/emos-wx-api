package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 行为表
 */
@Data
@Entity
@ApiModel("行为表")
@Table(name = "tb_action")
public class Action {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("行为编号")
    @Column(name = "action_code", nullable = false)
    private String actionCode;

    @ApiModelProperty("行为名称")
    @Column(name = "action_name", nullable = false)
    private String actionName;

}
