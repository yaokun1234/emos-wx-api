package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.repository.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;


}
