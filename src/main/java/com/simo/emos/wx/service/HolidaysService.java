package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidaysService {

    @Autowired
    private HolidaysRepository holidaysRepository;


}
