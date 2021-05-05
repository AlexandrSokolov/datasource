#### Mapping


documents on DurationConverter

See also: 
[Hibernate types](https://vladmihalcea.com/a-beginners-guide-to-hibernate-types/)
[Data types mapping in Liquibase](https://dba-presents.com/index.php/liquibase/216-liquibase-3-6-x-data-types-mapping-table)

- What is the use of the @Temporal annotation in Hibernate?](https://stackoverflow.com/questions/25333711/what-is-the-use-of-the-temporal-annotation-in-hibernate)
- [Enums](https://dev.mysql.com/doc/refman/8.0/en/enum.html)
- [Set types](https://dev.mysql.com/doc/refman/8.0/en/set.html)
- [custom hibernate dialect for validation](https://stackoverflow.com/questions/43093799/hibernate-database-schema-validation-fails-for-h2-database-when-entity-contains)
- Transient state
[FetchTypes](https://thorben-janssen.com/entity-mappings-introduction-jpa-fetchtypes/)
[equals() and hashCode() with Hibernate](https://thorben-janssen.com/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/)
[inheritance](https://thorben-janssen.com/complete-guide-inheritance-strategies-jpa-hibernate/)
utility methods, `No need to mark utility methods as @Transient`

[Hibernate Validator](https://docs.jboss.org/hibernate/validator/3.1/reference/en/html_single/)


- [How to fix “wrong column type encountered” schema-validation errors with JPA and Hibernate](https://vladmihalcea.com/how-to-fix-wrong-column-type-encountered-schema-validation-errors-with-jpa-and-hibernate/)
 with overwriting: [dialect](https://stackoverflow.com/questions/16891570/hibernate-validation-issue-for-blob-sql-type)

- change schema validation for a single field, without `columnDefinition`: 
  see [Hibernate database specific columnDefinition values](https://stackoverflow.com/questions/1944660/hibernate-database-specific-columndefinition-values)
- [TODO](https://stackoverflow.com/questions/56863906/hibernate-throwing-validation-exception-wrong-column-type-encountered-in-column)
- [5 Things You Need to Know When Using Hibernate with Mysql](https://thorben-janssen.com/5-things-you-need-to-know-when-using-hibernate-with-mysql/)

https://thorben-janssen.com/hibernate-tips-whats-the-difference-between-column-nullable-false-and-notnull/
https://www.baeldung.com/hibernate-notnull-vs-nullable

[hibernate](https://docs.jboss.org/hibernate/orm/5.0/mappingGuide/en-US/html/ch03.html)

https://www.baeldung.com/hibernate-types-library

Issue with lazy Fetching
`AttributeConverter`


- Files, write tests for all cases, add InputStream field, if it is possible
- write tests for `@Temporal(TemporalType.TIME)`

#### Queries
- [Joining](https://thorben-janssen.com/jpql)
  JP QL supports multiple join types, including 
  inner and 
  outer joins, 
  as well as a technique called fetch joins for eagerly loading data associated to the result type 
  of a query but not directly returned.
  
  ELECT p.number
  FROM Employee e JOIN e.phones p
  In the definition of path expressions earlier, it was noted that a path couldn’t continue from a state
  field or collection association field. To work around this situation, the collection association field must
  be joined in the FROM clause so that a new identification variable is created for the path, allowing it to
  be the root for new path expressions.
  
  [How to join unrelated entities](https://thorben-janssen.com/how-to-join-unrelated-entities/)
  [How to join unrelated entities](https://stackoverflow.com/questions/20473033/jpa-left-join-2-tables-without-association)

- [Grouping](https://thorben-janssen.com/jpql/)
- [Projection – The SELECT clause](https://thorben-janssen.com/jpql)
  [SQLResultSetMappings](https://thorben-janssen.com/jpa-native-queries/)
  returning Optional
- [Distinct, pagination, ordering](https://thorben-janssen.com/jpql)
  [Pagination](https://www.baeldung.com/spring-data-jpa-query)
  [pagination](https://www.baeldung.com/jpa-pagination)
- [Functions](https://thorben-janssen.com/jpql/)
- [Subselects](https://thorben-janssen.com/jpql/)
- [CrudRepository, JpaRepository, and PagingAndSortingRepository](https://www.baeldung.com/spring-data-repositories)


- [Modifyting with queries](https://www.baeldung.com/spring-data-jpa-query)
  updates and inserts
  [Modifying Queries](https://thorben-janssen.com/spring-data-jpa-query-annotation/)
  [insert](https://www.baeldung.com/jpa-insert)
- [Dynamic queries (JPA Criteria API)](https://www.baeldung.com/spring-data-jpa-query)
  [Combining JPA And/Or Criteria Predicates](https://www.baeldung.com/jpa-and-or-criteria-predicates)
  [Criteria](https://www.baeldung.com/spring-data-criteria-queries)
  [IN in criteria](https://www.baeldung.com/jpa-criteria-api-in-expressions)
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
- jpa query hints: https://examples.javacodegeeks.com/enterprise-java/jpa-named-query-example/
- [Polymorphism and Downcasting](https://thorben-janssen.com/jpql/)  
- @org.hibernate.annotations.NamedQueries, timeouts and fetchSize
  https://www.baeldung.com/hibernate-named-query
  
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

[Hibernate Logging Guide](https://thorben-janssen.com/hibernate-logging-guide/)

#### Test mysql containers configuration

connecting to the running container, without starting/stopping it in test

updating to use only: `TLSv1.2`:
mysql> SHOW GLOBAL VARIABLES LIKE 'tls_version';
+---------------+-----------------------+
| Variable_name | Value                 |
+---------------+-----------------------+
| tls_version   | TLSv1,TLSv1.1,TLSv1.2 |
+---------------+-----------------------+
1 row in set (0.00 sec)

see also: /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/java.security
https://stackoverflow.com/questions/38205947/sslhandshakeexception-no-appropriate-protocol

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

#### hibernate custom validation:

https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/validator-customconstraints.html
https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/index.html
https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#preface
https://stackoverflow.com/questions/40482252/validation-of-a-date-with-hibernate
https://docs.jboss.org/ejb3/app-server/HibernateAnnotations/api/org/hibernate/validator/package-summary.html

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
