package com.simo.emos.wx.service;

import com.simo.emos.wx.dao.repository.FaceModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceModelService {

    @Autowired
    private FaceModelRepository faceModelRepository;


}
