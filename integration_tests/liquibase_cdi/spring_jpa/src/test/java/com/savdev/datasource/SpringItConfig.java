package com.savdev.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.StreamSupport;

@Configuration
@EnableJpaRepositories(basePackages = "com.savdev.datasource") //searches in sub-packages
@PropertySource(SpringItConfig.HIBERNATE_PROPERTIES)
@EnableTransactionManagement
public class SpringItConfig {

  public static final String HIBERNATE_PROPERTIES = "hibernate.properties";

  public static MySQLContainer mysql = new MySQLContainer(
    DockerImageName.parse(MySQLContainer.NAME).withTag("5.7.22"));

  static {
    mysql.start();
  }

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {

    return DataSourceBuilder
      .create()
      .url(mysql.getJdbcUrl())
      .username(mysql.getUsername())
      .password(mysql.getPassword())
      .driverClassName("com.mysql.jdbc.Driver")
      .build();
  }

  @Bean
  public SpringLiquibase springLiquibase(DataSource dataSource) throws SQLException {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDropFirst(true);
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.yml");
    return liquibase;
  }

  @Bean
  @DependsOn("springLiquibase") //to create schema with liquibase before schema validation by hibernate
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("com.savdev.datasource");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    em.setJpaProperties(asProperties(HIBERNATE_PROPERTIES));
    em.afterPropertiesSet();
    return em;
  }

  @Bean
  JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  private Properties asProperties(final String fileName) {
    return StreamSupport.stream(
      ((AbstractEnvironment) env).getPropertySources().spliterator(), false)
      .filter(ps -> ps instanceof ResourcePropertySource)
      .map(ps -> (ResourcePropertySource) ps)
      .filter(rps -> rps.getName().contains(fileName))
      .collect(
        Properties::new,
        (props, rps) -> props.putAll(rps.getSource()),
        Properties::putAll);
  }
}
