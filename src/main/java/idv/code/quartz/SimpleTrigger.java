package idv.code.quartz;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class SimpleTrigger {

    public Trigger get() {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
        return trigger;
    }
}
