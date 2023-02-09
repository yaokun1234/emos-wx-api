package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * 会议表
 */
@Data
@Entity
@ApiModel("会议表")
@Table(name = "tb_meeting")
public class Meeting {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    @ApiModelProperty("UUID")
    private String uuid;

    @ApiModelProperty("会议题目")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty("创建人ID")
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @ApiModelProperty("日期")
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "place")
    @ApiModelProperty("开会地点")
    private String place;

    @ApiModelProperty("开始时间")
    @Column(name = "start", nullable = false)
    private Time start;

    @ApiModelProperty("结束时间")
    @Column(name = "end", nullable = false)
    private Time end;

    @ApiModelProperty("会议类型（1在线会议，2线下会议）")
    @Column(name = "type", nullable = false)
    private Integer type;

    @ApiModelProperty("会议内容")
    @Column(name = "desc", nullable = false)
    private String desc;

    @ApiModelProperty("工作流实例ID")
    @Column(name = "instance_id")
    private String instanceId;

    @ApiModelProperty("状态（1未开始，2进行中，3已结束）")
    @Column(name = "status", nullable = false)
    private Integer status;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @ApiModelProperty("参与者")
    @Column(name = "members", nullable = false)
    private String members;

}
