package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, String>, JpaSpecificationExecutor<Holidays> {

    boolean existsWorkdayByDate(String beginOfDay);

    List<Holidays> findAllByDateBetween(String startDate, String endDate);
}