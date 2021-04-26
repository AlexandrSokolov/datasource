package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.ParametersQueryOnRepoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.FIELD_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.PARAM_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.PARAM_NAMES;

@Eager
public interface ParametersJpqlQueryRepository extends JpaRepository<ParametersQueryOnRepoEntity, Long> {

  List<ParametersQueryOnRepoEntity> findByName(String name);

  List<ParametersQueryOnRepoEntity> findByNameIsNot(String name);

  @Query("SELECT e FROM " + ParametersQueryOnRepoEntity.Persistence.ENTITY_NAME + " e WHERE " + FIELD_NAME + " = :" + PARAM_NAME)
  List<ParametersQueryOnRepoEntity> byNamedParameter(@Param(PARAM_NAME) String name);

  @Query("SELECT e FROM " + ParametersQueryOnRepoEntity.Persistence.ENTITY_NAME + " e WHERE " + FIELD_NAME + " = ?1")
  List<ParametersQueryOnRepoEntity> byIndexedParameter(String name);

  @Query("SELECT e FROM " + ParametersQueryOnRepoEntity.Persistence.ENTITY_NAME + " e WHERE " + FIELD_NAME + " IN :" + PARAM_NAMES)
  List<ParametersQueryOnRepoEntity> withInClause(@Param(PARAM_NAMES) Set<String> names);

  @Query("SELECT e FROM " + ParametersQueryOnRepoEntity.Persistence.ENTITY_NAME + " e WHERE " + FIELD_NAME + " NOT IN :" + PARAM_NAMES)
  List<ParametersQueryOnRepoEntity> withNotInClause(@Param(PARAM_NAMES) Set<String> names);

  //with `LessThan`
  List<ParametersQueryOnRepoEntity> findByAgeLessThan(int age);

  //with `LessThanEqual`
  List<ParametersQueryOnRepoEntity> findByAgeLessThanEqual(int age);

  List<ParametersQueryOnRepoEntity> findByAgeGreaterThan(int age);

  List<ParametersQueryOnRepoEntity> findByAgeGreaterThanEqual(int age);

  List<ParametersQueryOnRepoEntity> findByDateAfter(LocalDateTime date);

  //there is no `AfterEqual` for dates, but you can use `GreaterThanEqual` with dates
  List<ParametersQueryOnRepoEntity> findByDateGreaterThanEqual(LocalDateTime date);

  List<ParametersQueryOnRepoEntity> findByDateBefore(LocalDateTime date);

  //there is no `BeforeEqual` for dates, but you can use `LessThanEqual` with dates
  List<ParametersQueryOnRepoEntity> findByDateLessThanEqual(LocalDateTime date);

  List<ParametersQueryOnRepoEntity> findByDateBetween(LocalDateTime dateL, LocalDateTime dateR);

  List<ParametersQueryOnRepoEntity> findByAgeBetween(int l, int r);
}
