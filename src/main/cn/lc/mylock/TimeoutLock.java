package main.cn.lc.mylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ʱ�޶�������֧��tryLock��ʱ����
 * QNodeά��һ��ָ��preNodeָ��ǰһ���ڵ㡣��preNode == AVAILABLE��ʾ�Ѿ��ͷ�������preNode == null��ʾ�ȴ���
 * tailά��һ����������,ͨ��tail.getAndSet�������ǰһ���ڵ�,����ǰһ���ڵ�����,���ͷ���ʱǰһ���ڵ��preNode == AVAIABLE���Զ�֪ͨ��һ���ڵ��ȡ��
 * ��һ���ڵ㳬ʱ���߱��жϣ���ô����ǰ���ڵ㲻Ϊ�ա������ڵ㿴������ǰ���ڵ㲻Ϊ�գ����Ҳ���AVAILABLEʱ��֪������ڵ��˳��ˣ��ͻ�������
 * ���ڵ������������ٽ���������ǰ���ڵ���Ա�����
 * **/
public class TimeoutLock implements TryLock{
	// ����Ϊ��̬��������ֹ����ʱ����
	private static final QNode AVAILABLE = new QNode();
	
	// ԭ�ӱ���ָ���β
	private AtomicReference<QNode> tail;

	ThreadLocal<QNode> myNode;
	
	public TimeoutLock(){
		tail = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return new QNode();
			}
		};
	}
	
	@Override
	public void lock() {
		// ��CLHLock��ͬ��ÿ���½�һ��Node�������ø��̣߳�Ŀ����֧��ͬһ���߳̿��Զ�λ����������Ӱ�����������ڵ��״̬
		// CLHLock����Ҫÿ���½�Node����Ϊ��ʹ��������ָ�룬һ��ָ��ǰ���ڵ㡣��ǰ���ڵ��ͷź�Ϳ��Ի����ˡ�
		// CLHLockÿ���ͷ���ʱ����myNodeΪʧЧ��ǰ���ڵ㣬Ҳ��Ϊ��֧��ͬһ���߳̿��Զ�λ�ȡ������Ӱ�������ڵ�
		QNode node = new QNode();
		myNode.set(node);
		QNode pre = tail.getAndSet(node);
		if(pre != null){
			// ��ǰһ���ڵ���������ǰһ���ڵ���AVAILABLEʱ����ʾ�������
			while(pre.preNode != AVAILABLE){
				
			}
		}
	}

	@Override
	public void unlock() {
		QNode node = myNode.get();
		// CAS���������Ϊtrue����ʾ��Ψһ�ڵ㣬ֱ���ͷž��У������preNodeָ��AVAILABLE
		if(!tail.compareAndSet(node, null)){
			node.preNode = AVAILABLE;
		}
	}
	
	@Override
	public boolean trylock(long time, TimeUnit unit) throws InterruptedException {
		if(Thread.interrupted()){
			throw new InterruptedException();
		}
		boolean isInterrupted = false;
		long startTime = System.currentTimeMillis();
		long duration = TimeUnit.MILLISECONDS.convert(time, unit);
		// ע�⣺ÿ��tryLock��Ҫnew�µ�Node��Ϊ��ͬһ���߳̿��Զ�λ���������ÿ���̶߳�ʹ��ͬһ���ڵ㣬��Ӱ�����������Ľڵ�
		QNode node = new QNode();
		myNode.set(node);
		// ����һ�λ�ȡ��
		QNode pre = tail.getAndSet(node);
		// ��һ���ڵ����֮ǰ�Ľڵ㶼���Ѿ��ͷ������Ľڵ�, pre==AVAILABLE��ʾ�������
		if(pre == null || pre == AVAILABLE){
			return true;
		}
		// �ڸ���ʱ���ڶ�preNode����
		while((System.currentTimeMillis() - startTime < duration) && !isInterrupted){
			QNode predPreNode = pre.preNode;
			// ��ʾǰһ���ڵ��Ѿ��ͷ�������������preNode�򣬷���preNode��Ϊ��
			if(predPreNode == AVAILABLE){
				return true;
			}
			// ��prePreNode ��= nullʱ��ֻ�������������������ʱ�ˣ����߱��ж��ˡ�
			// ����prePreNode��Ϊ�յĽڵ㣬��������������һ���ڵ�
			else if(predPreNode != null){
				pre = predPreNode;
			}
			if(Thread.interrupted()){
				isInterrupted = true;
			}
		}
		
		// ��ʱ����interrupted����Ҫ����node��ǰ���ڵ㲻Ϊ��
		node.preNode = pre;
		
		if(isInterrupted){
			throw new InterruptedException();
		}
		return false;
	}
	
	public static class QNode {
		volatile QNode preNode;
	}
	
	public String toString(){
		return "TimeoutLock";
	}

}

