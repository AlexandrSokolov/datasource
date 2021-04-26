package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;

import static com.savdev.datasource.entities.ParametersQueryOnRepoEntity.Persistence.COLUMN_NAME;

@Entity
@Table(name = ParametersQueryOnRepoEntity.Persistence.TABLE_NAME)
public class ParametersQueryOnRepoEntity {

  public interface Persistence {
    String ENTITY_NAME = "ParametersQueryOnRepoEntity"; //same as entity
    String TABLE_NAME = "example";
    String COLUMN_NAME = "name";
  }

  public interface Queries {
    //quite often field name is the same as param name
    String FIELD_NAME = "name";
    String PARAM_NAME = "name";
    String PARAM_NAMES = "names";
  }

  @Id
  private long id;

  @Column(name=COLUMN_NAME, unique = false, nullable = true, length = 1024)
  private String name;

  private int age;

  @Column(columnDefinition = "DATE")
  private LocalDateTime date;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String nameQ) {
    this.name = nameQ;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
