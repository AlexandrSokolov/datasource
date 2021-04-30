package com.savdev.datasource;

import com.savdev.datasource.entities.ExampleEntity;
import com.savdev.datasource.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class PopulatingWithEntityManagerIT extends MySqlLiquibaseBaseIT {

  public static final long ENTITY_ID = 85L;
  public static final long SHARED_ID = 95L;

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private ExampleEntityRepository exampleEntityRepository;

  @BeforeEach
  public void initEachTestWithSharedData() {
    ExampleEntity example = new ExampleEntity();
    example.setId(SHARED_ID);
    example.setName("test");
    entityManager.persist(example);
    entityManager.flush();
  }

  @Test
  public void populateWithEntityManager() {
    // 1 item - from `initEachTestWithSharedData` with `EntityManager` and `@BeforeEach`
    Assertions.assertEquals(1,
      exampleEntityRepository
        .findAll()
        .size());

    Assertions.assertFalse(exampleEntityRepository
      .findById(ENTITY_ID)
      .isPresent());

    ExampleEntity example = new ExampleEntity();
    example.setId(ENTITY_ID);
    example.setName("test");
    entityManager.persist(example);
    entityManager.flush();

    Assertions.assertTrue(exampleEntityRepository
      .findById(ENTITY_ID)
      .isPresent());

    Assertions.assertEquals(2, exampleEntityRepository
      .findAll()
      .size());
  }
}
