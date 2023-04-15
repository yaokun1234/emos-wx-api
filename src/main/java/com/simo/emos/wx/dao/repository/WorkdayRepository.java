package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Workday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WorkdayRepository extends JpaRepository<Workday, String>, JpaSpecificationExecutor<Workday> {

    boolean existsWorkdayByDate(String date);

    List<Workday> findAllByDateBetween(String startDate, String endDate);
}