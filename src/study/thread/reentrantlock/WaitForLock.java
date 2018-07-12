package study.thread.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class WaitForLock extends Thread{
	private static ReentrantLock lock = new ReentrantLock();
	
	private int id;
	
	
	
	public WaitForLock(int id) {
		super();
		this.id = id;
	}



	@Override
	public void run() {
		try {
			if (lock.tryLock(5, TimeUnit.SECONDS)) {
				System.out.println(id + " has get the lock.");
				Thread.sleep(6000);
			}else {
				System.out.println(id + " does'n get the lock.Give up the lock.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if (lock.isHeldByCurrentThread()) lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		WaitForLock w1 = new WaitForLock(1);
		WaitForLock w2 = new WaitForLock(2);
		w1.start();
		w2.start();
	}

}
