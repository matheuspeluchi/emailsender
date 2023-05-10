package com.mpr.emailsender.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;

import com.mpr.emailsender.domain.ProductCustomer;

@Configuration
public class SendMailStepConfig {
  @Bean
  public Step sendMailStep(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      ItemReader<ProductCustomer> productCustomerReader,
      ItemProcessor<ProductCustomer, SimpleMailMessage> productCustomerProcessor,
      ItemWriter<SimpleMailMessage> productCustomerWriter) {
    return new StepBuilder("sendMailStep", jobRepository)
        .<ProductCustomer, SimpleMailMessage>chunk(1, transactionManager)
        .reader(productCustomerReader)
        .processor(productCustomerProcessor)
        .writer(productCustomerWriter)
        .build();

  }
}
