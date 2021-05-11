package com.savdev.datasource.entities;

import com.savdev.datasource.MySqlLiquibaseBaseIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class IdentityIdEntityTest extends MySqlLiquibaseBaseIT {

  public static final String NAME = "test name";
  public static final String UPDATED_NAME = "updated name";

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testSave() {
    //insert the 1st entity
    Assertions.assertNotNull(persistEntity(NAME));
    //insert the 2nd entity
    Long id2nd = persistEntity(NAME);
    Assertions.assertNotNull(id2nd);
    IdentityIdEntity entity = entityManager.find(IdentityIdEntity.class, id2nd);
    Assertions.assertNotNull(entity);
    //update the 2nd entity
    entity.setName(UPDATED_NAME);
    entityManager.flush();
  }


  private Long persistEntity(final String name) {
    IdentityIdEntity entity = new IdentityIdEntity();
    entity.setName(name);
    Assertions.assertNull(entity.getId());
    entityManager.persist(entity);
    return entity.getId();
  }
}
