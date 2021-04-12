package com.savdev.datasource.repositories;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CdiEntityManagerProducer {

  @Produces
  @Dependent //do not set RequestScoped
  @PersistenceContext(unitName = "custom-persistent-unit") //see `persistence-unit name="custom-persistent-unit"` in persistence.xml
  public EntityManager entityManager;
}
