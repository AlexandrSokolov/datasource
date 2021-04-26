package com.savdev.datasource;

import com.google.common.collect.Sets;
import com.savdev.datasource.repositories.ParametersJpqlQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DataJpaTest
@Sql("/scripts/examples.sql")
public class ParametersJpqlQueryRepositoryTest {

  public static final String NAME_VALUE = "test name 20";
  public static final String NAME_VALUE_2 = "test name 30";

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Autowired
  private ParametersJpqlQueryRepository repository;

  @Test
  public void testFindByName() {
    Assertions.assertEquals(1, repository.findByName(NAME_VALUE).size());
  }

  @Test
  public void testFindByNameIsNot() {
    Assertions.assertEquals(4, repository.findByNameIsNot(NAME_VALUE).size());
  }

  @Test
  public void testByNamedParameter() {
    Assertions.assertEquals(1, repository.byNamedParameter(NAME_VALUE).size());
  }

  @Test
  public void testByIndexedParameter() {
    Assertions.assertEquals(1, repository.byIndexedParameter(NAME_VALUE).size());
  }

  @Test
  public void testWithInClause() {
    Assertions.assertEquals(2, repository.withInClause(Sets.newHashSet(NAME_VALUE, NAME_VALUE_2)).size());
  }

  @Test
  public void testWithNotInClause() {
    //null value also will not be included
    Assertions.assertEquals(3, repository.withNotInClause(Sets.newHashSet(NAME_VALUE, NAME_VALUE_2)).size());
  }

  @Test
  public void testFindByAgeLessThen() {
    Assertions.assertEquals(4, repository.findByAgeLessThan(30).size());
  }


  @Test
  public void testFindByAgeLessThenEqual() {
    Assertions.assertEquals(3, repository.findByAgeLessThanEqual(18).size());
  }

  @Test
  public void testFindByAgeGreaterThan() {
    Assertions.assertEquals(1, repository.findByAgeGreaterThan(30).size());
  }

  @Test
  public void testFindByAgeGreaterThanEqual() {
    Assertions.assertEquals(2, repository.findByAgeGreaterThanEqual(30).size());
  }

  @Test
  public void testFindByDateAfter() {
    Assertions.assertEquals(3,
      repository.findByDateAfter(LocalDateTime.parse("2020-03-03 22:59:52", formatter)).size());
  }

  @Test
  public void testFindByDateGreaterThanEqual() {
    Assertions.assertEquals(4,
      repository.findByDateGreaterThanEqual(LocalDateTime.parse("2020-03-03 22:59:52", formatter)).size());
  }

  @Test
  public void testFindByDateBefore() {
    Assertions.assertEquals(2,
      repository.findByDateBefore(LocalDateTime.parse("2020-03-03 22:59:51", formatter)).size());
  }

  @Test
  public void testFindByDateLessThanEqual() {
    Assertions.assertEquals(3,
      repository.findByDateLessThanEqual(LocalDateTime.parse("2020-03-03 22:59:52", formatter)).size());
  }

  @Test
  public void testFindByDateBetween() {
    Assertions.assertEquals(1,
      repository.findByDateBetween(
        LocalDateTime.parse("2020-03-03 22:59:52", formatter),
        LocalDateTime.parse("2020-05-03 22:59:52", formatter)).size());
  }

  @Test
  public void testFindByAgeBetween() {
    Assertions.assertEquals(2,
      repository.findByAgeBetween(21, 40).size());
  }
}
