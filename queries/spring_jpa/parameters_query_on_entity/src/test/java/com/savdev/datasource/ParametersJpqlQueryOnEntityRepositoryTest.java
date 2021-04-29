package com.savdev.datasource;

import com.savdev.datasource.repositories.ParametersJpqlQueryOnEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

@DataJpaTest
@Sql("/scripts/examples.sql")
public class ParametersJpqlQueryOnEntityRepositoryTest {

  private static final String NAME_VALUE = "test name 41";
  private static final String NAME_VALUE2 = "test name 30";
  private static final String NOT_EXISTING = "not existing";
  @Autowired
  private ParametersJpqlQueryOnEntityRepository repository;

  @Test
  public void testByEqual() {
    Assertions.assertTrue(repository.byEqual(NAME_VALUE).isPresent());
    Assertions.assertFalse(repository.byEqual(NOT_EXISTING).isPresent());
  }

  @Test
  public void testByNotEqual() {
    Assertions.assertEquals(4, repository.byNotEqual(NAME_VALUE).size());
  }

  @Test
  public void testByGreaterThan() {
    Assertions.assertEquals(1, repository.byGreaterThan(30).size());
  }

  @Test
  public void testBySmallerThan() {
    Assertions.assertEquals(4, repository.bySmallerThan(30).size());
  }

  @Test
  public void testByBetween() {
    Assertions.assertEquals(4, repository.byBetween(15, 30).size());
  }

  @Test
  public void testByLike() {
    Assertions.assertEquals(2, repository.byLike("%name 2%").size());
  }

  @Test
  public void testByIsNull() {
    Assertions.assertEquals(1, repository.byIsNull().size());
  }

  @Test
  public void testByIsNotNull() {
    Assertions.assertEquals(5, repository.byIsNotNull().size());
  }

  @Test
  public void testByIn() {
    Assertions.assertEquals(2, repository.byIn(Arrays.asList(NAME_VALUE, NAME_VALUE2)).size());
  }
}
