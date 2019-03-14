package com.lc.misc.usualTest;

import java.util.concurrent.atomic.AtomicInteger;

public class isNotTest {
    private static AtomicInteger flag = new AtomicInteger(1);

    public static void main(String[] args) {
        new execThread(flag,1,2).start();
        new execThread(flag,2,2).start();
    }
}

class execThread  extends  Thread{
    private AtomicInteger flag;
    private int currentNum;
    private int length ;
    public execThread(AtomicInteger flag,int currentNum,int length) {
        this.flag = flag;
        this.length = length;
        this.currentNum = currentNum;
    }

    @Override
    public void run() {
        while(true || Thread.currentThread().isInterrupted()) {
            while (flag.compareAndSet(currentNum, (currentNum) % length + 1)) {
            }
                System.out.println(currentNum + " - "  + Thread.currentThread().getName());
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
