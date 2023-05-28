package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageEntityRepository extends MongoRepository<MessageEntity, String> {
}
