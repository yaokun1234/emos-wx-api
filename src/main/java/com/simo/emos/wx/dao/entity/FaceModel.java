package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_face_model")
public class FaceModel {

    @Id
    @ApiModelProperty("主键值")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("用户ID")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @ApiModelProperty("用户人脸模型")
    @Column(name = "face_model", nullable = false)
    private String faceModel;

}
