package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String>, JpaSpecificationExecutor<Meeting> {

    Page<Meeting> findByIdIn(List<String> ids, Pageable pageable);
 }
