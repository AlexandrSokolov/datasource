package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.ParametersQueryOnEntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.PARAM_AGE;
import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.PARAM_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.PARAM_NAMES;

@Eager
public interface ParametersJpqlQueryOnEntityRepository extends JpaRepository<ParametersQueryOnEntityEntity, Long> {


  Optional<ParametersQueryOnEntityEntity> byEqual(@Param(PARAM_NAME) String name);

  List<ParametersQueryOnEntityEntity> byNotEqual(@Param(PARAM_NAME) String name);

  List<ParametersQueryOnEntityEntity> byGreaterThan(@Param(PARAM_AGE) int age);

  List<ParametersQueryOnEntityEntity> bySmallerThan(@Param(PARAM_AGE) int age);

  List<ParametersQueryOnEntityEntity> byBetween(int ageL, int ageR);

  List<ParametersQueryOnEntityEntity> byLike(@Param(PARAM_NAME) String namePattern);

  List<ParametersQueryOnEntityEntity> byIsNull();

  List<ParametersQueryOnEntityEntity> byIsNotNull();

  List<ParametersQueryOnEntityEntity> byIn(@Param(PARAM_NAMES) Collection<String> names);
}
