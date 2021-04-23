package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import static com.savdev.datasource.entities.NativeOnEntity.Persistence.COLUMN_NAME;

@Entity
@Table(name = NativeOnEntity.Persistence.TABLE_NAME)
@NamedNativeQueries({
  @NamedNativeQuery(
    name = NativeOnEntity.Queries.NATIVE_QUERY,
    query = "SELECT * FROM " + NativeOnEntity.Persistence.TABLE_NAME, resultClass = NativeOnEntity.class),
})
public class NativeOnEntity {

  public interface Persistence {
    String ENTITY_NAME = "NativeOnEntity"; //same as entity
    String TABLE_NAME = "example";
    String COLUMN_NAME = "name";
  }

  /**
   * To use with Spring Data Jpa, query name must start with entity name, then dot, then method name,
   * for instance the following methods will be invoked for queries:
   *
   */
  public interface Queries {
    String NATIVE_QUERY = Persistence.ENTITY_NAME + ".findAllByNative";
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
