package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * @Description
 * @Author simo
 * @Date 2023/4/22 00:52
 * @Version 1.0
 **/

@Data
@Entity
@ApiModel("会议人员关系表")
@Table(name = "meeting_members")
public class MeetingMembers {
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("会议Id")
    private String meetingId;

    @ApiModelProperty("人员Id")
    private String memberId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public MeetingMembers() {
        this.id = UUID.randomUUID().toString();
        this.createTime = new Date();
    }
}
