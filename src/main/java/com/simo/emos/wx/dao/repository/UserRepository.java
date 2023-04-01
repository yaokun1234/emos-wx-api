package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query(value = "SELECT p.permission_name " +
            "FROM tb_user u " +
            "JOIN tb_role r " +
            "ON JSON_CONTAINS(u.role, CAST(r.id AS CHAR)) " +
            "JOIN tb_permission p " +
            "ON JSON_CONTAINS(r.permissions, CAST(p.id AS CHAR)) " +
            "Where u.open_id = ?1 AND u.status = 0", nativeQuery = true)
    Set<String> searchUserPermissions(String openId);

    User findByRoot(Boolean root);


    User findByOpenId(String openid);
}