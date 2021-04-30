package com.savdev.datasource.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PropertyAccess {

  private int id;
  private long income;

  @Id
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getSalary() {
    return income;
  }

  public void setSalary(long salary) {
    this.income = salary;
  }
}
