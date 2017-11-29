package main.cn.lc.mylock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * �򵥵Ŀ�������ʵ�֣�ʹ��һ����������¼��ǰ�߳��������Ĵ����������ʱ��������1���ͷ���ʱ��������1��������������0ʱ��ʾ�ͷ�����
 * **/
public class SimpleReentrantLock implements Lock{
    
    // ָ���Ѿ���������߳�
    private volatile Thread exclusiveOwnerThread;
    
    // ��¼��ȡ��ͬһ�����Ĵ���
    private volatile int holdCount;
    
    private final java.util.concurrent.locks.Lock lock;
    
    // �Ƿ����Լ������������
    private final Condition isCountZero;
    
    public SimpleReentrantLock(){
        lock = new ReentrantLock();
        isCountZero = lock.newCondition();
        holdCount = 0;
    }
    
    @Override
    public void lock() {
        lock.lock();
        try{
            // ��ǰ�̵߳�����
            Thread currentThread = Thread.currentThread();
            // �����������߳����Լ�����ô��������1��ֱ�ӷ���
            if(exclusiveOwnerThread == currentThread){
                holdCount ++;
                return;
            }
            
            while(holdCount != 0){
                try {
                    isCountZero.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted");
                }
            }
            // ��exclusiveOwnerThread����Ϊ�Լ�
            exclusiveOwnerThread = currentThread;
            holdCount ++;
        }finally{
            lock.unlock();
        }
    }

    @Override
    public void unlock() {
        lock.lock();
        try{
            holdCount --;
            if(holdCount == 0){
                isCountZero.signalAll();
            }
        }finally{
            lock.unlock();
        }
    }

}


