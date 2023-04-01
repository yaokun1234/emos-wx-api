package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface CheckinRepository extends JpaRepository<Checkin, String>, JpaSpecificationExecutor<Checkin> {

    boolean existsByUserIdAndCheckinTypeAndDate(String userId, int checkinType, Date date);
}