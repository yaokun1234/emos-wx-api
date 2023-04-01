package com.simo.emos.wx.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void addUser() {

        BigDecimal multiply = ((BigDecimal.valueOf(7433).add(BigDecimal.valueOf(2272))).divide(BigDecimal.valueOf(56885).add(BigDecimal.valueOf(2272)), 2, BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100));
        BigDecimal multiply1 = ((BigDecimal.valueOf(10).add(BigDecimal.valueOf(10))).divide(BigDecimal.valueOf(20).add(BigDecimal.valueOf(50)), 2, BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100));
        BigDecimal divide = BigDecimal.valueOf(9805).divide(BigDecimal.valueOf(56935),2,BigDecimal.ROUND_HALF_UP);
        System.out.println(multiply);
    }

    @Test
    void registerUser() {




    }
}