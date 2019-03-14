package com.lc.misc.mylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 不知道怎么就乱码了
 **/
public class TimeoutLock implements TryLock{
	//
	private static final QNode AVAILABLE = new QNode();
	
	// 队尾指针
	private AtomicReference<QNode> tail;

	// 通过threadlocal模拟队列
	ThreadLocal<QNode> myNode;
	
	public TimeoutLock(){
		tail = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>(){
			protected QNode initialValue(){
				return new QNode();
			}
		};
	}
	
	public void lock() {
		QNode node = new QNode();
		myNode.set(node);
		QNode pre = tail.getAndSet(node);
		if(pre != null){
			//
			while(pre.preNode != AVAILABLE){
				//自旋锁
			}
		}
	}

	public void unlock() {
		QNode node = myNode.get();
		// CAS??????????true???????Ψ????????????У??????preNode???AVAILABLE
		if(!tail.compareAndSet(node, null)){
			node.preNode = AVAILABLE;
		}
	}
	
	public boolean trylock(long time, TimeUnit unit) throws InterruptedException {
		if(Thread.interrupted()){
			throw new InterruptedException();
		}
		boolean isInterrupted = false;
		long startTime = System.currentTimeMillis();
		long duration = TimeUnit.MILLISECONDS.convert(time, unit);
		// ??????tryLock???new???Node????????????????λ???????????????????????????????????????????
		QNode node = new QNode();
		myNode.set(node);
		// ??????λ????
		QNode pre = tail.getAndSet(node);
		// ?????????????????????????????????, pre==AVAILABLE??????????
		if(pre == null || pre == AVAILABLE){
			return true;
		}
		// ???????????preNode????
		while((System.currentTimeMillis() - startTime < duration) && !isInterrupted){
			QNode predPreNode = pre.preNode;
			// ????????????????????????????preNode?????preNode?????
			if(predPreNode == AVAILABLE){
				return true;
			}
			// ??prePreNode ??= null?????????????????????????????????ж????
			// ????prePreNode????????????????????????????
			else if(predPreNode != null){
				pre = predPreNode;
			}
			if(Thread.interrupted()){
				isInterrupted = true;
			}
		}
		
		// ???????interrupted?????????node???????????
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

