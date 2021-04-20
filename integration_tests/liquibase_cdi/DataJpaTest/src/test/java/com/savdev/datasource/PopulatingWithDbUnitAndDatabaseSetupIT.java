package com.savdev.datasource;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.savdev.datasource.entities.ExampleEntity;
import com.savdev.datasource.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;


@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
public class PopulatingWithDbUnitAndDatabaseSetupIT extends BaseInitializerIT {

  @Autowired
  private ExampleEntityRepository exampleEntityRepository;

  @Test
  @DatabaseSetup("classpath:datasets/examples.xml")
  public void populateWithDbUnitAndDatabaseSetup() {
    List<ExampleEntity> items = exampleEntityRepository.findAll();
    Assertions.assertNotNull(items);
    Assertions.assertEquals(2, items.size());
  }

}
