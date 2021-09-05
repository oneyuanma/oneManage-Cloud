package com.oym.kafka.client;

import com.oym.commons.utils.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafkaClient
 *
 * @author oneyuanma
 * @date 2020/04/14
 */
@Component
public class KafkaClient {

    private Logger logger = LoggerFactory.getLogger(KafkaClient.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaClient(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * kafka消息发送
     *
     * @param topic
     * @param obj
     */
    public void send(String topic, Object obj) {
        String obj2String = GsonUtil.toJson(obj);
        send(topic, obj2String);
    }

    /**
     * kafka消息发送
     *
     * @param topic
     * @param message
     */
    public void send(String topic, String message) {
        logger.info("准备发送消息为：{}", message);
        //发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                // 发送失败的处理
                logger.info(topic + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                // 成功的处理
                logger.info(topic + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });

    }

}
