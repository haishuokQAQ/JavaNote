package study.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class InterruptedDeadLockThread extends Thread{
	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	
	private int mode = 0;

	public InterruptedDeadLockThread(int mode) {
		this.mode = mode;
	}
	
	@Override
	public void run() {
		switch(mode){
		case 0 : 
				lockFirst();
		break;
		default :
				lockSecond();
		}
	}
	
	private void lockFirst(){
		try {
			lock1.lockInterruptibly();
			Thread.sleep(2000);
			lock2.lockInterruptibly();
		} catch (InterruptedException e) {
			System.out.println("Thread " + mode + " interrupted");
		}finally{
			if (lock1.isHeldByCurrentThread()) lock1.unlock();
			if (lock2.isHeldByCurrentThread()) lock2.unlock();
		}
	}
	
	private void lockSecond(){
		try{
			lock2.lockInterruptibly();
			Thread.sleep(2000);
			lock1.lockInterruptibly();
		} catch (InterruptedException e) {
			System.out.println("Thread " + mode + " interrupted");
		}finally{
			if (lock1.isHeldByCurrentThread()) lock1.unlock();
			if (lock2.isHeldByCurrentThread()) lock2.unlock();
		}
	
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		InterruptedDeadLockThread t1 = new InterruptedDeadLockThread(0);
		InterruptedDeadLockThread t2 = new InterruptedDeadLockThread(1);
		t1.start();
		t2.start();
		Thread.sleep(3000);
		t2.interrupt();
	}
	
}
