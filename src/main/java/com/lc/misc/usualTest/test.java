package com.lc.misc.usualTest;

import com.google.common.collect.Sets;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
    public static void main(String args[]){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i=0;i<10;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }
}
