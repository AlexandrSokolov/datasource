package com.savdev.datasource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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

@Configuration
@EnableJpaRepositories(basePackages = "com.savdev.datasource") //searches in sub-packages
@PropertySource("hibernate.properties")
@EnableTransactionManagement
public class SpringItConfig {

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
      .driverClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource")
      //.driverClassName("com.mysql.jdbc.Driver")
      //.driverClassName("com.mysql.cj.jdbc.MysqlDataSource")
      //.driverClassName("com.mysql.cj.jdbc.Driver")
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
    em.setPackagesToScan(new String[] { "com.savdev.datasource.entities", "com.savdev.datasource.repositories" });
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    em.setJpaProperties(hibernateProperties());
    em.afterPropertiesSet();
    return em;
  }

  @Bean
  JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  private Properties hibernateProperties() {

    final Properties hibernateProperties = new Properties();

    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
    hibernateProperties.setProperty("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
    hibernateProperties.setProperty("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings"));

    return hibernateProperties;
  }
}
