package com.lc.web.controller;

import com.lc.web.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/putValueToKafka")
    public String putValue(String value) {
        kafkaProducer.produceDo(value);
        return value;
    }
}
