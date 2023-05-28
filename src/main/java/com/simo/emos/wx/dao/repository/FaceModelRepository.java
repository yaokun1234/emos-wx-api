package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.FaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceModelRepository extends JpaRepository<FaceModel, String>, JpaSpecificationExecutor<FaceModel> {

}