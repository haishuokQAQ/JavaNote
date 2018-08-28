package study.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class TaskDemoHaveReturn extends RecursiveTask<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8357122362342645377L;

	private long start;
	
	private long end;
	
	private final static int THREHOLD = 1000;
	
	
	public TaskDemoHaveReturn(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}




	@Override
	protected Long compute() {
		long result = 0;
		if (THREHOLD > (end - start)) {
			for (long i = start ; i <= end; i++) {
				result += i;
			}
		}else{
			TaskDemoHaveReturn left = new TaskDemoHaveReturn(start, (end + start) / 2 - 1);
			TaskDemoHaveReturn right = new TaskDemoHaveReturn((end + start) /2, end);
			left.fork();
			right.fork();
			long resultLeft = left.join();
			long resultRight = right.join();
			result = resultLeft + resultRight;
		}
		
		return result;
	}


	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool fjp = new ForkJoinPool();
		TaskDemoHaveReturn demo = new TaskDemoHaveReturn(1, 10000);
		Future<Long> ft = fjp.submit(demo);
		System.out.println(ft.get());
	}
	
}
