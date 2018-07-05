package study.thread.taskcompelete;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import study.thread.CommonControlThread;

public class TaskCompelete {
	
	public static void main(String[] args) throws InterruptedException {
		Callable<Boolean> ca = new CommonControlThread();
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<Boolean> future =exec.submit(ca);
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < 3; i ++){
			Thread t = new Thread(new ConsumerThread(), "consumer_" + i);
			threadList.add(t);
			t.start();
		}
		while(!future.isDone()){
			Thread.sleep(100);
		}
		Boolean isdone = new Boolean(false);
		try {
			isdone = future.get();
			if (isdone)
			for (Thread t : threadList)
				t.interrupt();
			else System.out.println("Error");
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally{
			exec.shutdown();
		}
	}
}
