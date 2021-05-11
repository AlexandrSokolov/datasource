package com.savdev.datasource.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "table_id")
public class TableIdEntity {

  public static final String TABLE_GENERATOR = "id_table_gen";

  @Id
  @TableGenerator(name=TABLE_GENERATOR,
    table="id_gen", //`id_table_gen` default table name
    pkColumnName="gen_name", //`sequence_name` default column name for a sequence name
    valueColumnName="gen_value", //`next_val` default column name for a sequence next value
    pkColumnValue = "custom_table_id",
    allocationSize = 100) //table name of the entity - `table_id` is used as a default
  @GeneratedValue(strategy = GenerationType.TABLE, generator=TABLE_GENERATOR)
  private Long id;

  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
