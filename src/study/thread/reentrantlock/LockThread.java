package study.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 单个锁的例子
 * @author haishuo_k
 *
 */
public class LockThread extends Thread{
	private ReentrantLock lock ;
	private int id;

	public LockThread(ReentrantLock lock, int id) {
		this.lock = lock;
		this.id = id;
	}
	
	@Override
	public void run() {
		lock.lock();
		System.out.println("Thread " + id + " is working");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread " + id + " unlock");
		lock.unlock();
	}
	
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		LockThread t1 = new LockThread(lock, 1);
		LockThread t2 = new LockThread(lock, 2);
		t1.start();
		t2.start();
	}
}
