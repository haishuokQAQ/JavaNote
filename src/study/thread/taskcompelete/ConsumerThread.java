package study.thread.taskcompelete;

public class ConsumerThread extends Thread{
	@Override
	public void run() {
		System.out.println(this.getName() + " start");
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println(this.getName() + " has been stopped");
				break;
			}
		}
	}
}
