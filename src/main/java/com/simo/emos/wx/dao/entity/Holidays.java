package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * 节假日表
 */
@Data
@Entity
@ApiModel("节假日表")
@Table(name = "tb_holidays")
public class Holidays {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("日期")
    @Column(name = "date", nullable = false)
    private Date date;

}
