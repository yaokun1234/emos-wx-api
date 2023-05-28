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
@Document(collection = "message")
@ApiModel("消息表")
public class MessageEntity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("防止消息被重复消费")
    @Indexed(unique = true)
    private String uuid;

    @ApiModelProperty("发送者ID")
    @Indexed
    private String senderId;

    @ApiModelProperty("发送者头像")
    private String senderPhoto="https://static-1258386385.cos.ap-beijing.myqcloud.com/img/System.jpg";

    @ApiModelProperty("发送者名称")
    private String senderName;

    @ApiModelProperty("消息正文")
    private String msg;

    @ApiModelProperty("发送时间")
    @Indexed
    private Date sendTime;

    public MessageEntity() {
        this.id = UUID.randomUUID().toString();
        this.uuid = UUID.randomUUID().toString();
        this.sendTime = new Date();
    }
}
