package com.savdev.datasource;

import com.savdev.datasource.entities.ExampleEntity;
import com.savdev.datasource.repositories.ExampleEntityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringRunner.class) //alternative is SpringJUnit4ClassRunner.class
@ContextConfiguration(classes = { SpringItConfig.class })
@Transactional
public class ExampleEntityIT {

  public static final long ENTITY_ID = 25L;

  @Autowired
  private DataSource dataSource;
  @Autowired private EntityManager entityManager;
  @Autowired private ExampleEntityRepository exampleEntityRepository;

  @Test
  public void injectedComponentsAreNotNull(){
    Assert.assertNotNull(dataSource);
    Assert.assertNotNull(entityManager);
    Assert.assertNotNull(exampleEntityRepository);
  }

  @Test
  public void saveWithEntityManagerAndFindById() {
    Optional<ExampleEntity> found = exampleEntityRepository.findById(ENTITY_ID);
    Assert.assertFalse(found.isPresent());

    ExampleEntity example = new ExampleEntity();
    example.setId(ENTITY_ID);
    example.setName("test");
    entityManager.persist(example);
    entityManager.flush();

    found = exampleEntityRepository.findById(ENTITY_ID);
    Assert.assertTrue(found.isPresent());
  }

  @Test
  public void saveWithSpringRepositoryAndFindById() {
    Optional<ExampleEntity> found = exampleEntityRepository.findById(ENTITY_ID);
    Assert.assertFalse(found.isPresent());

    ExampleEntity example = new ExampleEntity();
    example.setId(ENTITY_ID);
    example.setName("test");
    exampleEntityRepository.saveAndFlush(example);

    found = exampleEntityRepository.findById(ENTITY_ID);
    Assert.assertTrue(found.isPresent());
  }
}
