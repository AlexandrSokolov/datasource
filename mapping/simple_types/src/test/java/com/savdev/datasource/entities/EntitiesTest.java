package com.savdev.datasource.entities;

import com.savdev.datasource.MySqlLiquibaseBaseIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.Duration;

public class EntitiesTest extends MySqlLiquibaseBaseIT {

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testTablesCreation() {
    Assertions.assertNotNull(entityManager);
  }

  @Test
  public void testDuration() {
    Duration duration = Duration.ofHours(1);
    DateAndTimeTypesEntity dateAndTimeTypesEntity = new DateAndTimeTypesEntity();
    dateAndTimeTypesEntity.setId(1L);
    dateAndTimeTypesEntity.setDuration(duration);

    entityManager.persist(dateAndTimeTypesEntity);
    entityManager.flush();

    DateAndTimeTypesEntity found = entityManager.find(DateAndTimeTypesEntity.class, dateAndTimeTypesEntity.getId());
    Assertions.assertEquals(duration, found.getDuration());
  }
}
