package com.oym.log.listener;

import com.oym.commons.utils.GsonUtil;
import com.oym.log.domain.dto.OperateLogDTO;
import com.oym.log.domain.message.OperateMessage;
import com.oym.log.transform.LogTransform;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import java.util.Optional;

/**
 * 操作日志消息消费
 *
 * @author oneyuanma
 * @date 2021/07/18
 */
@Component
@Slf4j
public class OperateLogListener {

    private static final Logger logger = LoggerFactory.getLogger(OperateLogListener.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @KafkaListener(topics = "operate_log_topic", groupId = "${kafka.consumer.group.id:oneManage-log}")
    public void consume(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            try {
                Object msg = message.get();
                OperateMessage operateMessage = GsonUtil.fromJson(String.valueOf(msg), OperateMessage.class);
                OperateLogDTO operateLogDTO = LogTransform.INS.transfer(operateMessage);
                saveOperateLog(operateLogDTO);
                log.info("login_log_topic consumer: Topic:{},Message:{}", topic, operateMessage.toString());
            } catch (Exception e) {
                logger.error("LoginLogListener consume err,msg:{}", ExceptionUtils.getStackTrace(e));
            } finally {
                // 不管成功失败都确定消息被消费
                ack.acknowledge();
            }
        }
    }

    private void saveOperateLog(OperateLogDTO operateLogDTO) {
        try {
            elasticsearchTemplate.save(operateLogDTO);
        } catch (Exception e) {
            logger.error("LoginLogListener loginlog save err,msg:{}", ExceptionUtils.getStackTrace(e));
        }
    }
}
