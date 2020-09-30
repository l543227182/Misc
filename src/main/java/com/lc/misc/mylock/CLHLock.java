package com.lc.misc.mylock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * �޽��������ʹ��һ����������֯�߳�
 * ����L������n���̣߳���ô���Ŀռ临�Ӷ�ΪO(L+n)
 **/
public class CLHLock implements Lock {
    // ԭ�ӱ���ָ���β
    private AtomicReference<QNode> tail;
    // ����ָ�룬һ��ָ���Լ���Node,һ��ָ��ǰһ��Node
    ThreadLocal<QNode> myNode;
    ThreadLocal<QNode> myPreNode;

    public CLHLock() {
        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPreNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    public void lock() {
        QNode node = myNode.get();
        node.lock = true;
        // CASԭ�Ӳ�������֤ԭ����
        QNode preNode = tail.getAndSet(node);
        myPreNode.set(preNode);
        // volatile�������ܱ�֤���ͷż�ʱ֪ͨ
        // ֻ��ǰһ���ڵ��״̬���������ٻ���һ��������
        while (preNode.lock) {

        }
    }

    public void unlock() {
        QNode node = myNode.get();
        node.lock = false;
        // ��myNodeָ��preNode��Ŀ���Ǳ�֤ͬһ���߳��´λ���ʹ�����������ΪmyNodeԭ��ָ��Ľڵ������ĺ�һ���ڵ��preNode����
        // ��ֹ����߳��´�lockʱmyNode.get���ԭ���Ľڵ�
        myNode.set(myPreNode.get());
    }

    static class QNode {
        volatile boolean lock;
    }

    public String toString() {
        return "CLHLock";
    }
}
