package study.thread;

import java.util.concurrent.Callable;

public class CommonControlThread implements Callable<Boolean>{

	@Override
	public Boolean call() throws Exception {
		try{
			Thread.sleep(10000);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
