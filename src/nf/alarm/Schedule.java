/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.alarm;

import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author ACER
 */
public class Schedule implements Runnable {
    /*
     *  Class ini menangani Scheduler yang menjalankan JobDetail 
     *  Juga Mememriksa Database Setiap Menit Untuk pengingat
     */
    @Override
    public void run() {
        try {
            // Membuat scheduler
            Scheduler schedule = StdSchedulerFactory.getDefaultScheduler();
            //menjalankan Scheduler
            schedule.start();
            // Membuat informasi Job pada Job class
            JobDetail job = newJob(Timer.class)
            .withIdentity("CronTrigger")
            .build();
            // Membuat Trigger 60 detik
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("Trigger1")
            .startNow()
            .withSchedule(simpleSchedule().withIntervalInSeconds(60).repeatForever())
            .build();
             // Menjadwalkan Job Menggunakan trigger
            schedule.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            System.err.println(se);
        }
    }

}