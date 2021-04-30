## Queries

- [Type of queries](#1-types-of-queries)
- [How a query for selection can be defined?](#2-named-query-definition-for-selection)
- [Passing parameters](#3-passing-parameters) 
- [Joining entities](#4-joining-entities)
- [Ordering, sorting]
  [ORDER BY](https://thorben-janssen.com/spring-data-jpa-query-annotation/)
- [Setting a limit, pagination]
  [pagination](https://thorben-janssen.com/spring-data-jpa-query-annotation/)
- [Getting collection, list, set, optional]
- [How a query for insertions/updates can be defined? Batch updates]
- [How a query can be invoked?](#3-query-invocation)
- [Modifying the resulting entity](#modifying-the-resulting-entity)

#### 1. Types of queries

1.1 JPQL Queries. Used JPQL language, based on the JPA entity model. JPA entity fields, but not column names are used:
`SELECT a, b FROM Author a JOIN a.books b`

  You have 2 options here:
  - statically defined named queries (preferred option)
  - dynamically defined queries:
```java
public UserEntity getUserByIdWithTypedQuery(Long id) {
    TypedQuery<UserEntity> typedQuery
      = getEntityManager().createQuery("SELECT u FROM UserEntity u WHERE u.id=:id", UserEntity.class);
    typedQuery.setParameter("id", id);
    return typedQuery.getSingleResult();
}
```


1.2 Native queries. SQL is used. Database-vendor specific approach:
```SELECT id, firstName, lastName, email FROM employee```

**Note:** With SQL queries that return entities the resulting entity instances become managed by the persistence context.
If you modify one of the returned entities, 
it will be written to the database when the persistence context becomes associated with a transaction.

So it is important to ensure that all the necessary data required to fully construct the entity is part of the query. 
If you leave out a field from the query, or default it to some value and then modify the resulting entity, 
there is a possibility that you will overwrite the correct version already stored in the database.

1.3 Criteria API Query

See [Criteria API Query](#todo)

#### 2. Named query definition for selection

##### 2.1 Define query on a Spring repository method (Spring specific, not JPA specification)

Very clear and explicit approach. You define query on the method.

JPA Entity definition does not contain any queries defined:
```java
@Entity
@Table(name = JpqlOnRepository.Persistence.TABLE_NAME)
public class JpqlOnRepository {

  public interface Persistence {
    String ENTITY_NAME = "JpqlOnRepository"; //same as entity
  }
}
```
Via Spring's `@Query` annotation define query on the method: 
```java
import org.springframework.data.jpa.repository.Query;
...
public interface JpqlOnRepositoryRepository extends JpaRepository<JpqlOnRepository, Long> {


  @Query("SELECT e FROM " + JpqlOnRepository.Persistence.ENTITY_NAME + " e")
  List<JpqlOnRepository> findAllByExplicitJpql();
}
```

See: 

[JPQL query defined on a Spring repository](spring_jpa/jpql_on_repository/src/main/java/com/savdev/datasource/repositories/JpqlOnRepositoryRepository.java)

[Native query defined on a Spring repository](spring_jpa/native_on_repository/src/main/java/com/savdev/datasource/repositories/NativeOnRepositoryRepository.java)

##### 2.2 Define query on a JPA Entity to invoke it via Spring repository

Query name must follow the following rule:

It **must start** with an entity name, then dot, then method name, used in a Spring repository:

**Note:** since  JPA 2.2, you can use multiple `@NamedQuery` annotations without wrapping them by `@NamedQueries`

```java
@NamedQueries({
  @NamedQuery(
    name = JpqlOnEntity.Queries.JPQL_QUERY,
    query = "SELECT e FROM " + JpqlOnEntity.Persistence.ENTITY_NAME + " e")
})
public class JpqlOnEntity {

  public interface Persistence {
    String ENTITY_NAME = "JpqlOnEntity"; //same as entity
  }

  /**
   * To use with Spring Data Jpa, query name must start with entity name, then dot, then method name,
   * for instance the following methods will be invoked for queries:
   */
  public interface Queries {
    String JPQL_QUERY = Persistence.ENTITY_NAME + ".findAllByJpql";
  }
}
```
Spring repository. Method name: `findAllByJpql` is the same as used in: `Persistence.ENTITY_NAME + ".findAllByJpql"`:
```java
public interface JpqlOnEntityRepository extends JpaRepository<JpqlOnEntity, Long> {

  List<JpqlOnEntity> findAllByJpql();
}
```

Note: it works this way both for JPQL and Native queries, see:
[JPQL query on Entity](spring_jpa/jpql_on_entity/src/main/java/com/savdev/datasource/entities/JpqlOnEntity.java)
[Native query on Entity](spring_jpa/native_on_entity/src/main/java/com/savdev/datasource/entities/NativeOnEntity.java)

##### 2.3 Define query on a JPA Entity to invoke it via `EntityManager` without Spring

The same as [query on a JPA Entity with Spring](#23-define-query-on-a-jpa-entity-to-invoke-it-via-entitymanager-without-spring),
but without the requirement to a query name.

#### 3. Passing parameters

In case a query is defined on a repository with Spring Data Jpa (recommended):
[Passing parameters into a query, defined on a Spring repository](spring_jpa/parameters_query_on_repository)

With Spring see also:
[Spring Query Creation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)

In case a query is defined on a JPA Entity **without** Spring Data Jpa:
[Passing parameters into a query, defined on a JPA entity](spring_jpa/parameters_query_on_entity)

#### 4. Joining entities

- [With `JOIN`](#41-joining-with-join-operator-preferred)
- [With `WHERE` without `JOIN`](#42-joining-with-where-without-join-operator)

##### 4.1 Inner Joins with `JOIN` operator:

The syntax: `[INNER] JOIN <path_expression> [AS] <identifier>`

The advantage of `JOIN` operator: 
the join can be expressed in terms of the association itself, 
and the query engine will automatically supply the necessary join criteria when it generates the SQL.


Note: you can use either `JOIN` or `INNER JOIN` syntax.

##### 4.2 Inner joining with `WHERE` without `JOIN` operator:

##### 4.3 Joining with a path expression.

#### Query invocation

#### Modifying the resulting entity

For both SQL and JPQL queries the resulting entity instances become managed by the persistence context.
If you modify one of the returned entities, 
it will be written to the database when the persistence context becomes associated with a transaction.