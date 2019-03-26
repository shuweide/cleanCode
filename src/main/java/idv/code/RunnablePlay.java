package idv.code;

public class RunnablePlay implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "Thread Run!");
	}

}
