package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.savdev.datasource.entities.Department.Persistence.COLUMN_NAME;

@Entity(name = Department.Persistence.ENTITY_NAME)
@Table(name = Department.Persistence.TABLE_NAME)
public class Department {

  public interface Persistence {
    String ENTITY_NAME = "Department"; //same as entity
    String TABLE_NAME = "department";
    String COLUMN_NAME = "name";
  }

  @Id
  private long id;

  @Column(name=COLUMN_NAME, unique = true, nullable = false, length = 1024)
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
