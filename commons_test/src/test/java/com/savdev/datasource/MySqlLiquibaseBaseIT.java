package com.savdev.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = { MySqlLiquibaseBaseIT.Initializer.class })
@Testcontainers
public class MySqlLiquibaseBaseIT {

  @Container
  public static MySQLContainer<?> mysql = new MySQLContainer<>(
    DockerImageName
      .parse(MySQLContainer.NAME)
      .withTag("5.7.22"));

  @Configuration
  @EnableJpaRepositories
  @EntityScan
  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
        "spring.datasource.url=" + mysql.getJdbcUrl(),
        "spring.datasource.username=" + mysql.getUsername(),
        "spring.datasource.password=" + mysql.getPassword(),
        "spring.datasource.driver-class-name=" + mysql.getDriverClassName())
        .applyTo(configurableApplicationContext.getEnvironment());
    }

    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {
      SpringLiquibase liquibase = new SpringLiquibase();
      liquibase.setDropFirst(true);
      liquibase.setDataSource(dataSource);
      liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.yml");
      return liquibase;
    }
  }
}
