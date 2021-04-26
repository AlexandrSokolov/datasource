package com.savdev.datasource;

import com.savdev.datasource.repositories.ParametersWithIsNullClauseJpqlQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
@Sql("/scripts/examples.sql")
public class ParametersWithIsNullClauseJpqlQueryRepositoryTest {

  public static final String SEARCH_VALUE = "test name 31";

  @Autowired
  private ParametersWithIsNullClauseJpqlQueryRepository repository;

  @Test
  public void testFindByNameContaining() {
    Assertions.assertEquals(1, repository.findByNameIsNull().size());
  }

  @Test
  public void testFindByNameIsNotNull() {
    Assertions.assertEquals(5, repository.findByNameIsNotNull().size());
  }

  @Test
  public void testFindByName() {
    Assertions.assertEquals(1, repository.findByName(null).size());
  }

  @Test
  public void testFindByExplicitQuery() {
    Assertions.assertEquals(6, repository.findByExplicitQuery(null).size());
    Assertions.assertEquals(1, repository.findByExplicitQuery(SEARCH_VALUE).size());
  }


}
