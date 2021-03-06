package com.savdev.datasource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = { MySqlLiquibaseBaseIT.Initializer.class })
@Testcontainers
public class MySqlLiquibaseBaseIT {

  private static final String MY_CONF = "my.test.cnf";
  public static final String DOCKER_CONF_PATH = "/etc/mysql/conf.d/my.test.cnf";
  public static final String DOCKER_HOST_LOGS_PATH = FileUtils.getUserDirectory() + "/var/log/it/mysql";
  public static final String DOCKER_MYSQL_LOGS_PATH = "/var/log/mysql";

  private static final Logger logger = LoggerFactory.getLogger(MySqlLiquibaseBaseIT.class.getName());
  private static final Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger).withSeparateOutputStreams();

  static { // `@BeforeAll` cannot be used. It must be triggered, before container is created!
    try {
      FileUtils.deleteQuietly(Paths.get(DOCKER_HOST_LOGS_PATH).toFile());
      Path path = Files.createDirectories(Paths.get(DOCKER_HOST_LOGS_PATH));
      applyPermissions(path);
      Path logPath = Files.createFile(Paths.get(path + File.separator + "mysqld.log"));
      applyPermissions(logPath);
      Path errorPath = Files.createFile(Paths.get(path + File.separator + "mysqld.error.log"));
      applyPermissions(errorPath);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Container
  public static MySQLContainer<?> mysql = new MySQLContainer<>(
    DockerImageName
      .parse(MySQLContainer.NAME)
      .withTag("5.7.22"))
      .withLogConsumer(logConsumer)
      .withCopyFileToContainer(MountableFile
        .forClasspathResource(MY_CONF), DOCKER_CONF_PATH)
      .withFileSystemBind(DOCKER_HOST_LOGS_PATH, DOCKER_MYSQL_LOGS_PATH, BindMode.READ_WRITE);

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

  private static void applyPermissions(final Path path) {
    path.toFile().setReadable(true, false);
    path.toFile().setWritable(true, false);
  }
}
