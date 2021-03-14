package com.lc.misc.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luochao
 * @date 2020/12/2 8:37 下午
 */
public class StrTest {

    private final static Lock lock = new ReentrantLock();

    private final static Condition a = lock.newCondition();

    private final static Condition b = lock.newCondition();


    public static void main(String[] args) {
        new Thread(new A()).start();
        new Thread(new B()).start();
    }

    static class A implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    System.out.println("A");
                    b.signal();
                    a.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.lock();
                }
            }
        }
    }

    static class B implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    System.out.println("B");
                    a.signal();
                    b.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.lock();
                }
            }
        }
    }

}
