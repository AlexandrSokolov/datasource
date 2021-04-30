package com.savdev.datasource.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
public class EntitiesTest {

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testTablesCreation() {
    Assertions.assertNotNull(entityManager);
  }
}
