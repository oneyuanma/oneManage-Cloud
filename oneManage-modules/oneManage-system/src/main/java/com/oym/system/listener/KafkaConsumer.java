package com.oym.system.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

//    @KafkaListener(topics = "topic.test", groupId = "topic.group1")
//    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//
//        Optional message = Optional.ofNullable(record.value());
//        if (message.isPresent()) {
//            Object msg = message.get();
//            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
//            ack.acknowledge();
//        }
//    }

}
