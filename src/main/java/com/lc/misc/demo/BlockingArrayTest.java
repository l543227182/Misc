package com.lc.misc.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class BlockingArrayTest {
    public static void main(String[] args) throws InterruptedException {
        //final BlockingArray<Integer> blockingArray = new BlockingArray<Integer>(10);

        final BlockingArrayWithCondition<Integer> blockingArray = new BlockingArrayWithCondition<Integer>(100);


        final AtomicInteger count = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        blockingArray.put(count.incrementAndGet());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            t.start();
        }
        Thread.sleep(5000);
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {

                public void run() {
                    try {
                        blockingArray.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            t.start();
        }

    }
}
