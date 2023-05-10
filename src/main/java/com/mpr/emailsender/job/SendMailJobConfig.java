package com.mpr.emailsender.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendMailJobConfig {

  @Bean
  public Job sendMailJob(JobRepository jobRepository, Step step) {
    return new JobBuilder("SendMailJob", jobRepository)
        .start(step)
        .incrementer(new RunIdIncrementer())
        .build();

  }
}
