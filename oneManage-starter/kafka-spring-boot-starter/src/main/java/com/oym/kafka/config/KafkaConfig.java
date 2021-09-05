package com.oym.kafka.config;

import com.oym.kafka.client.KafkaClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;


/**
 * Kafka配置类
 *
 * @author oneyuanma
 * @date 2021/07/18
 */
@Configuration
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaConfig {

    @Bean
    public KafkaClient kafkaClient(KafkaTemplate<String, Object> kafkaTemplate) {
        KafkaClient kafkaClient = new KafkaClient(kafkaTemplate);
        return kafkaClient;
    }
}
