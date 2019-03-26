package idv.code.concurrent;

import org.junit.Test;

import idv.code.concurrent.ExecutorPlay;
import idv.code.concurrent.ExecutorPlay.ThreadImpType;
import idv.code.concurrent.ExecutorPlay.ThreadPoolType;

public class ExecutorPlayTest {
	
	@Test
	public void testExecutorCache() throws Exception{
		ExecutorPlay executorPlay = new ExecutorPlay();
		executorPlay.threadPlay(ThreadPoolType.CACHE, ThreadImpType.RUNNABLE);
		executorPlay.threadPlay(ThreadPoolType.CACHE, ThreadImpType.CALLABLE);
	}
	
	@Test
	public void testExecutorFixed() throws Exception{
		ExecutorPlay executorPlay = new ExecutorPlay();
		executorPlay.threadPlay(ThreadPoolType.FIXED, ThreadImpType.RUNNABLE);
		executorPlay.threadPlay(ThreadPoolType.FIXED, ThreadImpType.CALLABLE);
	}
	
	@Test
	public void testExecutorSingle() throws Exception{
		ExecutorPlay executorPlay = new ExecutorPlay();
		executorPlay.threadPlay(ThreadPoolType.SINGLE, ThreadImpType.RUNNABLE);
		executorPlay.threadPlay(ThreadPoolType.SINGLE, ThreadImpType.CALLABLE);
	}
}
