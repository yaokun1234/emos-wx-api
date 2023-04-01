package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {

}