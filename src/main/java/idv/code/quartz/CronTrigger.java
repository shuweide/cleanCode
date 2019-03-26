package idv.code.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class CronTrigger {

    public Trigger get() {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
        return trigger;
    }
}
