package idv.code;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallablePlay implements Callable<String> {
	
	private int id;

	public CallablePlay(int id) {
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(id);
		System.out.println("call()" + Thread.currentThread().getName());
		return "call()" + id + "    " + Thread.currentThread().getName();
	}
}
