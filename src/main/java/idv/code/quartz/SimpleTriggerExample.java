package idv.code.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTriggerExample {
    public static void main(String[] args) throws SchedulerException {
        // Quartz 1.6.3
        // JobDetail job = new JobDetail();
        // job.setName("dummyJobName");
        // job.setJobClass(HelloJob.class);

        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();

        // Quartz 1.6.3
        // SimpleTrigger trigger = new SimpleTrigger();
        // trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
        // trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        // trigger.setRepeatInterval(30000);

        // Trigger the job to run on the next round minute
        Trigger trigger = new SimpleTrigger().get();

        // schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
