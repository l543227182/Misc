package com.lc.web.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "topic-test")
    public void consumerDo (ConsumerRecord<?, ?> record) {
        System.out.println("message === " + record);
    }
}
