
- [Usage](#usage)
- [Requirements](#requirements)
- [Logging](#logging)
- [Docker container configuration](#mysql-container-configuration)

#### Usage

To use in maven module, add the following dependency:
```xml
<dependencies>
  <dependency>
    <groupId>com.savdev.datasource</groupId>
    <artifactId>datasource-commons-test</artifactId>
    <version>${project.version}</version>
    <classifier>tests</classifier>
    <type>test-jar</type>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
  </dependency>
</dependencies>
```
Note: test dependencies are not inherited from `datasource-commons-test` module.

Extend your test class from `MySqlLiquibaseBaseIT`:
```java
public class ExampleEntityIT extends MySqlLiquibaseBaseIT {}
```

#### Requirements

In the parent `pom.xml` you must configure `maven-jar-plugin` with `test-jar` goal:
```xml
  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-jar-plugin</artifactId>
          <version>${maven-jar-plugin.version}</version>
          <executions>
            <execution>
              <goals>
                <goal>test-jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
```

#### Logging

The following logging levels are supported:
- enabled with `@DataJpaTest`, you see how container is created, liquibase logging, JPA logging
- `MySQLContainer` is created with `withLogConsumer` invocation. It includes docker container default logs.
- sql statements logs. Real sql statements which are sent to the database server. 
  You can find them in `~/var/log/it/mysql` folder. It is bound via `withFileSystemBind`:
```java
  @Container
  public static MySQLContainer<?> mysql = new MySQLContainer<>(
    DockerImageName
      .parse(MySQLContainer.NAME)
      ...
      .withFileSystemBind(DOCKER_HOST_LOGS_PATH, DOCKER_MYSQL_LOGS_PATH, BindMode.READ_WRITE);
```
  
#### MySql Container configuration

[`my.test.cnf`](src/test/resources/my.test.cnf) is copied to the MySql container via `withCopyFileToContainer`:
```java
  @Container
  public static MySQLContainer<?> mysql = new MySQLContainer<>(
    DockerImageName
      .parse(MySQLContainer.NAME)
      ...
      .withCopyFileToContainer(MountableFile
        .forClasspathResource(MY_CONF), DOCKER_CONF_PATH)
```

