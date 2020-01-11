package com.lc.web.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    public KafkaTemplate kafkaTemplate;

    public void produceDo(String value ) {
        kafkaTemplate.send("topic-test",value);
    }
}
