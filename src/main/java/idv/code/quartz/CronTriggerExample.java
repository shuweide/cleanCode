package idv.code.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerExample {
    public static void main(String[] args) throws Exception {
        // Quartz 1.6.3
        // JobDetail job = new JobDetail();
        // job.setName("dummyJobName");
        // job.setJobClass(HelloJob.class);
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();

        // Quartz 1.6.3
        // CronTrigger trigger = new CronTrigger();
        // trigger.setName("dummyTriggerName");
        // trigger.setCronExpression("0/5 * * * * ?");

        Trigger trigger = new CronTrigger().get();

        // schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }
}
