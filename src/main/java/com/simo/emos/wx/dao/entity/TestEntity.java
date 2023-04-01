package com.simo.emos.wx.dao.entity;

/**
 * @Description
 * @Author simo
 * @Date 2023/3/6 16:30
 * @Version 1.0
 **/


public class TestEntity {

    private String name;
    private String age;



    public void setName(String name) {
        System.out.println("setName"+name);
        this.name = name;
    }



    public void setAge(String age) {
        System.out.println("setAge"+age);
        this.age = age;
    }

    public TestEntity(String name, String age) {
        this.name = name;
        this.age = age;
        System.out.println("构造器");
        System.out.println(name);
        System.out.println(age);
    }
}
