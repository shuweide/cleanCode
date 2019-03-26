package idv.code.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import idv.code.CallablePlay;
import idv.code.RunnablePlay;

public class ExecutorPlay {
	
	ExecutorService executorService = null;
	
	enum ThreadPoolType{
		CACHE, FIXED, SINGLE, SCHEDULED;
	}
	enum ThreadImpType{
		RUNNABLE, CALLABLE;
	}
	
	public void threadPlay(ThreadPoolType threadPoolType, ThreadImpType threadImpType) throws Exception{
		switch(threadPoolType){
		case CACHE:
			//reuse thread idle 60s
			executorService = Executors.newCachedThreadPool();
			break;
		case FIXED:
			//reuse thread max 10 threads idle 0s
			executorService = Executors.newFixedThreadPool(10);
			break;
		case SINGLE:
			//one thread
			executorService = Executors.newSingleThreadExecutor();
			break;
		case SCHEDULED:
			executorService = Executors.newScheduledThreadPool(10);
			break;
		default:
			return;
		}	
		
		if(threadImpType == ThreadImpType.RUNNABLE) 
			useRunnable();
		if(threadImpType == ThreadImpType.CALLABLE)
			useCallable();
		
		executorService.shutdown();
	}
	
	private void useRunnable(){
		for(int i=0; i<20; i++){
			executorService.execute(new RunnablePlay());
			System.out.println(i);
		}		
	}
	
	private void useCallable() throws Exception{	
		List<Future<String>> results = new ArrayList<>();		
		for(int i=0; i<5; i++){
			Future<String> future = executorService.submit(new CallablePlay(i));
			results.add(future);
		}
		
		for(Future<String> future: results){
			try{
				while(!future.isDone()){
				}
				System.out.println(future.get());
			}finally {
				executorService.shutdown();
			}
		}
	}	
}
