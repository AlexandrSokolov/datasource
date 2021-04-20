package com.savdev.datasource;

import com.savdev.datasource.entities.ExampleEntity;
import com.savdev.datasource.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.util.List;

@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql("/scripts/insert.shared.examples.sql")
public class PopulatingWithSqlIT extends BaseInitializerIT {

  @Autowired
  private ExampleEntityRepository exampleEntityRepository;

  @Test
  @Sql("/scripts/insert.examples.sql")
  public void populateWithSqlScript() {
    List<ExampleEntity> items = exampleEntityRepository.findAll();
    Assertions.assertNotNull(items);
    // 2 items - from insert.shared.examples.sql
    // 2 items - from insert.examples.sql
    Assertions.assertEquals(4, items.size());
  }
}
