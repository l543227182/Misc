package com.lc.misc.mylock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * �޽��������ʹ��һ����������֯�߳�
 * ����L������n���̣߳���ô���Ŀռ临�Ӷ�ΪO(L+n)
 **/
public class MCSLock implements Lock {
    // ԭ�ӱ���ָ���β
    private AtomicReference<QNode> tail;
    // ����ָ�룬һ��ָ���Լ���Node,һ��ָ��ǰһ��Node
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    public void lock() {
        QNode node = myNode.get();
        // CASԭ�Ӳ�������֤ԭ����
        QNode preNode = tail.getAndSet(node);
        // ���preNode���ڿգ�֤���ǵ�һ����ȡ����
        if (preNode != null) {
            node.lock = true;
            preNode.next = node;
            // ���߳��Լ���node��������������cache��NUMAϵͳ�ܹ���˵�����ʱ����ڴ��ٶ����������ڵ���ڴ�
            while (node.lock) {

            }
        }
    }

    public void unlock() {
        QNode node = myNode.get();
        if (node.next == null) {
            // CAS�������ж��Ƿ�û���¼���Ľڵ�
            if (tail.compareAndSet(node, null)) {
                // û���¼���Ľڵ�,ֱ�ӷ���
                return;
            }
            // ���¼���Ľڵ㣬�ȴ���������ϵ
            while (node.next == null) {

            }
        }
        // ֪ͨ��һ���ڵ��ȡ��
        node.next.lock = false;
        // ����next�ڵ�Ϊ�գ�Ϊ�´λ�ȡ������״̬
        node.next = null;
    }

    static class QNode {
        volatile boolean lock;
        volatile QNode next;
    }

    public String toString() {
        return "MCSLock";
    }
}

