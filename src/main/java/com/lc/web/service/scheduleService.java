package com.lc.web.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class scheduleService {

    @Scheduled(fixedRate = 5000)
    public void testException() {
        System.out.println("--  testException --");
        int i=0;
        int j = 5/i;
    }
}
