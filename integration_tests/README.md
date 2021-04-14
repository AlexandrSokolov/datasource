
[Integration testing with the spring framework](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#integration-testing)

## Testing with `@ContextConfiguration`/`@Configuration`

[See](liquibase_cdi/ContextConfiguration)

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

## Transaction management with Spring IT

1. You must declare `@ContextConfiguration` together with `@Transactional`: 

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

2. Transactions logging
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

3. By default, the framework will create and roll back a transaction for each test.

If you want to control transaction management, you have the following options:
- `@Commit` - direct replacement for `@Rollback(false)`
- `@Rollback` (at the class and method levels, old versions only on test methods)
- [Deprecated and already removed](https://docs.spring.io/spring-framework/docs/4.3.14.RELEASE/javadoc-api/org/springframework/test/context/transaction/TransactionConfiguration.html): 
  `@TransactionConfiguration` (for class-level transaction configuration) 

4. Run test methods **outside** the transactional context. 
   
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
5. Changing Transaction the Isolation Level
`@Transactional(isolation = Isolation.SERIALIZABLE)`
   
6. Transaction Rollback
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

