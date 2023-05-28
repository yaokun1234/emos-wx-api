package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept, String>, JpaSpecificationExecutor<Dept> {

}