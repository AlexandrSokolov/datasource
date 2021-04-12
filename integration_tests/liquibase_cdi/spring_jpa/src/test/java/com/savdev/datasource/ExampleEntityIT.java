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
import java.util.List;

//without DataJpaTest
@RunWith(SpringRunner.class) //alternative is SpringJUnit4ClassRunner.class
@ContextConfiguration(classes = { SpringItConfig.class })
@Transactional
public class ExampleEntityIT {

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
  public void testFindByNameIndexed() {
    ExampleEntity example = new ExampleEntity();
    //example.setId(10);
    example.setName("test");

    //entityExampleRepository.saveAndFlush(example);
    entityManager.persist(example);
    entityManager.flush();

    List<ExampleEntity> found = exampleEntityRepository.findAll();
    Assert.assertNotNull(found);
    Assert.assertFalse(found.isEmpty());
  }
}
