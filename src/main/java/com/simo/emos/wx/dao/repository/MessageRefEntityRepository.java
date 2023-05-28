package com.simo.emos.wx.dao.repository;

import com.simo.emos.wx.dao.entity.MessageRefEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRefEntityRepository extends MongoRepository<MessageRefEntity, String> {

    long countAllByReadFlagAndReceiverId(boolean b, String openId);


    MessageRefEntity findByMessageId(String id);

    MessageRefEntity findFirstById(String id);
}
