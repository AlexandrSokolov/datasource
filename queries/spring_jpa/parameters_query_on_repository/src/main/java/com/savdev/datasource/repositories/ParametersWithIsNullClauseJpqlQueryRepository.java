package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.ParametersQueryOnRepoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.FIELD_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Queries.PARAM_NAME;

@Eager
public interface ParametersWithIsNullClauseJpqlQueryRepository extends JpaRepository<ParametersQueryOnRepoEntity, Long> {

  //Method ends with `IsNull`
  List<ParametersQueryOnRepoEntity> findByNameIsNull();

  //Method ends with `IsNull`
  List<ParametersQueryOnRepoEntity> findByNameIsNotNull();

  //if user passes null, with no value, Spring Data Jpa automatically converts query with IS NULL:
  List<ParametersQueryOnRepoEntity> findByName(String name);

  //with explicit query:
  //(:name is null or e.name = :name)
  //if the: name parameter is null, then the clause is always true
  @Query("SELECT e FROM " + ParametersQueryOnRepoEntity.Persistence.ENTITY_NAME + " e " +
    "WHERE (:" + FIELD_NAME + " IS NULL OR " + FIELD_NAME + " = :" + PARAM_NAME + ")")
  List<ParametersQueryOnRepoEntity> findByExplicitQuery(@Param(PARAM_NAME) String name);
}
