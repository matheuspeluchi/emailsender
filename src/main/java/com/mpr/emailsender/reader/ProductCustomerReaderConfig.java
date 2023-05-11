package com.mpr.emailsender.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.mpr.emailsender.domain.Customer;
import com.mpr.emailsender.domain.Product;
import com.mpr.emailsender.domain.ProductCustomer;

@Configuration
public class ProductCustomerReaderConfig {

  @Bean
  public JdbcCursorItemReader<ProductCustomer> productCustomerReader(
      @Qualifier("appDataSource") DataSource dataSource) {
    return new JdbcCursorItemReaderBuilder<ProductCustomer>()
        .name("productCustomerReader")
        .dataSource(dataSource)
        .sql(
            "select c.id  as  customerId, c.name, c.email, p.id as productId, p.name, p.description, p.price " +
                "from product_customer pc " +
                "join customer c on (customer = c.id) " +
                "join product p on (product = p.id)")
        .rowMapper(rowMapper())
        .build();
  }

  private RowMapper<ProductCustomer> rowMapper() {
    return new RowMapper<ProductCustomer>() {

      @Override
      @Nullable
      public ProductCustomer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("customerId"));
        customer.setEmail(rs.getString("email"));
        customer.setName(rs.getString("name"));

        Product product = new Product();
        product.setId(rs.getInt("productId"));
        product.setDescription(rs.getString("description"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));

        ProductCustomer productCustomer = new ProductCustomer();
        productCustomer.setCustomer(customer);
        productCustomer.setProduct(product);

        return productCustomer;
      }

    };
  }

}