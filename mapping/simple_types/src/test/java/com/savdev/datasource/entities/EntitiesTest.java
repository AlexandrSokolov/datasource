package com.savdev.datasource.entities;

import com.savdev.datasource.MySqlLiquibaseBaseIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class EntitiesTest extends MySqlLiquibaseBaseIT {

  private static final long ID = 1L;

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testTablesCreation() {
    Assertions.assertNotNull(entityManager);
  }
}
