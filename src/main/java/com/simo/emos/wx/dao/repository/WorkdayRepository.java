package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Workday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkdayRepository extends JpaRepository<Workday, Integer>, JpaSpecificationExecutor<Workday> {

}