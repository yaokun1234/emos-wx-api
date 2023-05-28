package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "message_ref")
@ApiModel
public class MessageRefEntity implements Serializable {

    @ApiModelProperty("主键")
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("消息ID")
    @Indexed
    private String messageId;

    @ApiModelProperty("接受者ID")
    @Indexed
    private String receiverId;

    @ApiModelProperty("是否已读")
    private Boolean readFlag;

    @ApiModelProperty("是否为新接受的消息")
    private Boolean lastFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    public MessageRefEntity() {
        this.id = UUID.randomUUID().toString();
        this.createTime = new Date();
    }
}
