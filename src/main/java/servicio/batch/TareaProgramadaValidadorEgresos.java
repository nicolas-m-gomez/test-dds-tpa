package servicio.batch;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TareaProgramadaValidadorEgresos
{
    public static void main( String[] args ) throws Exception
    {
    	JobDetail job = JobBuilder.newJob(batchValidacionEgresos.class)
		.withIdentity("batchValidacionEgresos.class", "group1").build();

    	Trigger trigger = TriggerBuilder
		.newTrigger()
		.withIdentity("TriggerValidadorEgresos", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
		.build();

    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	scheduler.start();
    	scheduler.scheduleJob(job, trigger);

    }
}