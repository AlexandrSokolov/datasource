package com.savdev.datasource;

import com.savdev.datasource.entities.JpqlOnRepository;
import com.savdev.datasource.repositories.JpqlOnRepositoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class JpqlOnRepositoryTest {

  @Autowired
  private JpqlOnRepositoryRepository repository;

  @Test
  public void testJpqlOnEntity() {
    List<JpqlOnRepository> items = repository.findAllByExplicitJpql();
    Assertions.assertNotNull(items);
  }
}
