package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CheckinRepository extends JpaRepository<Checkin, String>, JpaSpecificationExecutor<Checkin> {

    boolean existsByOpenIdAndCheckinTypeAndDate(String openID, int checkinType, String date);

    Checkin findFirstByDateAndCheckinType(String date,int checkinType);

    long countByOpenIdAndCheckinType(String openId,int checkinType);

    List<Checkin> findAllByOpenIdAndDateBetween(String openId, String startDate, String endDate);
}