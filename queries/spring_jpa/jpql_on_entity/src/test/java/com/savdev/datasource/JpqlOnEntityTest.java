package com.savdev.datasource;

import com.savdev.datasource.entities.JpqlOnEntity;
import com.savdev.datasource.repositories.JpqlOnEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class JpqlOnEntityTest {

  @Autowired
  private JpqlOnEntityRepository repository;

  @Test
  public void testJpqlOnEntity() {
    List<JpqlOnEntity> items = repository.findAllByJpql();
    Assertions.assertNotNull(items);
  }
}
