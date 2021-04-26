package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.ParametersQueryOnRepoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Persistence.COLUMN_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Persistence.TABLE_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.FIELD_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.PARAM_NAME;

@Eager
public interface ParametersWithLikeClauseJpqlQueryRepository extends JpaRepository<ParametersQueryOnRepoEntity, Long> {

  //next 3 methods will use the same sql statement:
  //SELECT * FROM example WHERE name LIKE '%in%';
  //method name is constracted as:
  //`findBy` + field name + one of the following: `Containing`, `Contains`, `IsContaining`
  List<ParametersQueryOnRepoEntity> findByNameContaining(String name);
  List<ParametersQueryOnRepoEntity> findByNameContains(String name);
  List<ParametersQueryOnRepoEntity> findByNameIsContaining(String name);

  //this method uses the same sql statement:
  //SELECT * FROM example WHERE name LIKE '%in%';
  //The only exception - a client, instead of passing `in`, must pass expression `%in%`
  //You are more flexible, cause can set a single `%`
  List<ParametersQueryOnRepoEntity> findByNameLike(String namePattern);

  //SELECT * FROM example WHERE name LIKE 'in%';
  List<ParametersQueryOnRepoEntity> findByNameStartsWith(String name);

  //SELECT * FROM example WHERE name LIKE '%in';
  List<ParametersQueryOnRepoEntity> findByNameEndsWith(String name);

  //To ignore case sensitivity use
  //the `IgnoreCase` keyword combined with one of our other keywords:
  List<ParametersQueryOnRepoEntity> findByNameContainingIgnoreCase(String name);

  //not in clause, use the NotContains, NotContaining, and NotLike keywords:
  List<ParametersQueryOnRepoEntity> findByNameNotContaining(String name);

  //Without naming convention, you can always use named or indexed parameter with explicit `LIKE` clause and `%`
  //This also the only choice, when a query is complicated
  @Query("SELECT e FROM " + ParametersQueryOnRepoEntity.Persistence.ENTITY_NAME + " e " +
    "WHERE " + FIELD_NAME + " LIKE %:" + PARAM_NAME + "%")
  List<ParametersQueryOnRepoEntity> findByNameNoConvention(@Param(PARAM_NAME) String name);

  //SQL Injection
  //Sometimes,it is said, we must escape incoming search values for native queries with indexed parameters:
  // [https://www.baeldung.com/spring-jpa-like-queries]
  //it is wrong. The test tries to inject sql statement, to drop non-existing table, and the statement is not executed.
  // See also: https://stackoverflow.com/questions/52791121/is-java-spring-jpa-native-query-sql-injection-proof
  @Query(value = "SELECT * FROM " + TABLE_NAME +
    " WHERE " + COLUMN_NAME + " LIKE %?1%", nativeQuery = true)
  List<ParametersQueryOnRepoEntity> findByNativeWithoutEscaping(String name);
}
