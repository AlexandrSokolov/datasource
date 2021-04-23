package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.savdev.datasource.entities.NativeOnRepository.Persistence.COLUMN_NAME;

@Entity
@Table(name = NativeOnRepository.Persistence.TABLE_NAME)
public class NativeOnRepository {

  public interface Persistence {
    String ENTITY_NAME = "JpqlOnRepository"; //same as entity
    String TABLE_NAME = "example";
    String COLUMN_NAME = "name";
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
