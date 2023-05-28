package com.simo.emos.wx.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.simo.emos.wx.dao.entity.MessageEntity;
import com.simo.emos.wx.dao.entity.MessageRefEntity;
import com.simo.emos.wx.dao.repository.MessageEntityRepository;
import com.simo.emos.wx.dao.repository.MessageRefEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author simo
 * @Date 2023/4/16 15:11
 * @Version 1.0
 **/

@Service
public class MessageService {

    @Autowired
    private MessageRefEntityRepository messageRefEntityRepository;
    @Autowired
    private MessageEntityRepository messageEntityRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public String insertMessage(MessageEntity entity) {
        MessageEntity save = messageEntityRepository.save(entity);
        return save.getId();
    }

    public void insertRef(MessageRefEntity entity) {
        messageRefEntityRepository.save(entity);
    }

    public long searchLastCount(String openId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lastFlag").is(true).and("receiverId").is(openId));
        Update update = new Update();
        update.set("lastFlag", false);
        UpdateResult result = mongoTemplate.updateMulti(query, update, "message_ref");
        long rows = result.getModifiedCount();
        return rows;
    }

    public long searchUnreadCount(String openId) {
        return messageRefEntityRepository.countAllByReadFlagAndReceiverId(false,openId);
    }

    public List<HashMap> searchMessageByPage(String openId, long start, int length) {
        JSONObject json=new JSONObject();
        json.set("$toString","$_id");
        Aggregation aggregation=Aggregation.newAggregation(
                Aggregation.addFields().addField("id").withValue(json).build(),
                Aggregation.lookup("message_ref","id","messageId","ref"),
                Aggregation.match(Criteria.where("ref.receiverId").is(openId)),
                Aggregation.sort(Sort.by(Sort.Direction.DESC,"sendTime")),
                Aggregation.skip(start),
                Aggregation.limit(length)
        );
        AggregationResults<HashMap> results=mongoTemplate.aggregate(aggregation,"message",HashMap.class);
        List<HashMap> list=results.getMappedResults();
        list.forEach(one->{
            List<MessageRefEntity> refList= (List<MessageRefEntity>) one.get("ref");
            MessageRefEntity entity=refList.get(0);
            boolean readFlag=entity.getReadFlag();
            String refId=entity.getId();
            one.put("readFlag",readFlag);
            one.put("refId",refId);
            one.remove("ref");
            one.remove("_id");
            Date sendTime= (Date) one.get("sendTime");
            sendTime= DateUtil.offset(sendTime, DateField.HOUR,-8);

            String today=DateUtil.today();
            if(today.equals(DateUtil.date(sendTime).toDateStr())){
                one.put("sendTime",DateUtil.format(sendTime,"HH:mm"));
            }
            else{
                one.put("sendTime",DateUtil.format(sendTime,"yyyy/MM/dd"));
            }
        });
        return list;
    }

    public MessageEntity searchMessageById(String id) {
        return messageEntityRepository.findById(id).orElse(null);
    }

    public long updateUnreadMessage(String id) {
        Optional<MessageRefEntity> messageRefEntityOptional = messageRefEntityRepository.findById(id);
        if (messageRefEntityOptional.isPresent()) {
            MessageRefEntity messageRefEntity = messageRefEntityOptional.get();
            messageRefEntity.setReadFlag(true);
            messageRefEntityRepository.save(messageRefEntity);
            return 1;
        }else {
            return 0;
        }

    }

    public boolean deleteMessageRefById(String id) {
        messageRefEntityRepository.deleteById(id);
        Optional<MessageRefEntity> messageRefEntityOptional = messageRefEntityRepository.findById(id);
        if(messageRefEntityOptional.isPresent()){
            return false;
        }else{
            return true;
        }
    }
}
