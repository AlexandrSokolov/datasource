package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name="integer_types")
public class IntegerTypesEntity {

  //liquibase type: bigint
  @Id
  private Long id;

  //liquibase type: tinyint
  //`tinyint` not valid for java `Short`, only for `Byte`
  @Column(name="byte")
  private Byte byteField;

  //liquibase type: smallint
  @Column(name="small_int")
  private Short smallInt;

  //liquibase type: int
  @Column(name="number_int")
  private Integer numberInt;

  //liquibase type: bigint
  @Column(name="big_number")
  private Long bigNumber;

  //liquibase type: number
  //`number` not valid for java `Integer`, only for `BigInteger`
  @Column(name="number_big_int")
  private BigInteger numberAsNumber;
}
