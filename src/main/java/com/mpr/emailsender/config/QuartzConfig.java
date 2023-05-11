package com.mpr.emailsender.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mpr.emailsender.job.SendClientPromotionScheduleJob;

@Configuration
public class QuartzConfig {

  @Bean
  public JobDetail quartzJobDetail() {
    return JobBuilder.newJob(SendClientPromotionScheduleJob.class)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger jobTrigger() {
    SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInMinutes(1)
        .withRepeatCount(2);

    return TriggerBuilder.newTrigger().forJob(quartzJobDetail())
        .withSchedule(scheduleBuilder).build();
  }

}