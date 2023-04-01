package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 签到表
 */
@Data
@Entity
@ApiModel("签到表")
@Table(name = "tb_checkin")
public class Checkin {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("用户ID")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "address")
    @ApiModelProperty("签到地址")
    private String address;

    @ApiModelProperty("国家")
    @Column(name = "country")
    private String country;

    @ApiModelProperty("省份")
    @Column(name = "province")
    private String province;

    @Column(name = "city")
    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区划")
    @Column(name = "district")
    private String district;

    @ApiModelProperty("考勤结果")
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "risk")
    @ApiModelProperty("风险等级")
    private Long risk;

    @ApiModelProperty("签到类型 1_签到 2_签退")
    @Column(name = "checkin_type", nullable = false)
    private int checkinType;

    @ApiModelProperty("签到日期")
    @Column(name = "date", nullable = false)
    private Date date;

    @ApiModelProperty("签到时间")
    @Column(name = "create_time", nullable = false)
    private Date createTime;

}
