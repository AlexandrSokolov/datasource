package com.savdev.datasource;

import com.savdev.datasource.entities.NativeOnRepository;
import com.savdev.datasource.repositories.NativeOnRepositoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class NativeOnRepositoryTest {

  @Autowired
  private NativeOnRepositoryRepository repository;

  @Test
  public void testJpqlOnEntity() {
    List<NativeOnRepository> items = repository.findAllByNativeQuery();
    Assertions.assertNotNull(items);
  }
}
