package com.lc.misc.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class MyKafkaConsumer {
    public static void main(String[] s) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.3.35:9092");
        props.put("group.id", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        System.out.println("****************poll start...");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            int count = records.count();
            if(count>0) {
                System.out.println("*********************the numbers of topic:" + count);
            }
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
        }
    }
}
