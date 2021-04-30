package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = Phone.Persistence.ENTITY_NAME)
@Table(name = Phone.Persistence.TABLE_NAME)
public class Phone {

  public interface Persistence {
    String ENTITY_NAME = "Phone"; //same as entity
    String TABLE_NAME = "phone";
    String COLUMN_NUMBER = "number";
  }

  @Id
  private long id;

  @Column(name= Persistence.COLUMN_NUMBER, unique = true, nullable = false, length = 1024)
  private String number;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String name) {
    this.number = name;
  }
}
