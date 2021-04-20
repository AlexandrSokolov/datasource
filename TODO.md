#### Queries
- Spring Data also has the concept of derived queries where the actual SQL query is derived from the method name:
```java
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByTrackingNumber(String trackingNumber);
}
```
- inferred query
  The first option is to create an inferred query:
  `UserEntity findByName(String name);`
  We don’t need to tell Spring Data what to do, since it automatically infers the SQL query from the name of the method name.
- @Query annotaion
  Having said this, it’s good practice to rename such methods to a shorter, more meaningful name 
  and add a @Query annotation to provide a custom JPQL query.
```java
@Query("select u from UserEntity u where u.name = :name")
UserEntity findByNameCustomQuery(@Param("name") String name);
```
Similar to inferred queries, we get a validity check for those JPQL queries for free.

So, should we write tests for custom queries? The unsatisfying answer is that we have to decide for ourselves if the query is complex enough to require a test.

- Native Queries with @Query
```java
@Query(
  value = "select * from user as u where u.name = :name",
  nativeQuery = true)
UserEntity findByNameNativeQuery(@Param("name") String name);
```
It’s important to note that neither Hibernate nor Spring Data validate native queries at startup. Since the query may contain database-specific SQL, there’s no way Spring Data or Hibernate can know what to check for.

So, native queries are prime candidates for integration tests



#### Test containers

- [Testcontainers](https://javadoc.io/static/org.testcontainers/junit-jupiter/1.15.3/org/testcontainers/junit/jupiter/Testcontainers.html)
- [JUnit 5 Quickstart](https://www.testcontainers.org/quickstart/junit_5_quickstart/)
- [Reuse Containers With Testcontainers for Fast Integration Tests](https://rieckpil.de/reuse-containers-with-testcontainers-for-fast-integration-tests/)
- [Write Spring Boot Integration Tests With Testcontainers](https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/)
- [testcontainers releases](https://github.com/testcontainers/testcontainers-java/releases)

#### Testing jdbc
-  [@Autowired private JdbcTemplate jdbcTemplate;](https://reflectoring.io/spring-boot-data-jpa-test/)

  

#### Transactions Management in-depth
JEE transaction:
```
@Resource
private TransactionManager transactionManager;

@Resource
private EJBContext context;
userTransaction  = context.getUserTransaction();

@Resource
private UserTransaction ut;

InitialContext context = new InitialContext();
UserTransaction ut = (UserTransaction) context.lookup("java:comp/UserTransaction");
TransactionManager tm = (TransactionManager) context.lookup("java:/TransactionManager");

JtaTransactionManager jta = new JtaTransactionManager();
jta.setUserTransaction(ut);
jta.setTransactionManager(tm);


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class HelloBean implements HelloBeanLocal {...}

@In 
private TransactionManager txMgr;

@In
private UserTransaction utx;

private XAResource xaRes1;
private XAResource xaRes2;

public void foo() {
   Transaction tx = txMgr.getTransaction();
   utx.begin();
   
   tx.enlistResource(xaRes1);
   tx.enlistResource(xaRes2);

   utx.commit();
}
```

`javax.transaction.TransactionManager`
`javax.transaction.Transaction`
`javax.transaction.Transactional`
`javax.transaction.UserTransaction`

Spring transactions:
`org.springframework.transaction.jta.JtaTransactionManager`
`org.springframework.transaction.TransactionManager`

[javax.transaction.Transactional vs org.springframework.transaction.annotation.Transactional](https://stackoverflow.com/questions/26387399/javax-transaction-transactional-vs-org-springframework-transaction-annotation-tr)

- [Tackling RESOURCE_LOCAL Vs. JTA Under Java EE Umbrella and Payara Server](https://dzone.com/articles/resource-local-vs-jta-transaction-types-and-payara)
- [EJB Transaction Management Example](https://examples.javacodegeeks.com/enterprise-java/ejb3/transactions/ejb-transaction-management-example/)
- [JTA Reference](http://docs.wildfly.org/16/Developer_Guide.html#transactions-in-enterprise-java-applications)
- [Container-Managed Transactions](https://javaee.github.io/tutorial/transactions004.html)
- [EJB Transactions](http://www.mastertheboss.com/javaee/transactions/how-to-receive-transaction-callbacks-for-ejb-and-cdi-beans)
- [Understanding JTA - The Java Transaction API](https://www.progress.com/tutorials/jdbc/understanding-jta)
- [USING TRANSACTIONS IN QUARKUS](https://quarkus.io/guides/transaction)
- [Spring Transaction Management: @Transactional In-Depth](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth)
- [Spring Transactional Management](https://dzone.com/articles/bountyspring-transactional-management)
- [How Does Spring @Transactional Really Work?](https://dzone.com/articles/how-does-spring-transactional)
- [Start with declarative transactions based on @Transactional full annotation, and thoroughly understand the principles of Spring transaction management](https://programmer.ink/think/5cdba57f6174f.html)
- [Guide to Jakarta EE JTA](https://www.baeldung.com/jee-jta)
- [Configuring Spring and JTA without full Java EE](https://spring.io/blog/2011/08/15/configuring-spring-and-jta-without-full-java-ee/)
- [Spring Transaction Management](https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/transaction.html)
- [Java SE JTA TransactionManager](https://stackoverflow.com/questions/777636/what-is-a-good-open-source-java-se-jta-transactionmanager-implementation)
- [TRANSACTION GUIDE](https://access.redhat.com/documentation/en-us/red_hat_fuse/7.0/html/transaction_guide/index)
- [Programmatic Transaction Management in Spring](https://www.baeldung.com/spring-programmatic-transaction-management)
- [Effective Spring Transaction Management](https://dzone.com/articles/spring-transaction-management)
- [Common Transaction Propagation Pitfalls in Spring Framework](https://medium.com/@safa_ertekin/common-transaction-propagation-pitfalls-in-spring-framework-2378ee7d6521)
- [Common Pitfalls of Declarative Transaction Management in Spring](https://blog.staynoob.cn/post/2019/02/common-pitfalls-of-declarative-transaction-management-in-spring/)
- [CDI Integration](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpd.misc.cdi-integration)

#### Using Flyway
- [Using Flyway in IT with Spring @DataJpaTest](https://reflectoring.io/spring-boot-data-jpa-test/)