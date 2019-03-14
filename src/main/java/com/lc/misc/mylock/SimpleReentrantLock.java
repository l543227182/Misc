package com.lc.misc.mylock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 简单的自定义排它锁
 */
public class SimpleReentrantLock implements Lock{
    
    // 拥有执行权的线程
    private volatile Thread exclusiveOwnerThread;
    
    // 应该是lock了几次
    private volatile int holdCount;
    
    private final java.util.concurrent.locks.Lock lock;
    
    // wati notify
    private final Condition isCountZero;
    
    public SimpleReentrantLock(){
        lock = new ReentrantLock();
        isCountZero = lock.newCondition();
        holdCount = 0;
    }
    
    public void lock() {
        lock.lock();
        try{

            Thread currentThread = Thread.currentThread();
            // 如果是当前线程 hold++
           if(exclusiveOwnerThread == currentThread){
                holdCount ++;
                return;
            }
            // 否则线程进入等待
            while(holdCount != 0){
                try {
                    isCountZero.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted");
                }
            }
            //
            exclusiveOwnerThread = currentThread;
            holdCount ++;
        }finally{
            lock.unlock();
        }
    }

    public void unlock() {
        lock.lock();
        try{
            holdCount --;
            // 唤醒所有 公平争抢锁
            if(holdCount == 0){
                isCountZero.signalAll();
            }
        }finally{
            lock.unlock();
        }
    }

}


