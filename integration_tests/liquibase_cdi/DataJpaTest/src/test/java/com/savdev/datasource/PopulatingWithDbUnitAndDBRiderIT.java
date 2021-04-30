package com.savdev.datasource;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider; //not com.github.database.rider.junit5.api.DBRider;
import com.savdev.datasource.entities.ExampleEntity;
import com.savdev.datasource.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
public class PopulatingWithDbUnitAndDBRiderIT extends MySqlLiquibaseBaseIT {

  @Autowired
  private ExampleEntityRepository exampleEntityRepository;

  @Test
  @DataSet("examples.yml") //only yml supports, but not `yaml`
  public void populateWithDbUnitAndDBRider() {
    List<ExampleEntity> items = exampleEntityRepository.findAll();
    Assertions.assertNotNull(items);
    Assertions.assertEquals(2, items.size());
  }
}
