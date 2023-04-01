package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysConfigRepository extends JpaRepository<SysConfig, String>, JpaSpecificationExecutor<SysConfig> {

}