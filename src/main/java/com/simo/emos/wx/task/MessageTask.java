package com.simo.emos.wx.task;

import com.rabbitmq.client.*;
import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.dao.entity.MessageEntity;
import com.simo.emos.wx.dao.entity.MessageRefEntity;
import com.simo.emos.wx.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MessageTask {
    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private MessageService messageService;

    public void send(String topic, MessageEntity entity) {
        String id = messageService.insertMessage(entity);


        try (Connection connection = factory.newConnection(); // 创建链接
             Channel channel = connection.createChannel();  // 创建信道
        ) {
            /*
             * queueDeclare：队列申明 队列不存在时自动创建队列，如果存在使用存在的
             * 参数1：队列名称
             * 参数2：是否持久化
             * 参数3：是否独占
             * 参数4：没有消费者的时候是否自动删除队列
             * 参数5：其他
             */
            channel.queueDeclare(topic, true, false, false, null);
            HashMap map = new HashMap();
            map.put("messageId", id);

            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();

            /*
			 * 发布消息
			 * 参数1：交换机
			 * 参数2：队列
			 * 参数3：其他额外的参数
			 * 参数4：要发送的消息，byte[]类型
             */
            channel.basicPublish("", topic, properties, entity.getMsg().getBytes());
            log.debug("消息发送成功");
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new ConditionException("向MQ发送消息失败");
        }
    }

    @Async
    public void sendAsync(String topic, MessageEntity entity) {
        send(topic, entity);
    }

    public int receive(String topic) {
        int i = 0;
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(topic, true, false, false, null);
            while (true) {
                GetResponse response = channel.basicGet(topic, false);
                if (response != null) {
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String messageId = map.get("messageId").toString();
                    byte[] body = response.getBody();
                    String message = new String(body);
                    log.debug("从RabbitMQ接收的消息：" + message);

                    MessageRefEntity entity = new MessageRefEntity();
                    entity.setMessageId(messageId);
                    entity.setReceiverId(topic);
                    entity.setReadFlag(false);
                    entity.setLastFlag(true);
                    messageService.insertRef(entity);
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                    i++;
                }
                else {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new ConditionException("接收消息失败");
        }
        return i;
    }

    @Async
    public int receiveAsync(String topic) {
        return receive(topic);
    }

    public void deleteQueue(String topic){
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
        ) {
            channel.queueDelete(topic);
            log.debug("消息队列成功删除");
        }catch (Exception e) {
            log.error("删除队列失败", e);
            throw new ConditionException("删除队列失败");
        }
    }

    @Async
    public void deleteQueueAsync(String topic){
        deleteQueue(topic);
    }

}
