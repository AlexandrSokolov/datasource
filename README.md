## Datasource

Project to illustrate datasource-related issues

**Project features:**
- [Schema creation, WildFly datasource and JPA configurations](schema/README.md)
- [Integration of Spring Data JPA with CDI](#integration-of-spring-data-jpa-with-cdi)
- [IT of datasource layer](integration_tests/README.md)
- [Connecting to sql docker container](#connecting-to-sql-docker-container)

### Integration of Spring Data JPA with CDI

[CDI Integration](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpd.misc.cdi-integration)

Steps to integrate Spring Data JPA with CDI:
1. Create a Spring data repository interface. Apply `@Eager` annotation:
```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;

@Eager
public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, Long> {
}
```
2. Produce `EntityManager` with `custom-persistent-unit` in:
```
<?xml version="1.0" encoding="UTF-8"?>
<persistence ...>
  <persistence-unit name="custom-persistent-unit" ...>
    ...
  </persistence-unit>
</persistence>
```
Producer:
```java
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CdiEntityManagerProducer {

  @Produces
  @Dependent //do not set RequestScoped
  @PersistenceContext(unitName = "custom-persistent-unit") //see `persistence-unit name="custom-persistent-unit"` in persistence.xml
  public EntityManager entityManager;
}
```
3. Inject a repository:
```
@Inject
ExampleEntityRepository repository;
```

**Note:**

There is an issue: [Spring Data JPA repositories use in EJB/CDI causes TransactionRequiredException](https://stackoverflow.com/questions/41621679/spring-data-jpa-repositories-use-in-ejb-timer-causes-transactionrequiredexceptio),
when you invoke a method of a Spring repository, and at first time you get an exception:
```text
Caused by: javax.ejb.EJBException: javax.persistence.TransactionRequiredException: WFLYJPA0060: Transaction is required to perform this operation (either use a transaction or extended persistence context)
```
Or very similar:
```text
ERROR [org.jboss.as.ejb3] (EJB default - 1) WFLYEJB0020: ... javax.ejb.EJBTransactionRolledbackException: WFLYJPA0060: Transaction is required to perform this operation (either use a transaction or extended persistence context)
```
Next invocations work fine. 

Solution: Add `@Eager` annotation to your repository declaration. 
The Spring Data CDI extension will initialize the repository on startup.

[DATAJPA-724](https://github.com/spring-projects/spring-data-jpa/issues/1099)

### Connecting to sql docker container

3.1 Via Docker Container:

`$ docker exec -it ${docker_mysql_container_name} mysql -uroot -p`

For instance if `${docker_mysql_container_name}` = `bm_my_sql`, run:

`$ docker exec -it bm_my_sql mysql -uroot -p`

And then enter `${mysql_root_pwd}` password.