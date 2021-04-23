package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static com.savdev.datasource.entities.JpqlOnEntity.Persistence.COLUMN_NAME;

@Entity
@Table(name = JpqlOnEntity.Persistence.TABLE_NAME)
@NamedQueries({
  @NamedQuery(
    name = JpqlOnEntity.Queries.JPQL_QUERY,
    query = "SELECT e FROM " + JpqlOnEntity.Persistence.ENTITY_NAME + " e")
})
public class JpqlOnEntity {

  public interface Persistence {
    String ENTITY_NAME = "JpqlOnEntity"; //same as entity
    String TABLE_NAME = "example";
    String COLUMN_NAME = "name";
  }

  /**
   * To use with Spring Data Jpa, query name must start with entity name, then dot, then method name,
   * for instance the following methods will be invoked for queries:
   *
   */
  public interface Queries {
    String JPQL_QUERY = Persistence.ENTITY_NAME + ".findAllByJpql";
  }

  @Id
  private long id;

  @Column(name=COLUMN_NAME, unique = false, nullable = true, length = 1024)
  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
