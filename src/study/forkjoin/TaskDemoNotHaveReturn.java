package study.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;

public class TaskDemoNotHaveReturn extends RecursiveAction {

	private static AtomicLong result = new AtomicLong();
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -237671939776428626L;

	
	private long start;
	
	private long end;
	
	private final static int THREHOLD = 1000;
	
	
	
	public TaskDemoNotHaveReturn(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}
	@Override
	protected void compute() {
		if (THREHOLD > (end - start)) {
			for (long i = start ; i <= end; i++) {
				result.addAndGet(i);
			}
		}else{
			TaskDemoHaveReturn left = new TaskDemoHaveReturn(start, (end + start) / 2 - 1);
			TaskDemoHaveReturn right = new TaskDemoHaveReturn((end + start) /2, end);
			left.fork();
			right.fork();
			long resultLeft = left.join();
			long resultRight = right.join();
			result.addAndGet(resultLeft + resultRight);
		}
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool fjp = new ForkJoinPool();
		TaskDemoNotHaveReturn demo = new TaskDemoNotHaveReturn(1, 10000);
		Future<Void> future = fjp.submit(demo);
		future.get();
		System.out.println(result.get());
	}
}
