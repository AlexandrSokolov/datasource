package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="integer_types")
public class IntegerTypesEntity {

  @Id
  private Integer id;

  //liquibase type: tinyint
  @Column(name="byte")
  private Byte byteField;

  //liquibase type: tinyint
  @Column(name="smallest_int")
  private Short smallestInt;

  //liquibase type: smallint
  @Column(name="small_int")
  private Short smallInt;

  //liquibase type: number
  private Integer number1;

  //liquibase type: int
  private Integer number2;

  //liquibase type: bigint
  @Column(name="big_number")
  private Long bigNumber;
}
