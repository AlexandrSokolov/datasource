package com.savdev.datasource;

import com.savdev.datasource.entities.ExampleEntity;
import com.savdev.datasource.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PopulatingTestDataIT extends BaseItInitializer {

  public static final long ENTITY_ID = 85L;

  @Autowired
  private EntityManager entityManager;
  @Autowired
  private ExampleEntityRepository exampleEntityRepository;

  @Test
  @BeforeEach
  @Sql("/scripts/insert.shared.examples.sql") //load initial data for all methods
  public void initEachTestWithSharedData() {
    System.out.println("fsd");
  }

  @Test
  public void populateWithEntityManager() {
    Optional<ExampleEntity> found = exampleEntityRepository.findById(ENTITY_ID);
    Assertions.assertFalse(found.isPresent());

    ExampleEntity example = new ExampleEntity();
    example.setId(ENTITY_ID);
    example.setName("test");
    entityManager.persist(example);
    entityManager.flush();

    found = exampleEntityRepository.findById(ENTITY_ID);
    Assertions.assertTrue(found.isPresent());
  }

  @Test
  @Sql("/scripts/insert.examples.sql")
  public void populateWithSqlScript() {
    List<ExampleEntity> items = exampleEntityRepository.findAll();
    Assertions.assertNotNull(items);
    Assertions.assertEquals(2, items.size());
  }
}
