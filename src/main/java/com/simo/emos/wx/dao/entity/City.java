package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * 疫情城市列表
 */
@Data
@Entity
@ApiModel("疫情城市列表")
@Table(name = "tb_city")
public class City {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("城市名称")
    @Column(name = "city", nullable = false,columnDefinition = "comment '城市名称中文'")
    private String city;

    @ApiModelProperty("拼音简称")
    @Column(name = "code", nullable = false)
    private String code;

}
