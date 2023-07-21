package com.simo.emos.wx.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tb_dept")
public class Dept {

    @Id
    @ApiModelProperty("主键")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ApiModelProperty("部门名称2")
    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "dept_id")
    private List<User> members;

}
