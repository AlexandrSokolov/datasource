#### Queries
- [JPQL – How to Define Queries in JPA and Hibernate](https://thorben-janssen.com/jpql/)
- [Nativ queries](https://thorben-janssen.com/jpa-native-queries/)
- [spring queries](https://thorben-janssen.com/spring-data-jpa-query-annotation/)
- [Types of JPA Queries](https://www.baeldung.com/jpa-queries)
- [Spring @Query JPQL](https://www.baeldung.com/spring-data-jpa-query)
  [query parameters](https://www.baeldung.com/jpa-query-parameters)
- [IS NULL](https://www.baeldung.com/spring-data-jpa-null-parameters)
- [IN](https://www.baeldung.com/jpa-criteria-api-in-expressions)
- [Spring @Query Nativ](https://www.baeldung.com/spring-data-jpa-query)
- Optional in results
- Collections in parameters
- [LIKE Queries in Spring JPA Repositories](https://www.baeldung.com/spring-jpa-like-queries)
- [Pagination](https://www.baeldung.com/spring-data-jpa-query)
  [pagination](https://www.baeldung.com/jpa-pagination)
- [CrudRepository, JpaRepository, and PagingAndSortingRepository](https://www.baeldung.com/spring-data-repositories)
- [Modifyting with queries](https://www.baeldung.com/spring-data-jpa-query)
  updates and inserts
  [insert](https://www.baeldung.com/jpa-insert)
- [Dynamic queries (JPA Criteria API)](https://www.baeldung.com/spring-data-jpa-query)
  [Combining JPA And/Or Criteria Predicates](https://www.baeldung.com/jpa-and-or-criteria-predicates)
  [Criteria](https://www.baeldung.com/spring-data-criteria-queries)
- [Extending the Existing Repository](https://www.baeldung.com/spring-data-jpa-query)
  [Spring Data Composable Repositories](https://www.baeldung.com/spring-data-composable-repositories)
- [Constructing a JPA Query Between Unrelated Entities](https://www.baeldung.com/jpa-query-unrelated-entities)
- [Queries with Aggregation Functions](https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions)
- [querydsl](https://www.baeldung.com/querydsl-with-jpa-tutorial)
  [querydsl](https://www.baeldung.com/rest-api-search-language-spring-data-querydsl)
- [JPA error](https://www.baeldung.com/jpa-error-java-lang-string-cannot-be-cast)
- [SqlResultSetMapping](https://www.baeldung.com/jpa-sql-resultset-mapping)
- [Batch Insert/Update with Hibernate/JPA](https://www.baeldung.com/jpa-hibernate-batch-insert-update)
- [Spring Data JPA Repository Populators](https://www.baeldung.com/spring-data-jpa-repository-populators)
- [dd](https://www.baeldung.com/spring-data-jpa-method-in-all-repositories)
- [kk](https://www.baeldung.com/rest-api-search-language-spring-data-specifications)
- [kk](https://www.baeldung.com/rest-search-language-spring-jpa-criteria)
- [jpa query](https://www.objectdb.com/java/jpa/query)
- [jpa](https://thorben-janssen.com/category/jpa/)
- [repository-pattern](https://thorben-janssen.com/implementing-the-repository-pattern-with-jpa-and-hibernate/)
- [thorben-janssen](https://thorben-janssen.com/tutorials/)
- [Spring data](https://www.baeldung.com/category/persistence/spring-persistence/spring-data/)
- [hibernate](https://vladmihalcea.com/tutorials/hibernate/)
- [jpa 2.1](https://thorben-janssen.com/jpa-21-overview/)
  
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

#### How to run only IT (all, for all modules, etc)

```xml

<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-failsafe-plugin</artifactId>
      <version>3.0.0-M4</version>
      <executions>
        <execution>
          <goals>
            <goal>integration-test</goal>
            <goal>verify</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

#### Logging

jpa/sql logging
logging in containers (save logs on the permanennt folder)

#### Test mysql containers configuration

configuration mysql for the container
connecting to the running container, without starting/stopping it in test

#### Spring's `@DynamicPropertySource`

Basic application integration test with JUnit 5 and Spring Boot > 2.2.6
vs:
Integration test with JUnit 5 and Spring Boot < 2.2.6

https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/
https://rieckpil.de/test-your-spring-boot-jpa-persistence-layer-with-datajpatest/

#### Schema DDL

tables creation, constraints module add during table creation, droppging, adding later
jpa entity with all constraints inlcuded
table adoption - adding, removing new fields
renaming fields
adopting field type, when it is possible

#### date time default triggers 
See comments:
https://stackoverflow.com/questions/168736/how-do-you-set-a-default-value-for-a-mysql-datetime-column

#### Schema DDL entity with all types

https://www.baeldung.com/jpa-java-time

table with all sql types AND enums and datetime formats
one entity per each set of types (entity with date-times)
[JPA 2.2 Support for Java 8 Date/Time Types](https://www.baeldung.com/jpa-java-time)
https://www.baeldung.com/spring-data-jpa-query-by-date

[correct tests with dates](queries/spring_jpa/parameters_query_on_repository/src/test/java/com/savdev/datasource/ParametersJpqlQueryRepositoryTest.java)

#### Id generation
[uuid2] -> [org.hibernate.id.UUIDGenerator]
[guid] -> [org.hibernate.id.GUIDGenerator]
[uuid] -> [org.hibernate.id.UUIDHexGenerator]
[uuid.hex] -> [org.hibernate.id.UUIDHexGenerator]
[assigned] -> [org.hibernate.id.Assigned]
[identity] -> [org.hibernate.id.IdentityGenerator]
[select] -> [org.hibernate.id.SelectGenerator]
[sequence] -> [org.hibernate.id.enhanced.SequenceStyleGenerator]
[seqhilo] -> [org.hibernate.id.SequenceHiLoGenerator]
[increment] -> [org.hibernate.id.IncrementGenerator]
[foreign] -> [org.hibernate.id.ForeignGenerator]
[sequence-identity] -> [org.hibernate.id.SequenceIdentityGenerator]
[enhanced-sequence] -> [org.hibernate.id.enhanced.SequenceStyleGenerator]
[enhanced-table] -> [org.hibernate.id.enhanced.TableGenerator]

#### Update your generation project with:
- switch to junit 5
- integration test
- with a single query type
- with the best id generation strategy
- entity with id, name and datetime
- populating data

#### open AssetMapping, explain and make it explicit, what is used FILE_NAME_PARAM vs COLUMN_FILE_NAME, and where

#### Testing with Arquillian, WildFly test container

#### Test containers

- [Testcontainers](https://javadoc.io/static/org.testcontainers/junit-jupiter/1.15.3/org/testcontainers/junit/jupiter/Testcontainers.html)
- [JUnit 5 Quickstart](https://www.testcontainers.org/quickstart/junit_5_quickstart/)
- [Reuse Containers With Testcontainers for Fast Integration Tests](https://rieckpil.de/reuse-containers-with-testcontainers-for-fast-integration-tests/)
- [Write Spring Boot Integration Tests With Testcontainers](https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/)
- [testcontainers releases](https://github.com/testcontainers/testcontainers-java/releases)

#### persistence.xml features
https://thorben-janssen.com/jpa-persistence-xml/
https://vladmihalcea.com/jpa-persistence-xml/

#### Datasources configuration in WildFly

2021-04-19 17:16:00,983 INFO  [org.jboss.as.connector.subsystems.datasources] (MSC service thread 1-5) WFLYJCA0098: Bound non-transactional data source: java:/DseDS
<datasource jta="false"
vs
2021-04-19 17:16:00,985 INFO  [org.jboss.as.connector.subsystems.datasources] (MSC service thread 1-6) WFLYJCA0001: Bound data source [java:/MediaDS]
<datasource jta="true"

And other dasource configuration

#### Monitoring DB and connections performance

[Auditing with JPA, Hibernate, and Spring Data JPA](https://www.baeldung.com/database-auditing-jpa)

#### Testing jdbc
-  [@Autowired private JdbcTemplate jdbcTemplate;](https://reflectoring.io/spring-boot-data-jpa-test/)

#### Multiple databases support

[Multiple databases](https://www.baeldung.com/spring-data-jpa-multiple-databases)

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

#### Spring vs jpa vs Hibernate annotations

https://www.baeldung.com/spring-data-annotations

#### Invoking stored procedures

invoking a procedure: https://stackoverflow.com/questions/64624083/is-it-possible-use-spring-data-jpa-with-spring-data-jdbc

#### triggering liquibase with spring only

#### triggering liquibase with standalone application

#### Using Flyway
- [Using Flyway in IT with Spring @DataJpaTest](https://reflectoring.io/spring-boot-data-jpa-test/)

#### Jpa vs hibernate vs Eclise

[The Difference Between JPA, Hibernate and EclipseLink](https://www.baeldung.com/jpa-hibernate-difference)

#### Spring Data JPA Tutorial

https://www.petrikainulainen.net/spring-data-jpa-tutorial
https://www.baeldung.com/tag/jpa/
https://dzone.com/articles/persistence-layer-spring-data
