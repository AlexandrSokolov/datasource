## Queries

When we work with queries we need to choose:

- [Type of queries](#1-types-of-queries)
- [How a query can be defined?](#2-query-definition)
- [How a query can be invoked?](#3-query-invocation)
- [Modifying the resulting entity](#modifying-the-resulting-entity)

#### 1. Types of queries

1.1 JPQL Queries. Used JPQL language, based on the JPA entity model. JPA entity fields, but not column names are used:
`SELECT a, b FROM Author a JOIN a.books b`

1.2 Native queries. SQL is used. Database-vendor specific approach:
```SELECT id, firstName, lastName, email FROM employee```

**Note:** With SQL queries that return entities the resulting entity instances become managed by the persistence context.
If you modify one of the returned entities, 
it will be written to the database when the persistence context becomes associated with a transaction.

So it is important to ensure that all the necessary data required to fully construct the entity is part of the query. 
If you leave out a field from the query, or default it to some value and then modify the resulting entity, 
there is a possibility that you will overwrite the correct version already stored in the database.

#### 2. Query definition

##### 2.1 Define query on a Spring repository method (Spring specific, not JPA specification)
##### 2.2 Define query on a JPA Entity to invoke it via Spring repository

Query name must follow the following rule:

It **must start** with an entity name, then dot, then method name, used in a Spring repository:

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

#### 3. Query invocation

#### Modifying the resulting entity

For both SQL and JPQL queries the resulting entity instances become managed by the persistence context.
If you modify one of the returned entities, 
it will be written to the database when the persistence context becomes associated with a transaction.