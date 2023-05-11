package com.mpr.emailsender.processor;

import java.text.NumberFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.mpr.emailsender.domain.ProductCustomer;

@Component
public class ProductCustomerProcessorConfig implements ItemProcessor<ProductCustomer, SimpleMailMessage> {

  @Override
  @Nullable
  public SimpleMailMessage process(@NonNull ProductCustomer ps) throws Exception {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setFrom("example@no-reply.com");
    email.setTo(ps.getCustomer().getEmail());
    email.setSubject("Unmissable product offer");
    email.setText(textPromotionGenerate(ps));

    return email;
  }

  private String textPromotionGenerate(ProductCustomer ps) {
    StringBuilder writer = new StringBuilder();
    writer.append(String.format("Hello, %s!%n%n", ps.getCustomer().getName()));
    writer.append("This offer may be of interest to you! \n\n");
    writer.append(String.format("%s - %s%n%n", ps.getProduct().getName(), ps.getProduct().getDescription()));
    writer
        .append(String.format("For only: %s!", NumberFormat.getCurrencyInstance().format(ps.getProduct().getPrice())));

    return writer.toString();
  }

}