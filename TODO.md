## Populating test data:
@Test
@BeforeEach
@Sql("/scripts/insert.shared.examples.sql") //load initial data for all methods
public void initEachTest() {
ExampleEntity example = new ExampleEntity();
example.setId(ENTITY_ID);
example.setName("test");
exampleEntityRepository.saveAndFlush(example); //additionally persist a single item for all methods
}

@Test
@Sql("/scripts/insert.examples.sql") //additionally load items only for this method
public void initSingleTest() {
List<ExampleEntity> initItems = exampleEntityRepository.findAll();
Assert.assertNotNull(initItems);
Assert.assertFalse(initItems.isEmpty());
Assert.assertEquals(5, initItems.size());
}

- [Using Using Spring DBUnit](https://reflectoring.io/spring-boot-data-jpa-test/)
- [With data.sql](https://reflectoring.io/spring-boot-data-jpa-test/)
- [With TestEntityManager](TODO https://www.baeldung.com/spring-boot-testing)
- [With @Sql](https://reflectoring.io/spring-boot-data-jpa-test/)
- [With BeforeEach]

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
- [Reuse Containers With Testcontainers for Fast Integration Tests](https://rieckpil.de/reuse-containers-with-testcontainers-for-fast-integration-tests/)
- [Write Spring Boot Integration Tests With Testcontainers](https://rieckpil.de/howto-write-spring-boot-integration-tests-with-a-real-database/)

#### Testing jdbc
-  [@Autowired private JdbcTemplate jdbcTemplate;](https://reflectoring.io/spring-boot-data-jpa-test/)

  

#### Transactions Management in-depth
- [Spring Transaction Management: @Transactional In-Depth](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth)
- [Spring Transactional Management](https://dzone.com/articles/bountyspring-transactional-management)
- [How Does Spring @Transactional Really Work?](https://dzone.com/articles/how-does-spring-transactional)
- [Start with declarative transactions based on @Transactional full annotation, and thoroughly understand the principles of Spring transaction management](https://programmer.ink/think/5cdba57f6174f.html)

#### Using Flyway
- [Using Flyway in IT with Spring @DataJpaTest](https://reflectoring.io/spring-boot-data-jpa-test/)