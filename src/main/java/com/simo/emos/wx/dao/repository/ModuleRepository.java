package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, String>, JpaSpecificationExecutor<Module> {

}