package com.simo.emos.wx.dao.repository;

import cn.hutool.core.date.DateTime;
import com.simo.emos.wx.dao.entity.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface HolidaysRepository extends JpaRepository<Holidays, String>, JpaSpecificationExecutor<Holidays> {

    boolean existsWorkdayByDate(DateTime beginOfDay);
}