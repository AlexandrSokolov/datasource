package com.savdev.datasource;

import com.savdev.datasource.entities.NativeOnEntity;
import com.savdev.datasource.repositories.NativeOnEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class NativeOnEntityTest {

  @Autowired
  private NativeOnEntityRepository repository;

  @Test
  public void testNativeOnEntity() {
    List<NativeOnEntity> items = repository.findAllByNative();
    Assertions.assertNotNull(items);
  }
}
