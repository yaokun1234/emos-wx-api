package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private String id;

    @Column(name = "uuid")
    @ApiModelProperty("UUID")
    private String uuid;

    @ApiModelProperty("会议题目")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty("创建人ID")
    @Column(name = "creator_id", nullable = false)
    private String creatorId;

    @ApiModelProperty("日期")
    @Column(name = "date")
    private String date;

    @Column(name = "place")
    @ApiModelProperty("开会地点")
    private String place;

    @ApiModelProperty("开始时间")
    @Column(name = "start")
    private String start;

    @ApiModelProperty("结束时间")
    @Column(name = "end")
    private String end;

    @ApiModelProperty("会议类型（1在线会议，2线下会议）")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty("会议内容")
    @Column(name = "meet_desc")
    private String desc;

    @ApiModelProperty("工作流实例ID")
    @Column(name = "instance_id")
    private String instanceId;

    @ApiModelProperty("状态（1未开始，2进行中，3已结束）")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column(name = "update_time")
    private Date updateTime;

    @ApiModelProperty("创建人")
    @Transient
    private User creator;

    @ApiModelProperty("参会人")
    @Transient
    private List<User> members;



    public Meeting() {
        this.id = UUID.randomUUID().toString();
        this.createTime = new Date();
    }
}
