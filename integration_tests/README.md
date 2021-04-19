
**Spring documentation:**
- [Integration testing with the spring framework](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#integration-testing)
- [Test Auto-configuration Annotations](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-test-auto-configuration.html)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

**Features:**
- [Datasource project with Liquibase/Spring for CDI/JEE environment](liquibase_cdi/spring_jpa)
- [Testing with `@DataJpaTest`](#1-testing-with-datajpatest)
- [Populating test data](#2-populating-test-data)
- [Transaction management](#3-transaction-management-with-spring-it)
- [(deprecated) Testing with `@ContextConfiguration`/`@Configuration`](#testing-with-contextconfigurationconfiguration)

### 1. Testing with `@DataJpaTest`

Auto-configured Data JPA Tests.

`@DataJpaTest` provides some standard setup needed for testing the persistence layer:
- configuring H2, an in-memory database
- setting Hibernate, Spring Data, and the DataSource
- performing an @EntityScan
- turning on SQL logging

Note that by default the application context is shared between all test methods 
within all `@DataJpaTest`-annotated test classes.

Cause default behaviour of `@DataJpaTest` is not suitable and additionally you must configure:
- Use a real database with a container, instead of in-memory embedded database
- Apply schema with Liquibase/Flyweight, with Hibernate you only validate it

Then it makes sense to extract a base class with all required annotations and configurations and inherit it in other ITs.

See [BaseItInitializer](liquibase_cdi/DataJpaTest/src/test/java/com/savdev/datasource/BaseItInitializer.java)

##### 1.1. Test database choice

1.1.1 By default it uses in-memory embedded database (like H2 database)
1.1.2 To overwrite it and use a real database:

- set:
`@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)`
- use a real database container:
```java
  @ClassRule //Not necessary to start it
  public static MySQLContainer<?> mysql = new MySQLContainer<>(
    DockerImageName
      .parse(MySQLContainer.NAME)
      .withTag("5.7.22"));
```
- Configure the application context initializer with:
```java
@ContextConfiguration(initializers = { BaseItInitializer.Initializer.class })
```
See [BaseItInitializer](liquibase_cdi/DataJpaTest/src/test/java/com/savdev/datasource/BaseItInitializer.java)

##### 1.2. Schema creation:

1.2.1 By default, `@DataJpaTest` will configure Hibernate to create the database schema for us automatically. 
The property responsible for this is `spring.jpa.hibernate.ddl-auto`, 
which Spring Boot sets to `create-drop` by default, 
meaning that the schema is created before running the tests and dropped after the tests have executed.

1.2.2 Using [Liquibase](https://www.liquibase.org/)
Activate Liquibase by simply adding the dependency:
`compile('org.liquibase:liquibase-core')`
Set `ddl-auto` to `validate`:
```java
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
```
Note: you might get an errors:
`Caused by: java.io.FileNotFoundException: class path resource [db/changelog/db.changelog-master.yaml] cannot be resolved to URL because it does not exist`
To solve it you must overwrite the `SpringLiquibase`:
```java
    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {
      SpringLiquibase liquibase = new SpringLiquibase();
      liquibase.setDropFirst(true);
      liquibase.setDataSource(dataSource);
      liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.yml");
      return liquibase;
    }
```
`Failed to load driver class com.mysql.cj.jdbc.Driver in either of HikariConfig class loader or Thread context classloader`
For MySql set the driver name explicitly:
`spring.datasource.driver-class-name=com.mysql.jdbc.Driver`

1.2.3 Using [Flyway](https://flywaydb.org/)
Similar to `Liquibase` with the only exception add dependency on:
`compile('org.flywaydb:flyway-core')`

#### 2. Populating test data

#### 3. Transaction management with Spring IT

3.1. You must declare `@ContextConfiguration` together with `@Transactional`:

Note: The `@DataJpaTest` meta-annotation contains the `@Transactional` annotation.

```java
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) //alternative is SpringJUnit4ClassRunner.class
@ContextConfiguration(classes = { SpringItConfig.class })
@Transactional
public class ExampleEntityIT {
}
```
The `SpringItConfig` class must be annotated with `@EnableTransactionManagement`:
```java
@Configuration
@EnableJpaRepositories(basePackages = "com.savdev.datasource") //searches in sub-packages
@EnableTransactionManagement
public class SpringItConfig { }
```
Note:
Instead of `@Configuration` annotation you can use `@TestConfiguration` for tests.
`@TestConfiguration` is specialized form of `@Configuration` that can be used for a test.
In spring boot, any beans configured in a top-level class annotated with `@TestConfiguration`
will not be picked up via component scanning.

3.2. Transactions logging
   `logging.level.org.springframework.transaction.interceptor=TRACE`

```yaml
logging:
   level:
      org.springframework.orm.jpa: DEBUG
      org.springframework.transaction: DEBUG
```
```java
TransactionSynchronizationManager.isActualTransactionActive();
TransactionAspectSupport.currentTransactionStatus();
```

3.3. By default, the framework will create and roll back a transaction for each test.

If you want to control transaction management, you have the following options:
- `@Commit` - direct replacement for `@Rollback(false)`
- `@Rollback` (at the class and method levels, old versions only on test methods)
- [Deprecated and already removed](https://docs.spring.io/spring-framework/docs/4.3.14.RELEASE/javadoc-api/org/springframework/test/context/transaction/TransactionConfiguration.html):
  `@TransactionConfiguration` (for class-level transaction configuration)

3.4. Run test methods **outside** the transactional context.

For example, to verify the initial database state prior to execution of your test
or to verify expected transactional commit behavior after test execution
(if the test was configured not to roll back the transaction).
You have the following options:
- `@BeforeTransaction`
- `@AfterTransaction`
  Note: JUnit's `@Before` and `@After` are executed **within** a transaction.

```java
@RunWith(SpringRunner.class)
@ContextConfiguration
@Commit
@Transactional
public class FictitiousTransactionalTest {

    @BeforeTransaction
    public void verifyInitialDatabaseState() {
        // logic to verify the initial state before a transaction is started
    }

    @Before
    public void setUpTestDataWithinTransaction() {
        // set up test data within the transaction
    }

    @Test
    // overrides the class-level `@Commit` setting
    @Rollback(true)
    public void modifyDatabaseWithinTransaction() {
        // logic which uses the test data and modifies database state
    }

    @After
    public void tearDownWithinTransaction() {
        // execute "tear down" logic within the transaction
    }

    @AfterTransaction
    public void verifyFinalDatabaseState() {
        // logic to verify the final state after transaction has rolled back
    }
}
```
3.5. Changing Transaction the Isolation Level
   `@Transactional(isolation = Isolation.SERIALIZABLE)`

3.6. Transaction Rollback
```java
@Transactional(rollbackFor = { SQLException.class })
public void createCourseDeclarativeWithCheckedException(Course course) throws SQLException {
    courseDao.create(course);
    throw new SQLException("Throwing exception for demoing rollback");
}
```
```java
@Transactional(noRollbackFor = { SQLException.class })
public void createCourseDeclarativeWithNoRollBack(Course course) throws SQLException {
    courseDao.create(course);
    throw new SQLException("Throwing exception for demoing rollback");
}
```
```java
public void createCourseDefaultRatingProgramatic(Course course) {
    try {
       courseDao.create(course);
    } catch (Exception e) {
       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
```

#### Testing with `@ContextConfiguration`/`@Configuration`

**Note:** Quite old solution, too many explicit configurations, prefer to use IT with `@DataJpaTest`.

[See code example](liquibase_cdi/ContextConfiguration)

Used to determine how to load and configure an `ApplicationContext` for integration tests.

Supported features:
- Loading of Spring ApplicationContexts and caching of those contexts between test execution
- Dependency Injection of test fixture instances
- Transaction management appropriate to integration testing

Can be used to define ApplicationContexts:
- via annotated classes `@ContextConfiguration(classes = { SpringItConfig.class })`
- via the application context resource locations: `@ContextConfiguration(locations="example/test-context.xml")`
- to set the ContextLoader strategy to use for loading the context: `@ContextConfiguration(loader=CustomContextLoader.class)`
- to set the ContextIntializer: `@ContextConfiguration(initializers = CustomContextIntializer.class)`

Test example:
```java
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
...
@RunWith(SpringRunner.class) //alternative is SpringJUnit4ClassRunner.class
@ContextConfiguration(classes = { SpringItConfig.class })
@Transactional
public class ExampleEntityIT {
  ...
}
```
The test application context configuration example:
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
...
@Configuration
@EnableJpaRepositories(basePackages = "com.savdev.datasource") //searches in sub-packages
@PropertySource(SpringItConfig.HIBERNATE_PROPERTIES)
@EnableTransactionManagement
public class SpringItConfig {
  ...
}
```

