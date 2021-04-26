package com.savdev.datasource;

import com.savdev.datasource.repositories.ParametersWithLikeClauseJpqlQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest
@Sql("/scripts/examples.sql")
public class ParametersWithLikeClauseJpqlQueryRepositoryTest {

  public static final String SEARCH_VALUE = "name 3";
  public static final String SEARCH_UPPER_CASE_VALUE = "NAME 3";
  public static final String STARTS_WITH = "test name 2";
  public static final String ENDS_WITH = "1";

  @Autowired
  private ParametersWithLikeClauseJpqlQueryRepository repository;

  @Test
  public void testFindByNameContaining() {
    Assertions.assertEquals(2, repository.findByNameContaining(SEARCH_VALUE).size());
  }

  @Test
  public void testFindByNameContains() {
    Assertions.assertEquals(2, repository.findByNameContains(SEARCH_VALUE).size());
  }

  @Test
  public void testFindByNameIsContaining() {
    Assertions.assertEquals(2, repository.findByNameIsContaining(SEARCH_VALUE).size());
  }

  @Test
  public void testFindByNameLike() {
    Assertions.assertEquals(2,
      repository.findByNameLike("%" + SEARCH_VALUE + "%").size());
  }

  @Test
  public void testFindByNameStartsWith() {
    Assertions.assertEquals(2, repository.findByNameStartsWith(STARTS_WITH).size());
  }

  @Test
  public void testFindByNameEndsWith() {
    Assertions.assertEquals(3, repository.findByNameEndsWith(ENDS_WITH).size());
  }

  @Test
  public void testFindByNameContainingIgnoreCase() {
    Assertions.assertEquals(2, repository.findByNameContainingIgnoreCase(SEARCH_UPPER_CASE_VALUE).size());
  }

  @Test
  public void testFindByNameNotContaining() {
    Assertions.assertEquals(3, repository.findByNameNotContaining(SEARCH_VALUE).size());
  }

  @Test
  public void testFindByNameNoConvention() {
    Assertions.assertEquals(2, repository.findByNameNoConvention(SEARCH_VALUE).size());
  }


  /**
   * We try to apply SQL injection, with dropping a not-existing table, but it is not executed.
   */
  @Test
  public void testFindByNativeWithoutEscaping() {
    Assertions.assertTrue(repository.findByNativeWithoutEscaping(
      "test%; DROP TABLE notExistingTable; --").isEmpty());
  }
}
