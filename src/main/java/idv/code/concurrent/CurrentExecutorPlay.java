package idv.code.concurrent;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class CurrentExecutorPlay {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public void beepForAnHour() throws InterruptedException {
		final Runnable beeper = () -> System.out.println("beep-" + new Date(System.currentTimeMillis()));
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 1, SECONDS);
		scheduler.schedule(() -> beeperHandle.cancel(true), 60, SECONDS);
//		scheduler.awaitTermination(10, SECONDS);
	}
	
	public static void main(String[] args) throws InterruptedException{
	    CurrentExecutorPlay play = new CurrentExecutorPlay();
	    play.beepForAnHour();
	}
}
