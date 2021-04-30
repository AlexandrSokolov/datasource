package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="floating_point_types")
public class FloatingPointTypesEntity {

  @Id
  private Long id;

  //liquibase type: currency
  private BigDecimal money;

  //liquibase type: float
  @Column(name="float_field")
  private Float floatField;

  //liquibase type: double
  @Column(name="double_field")
  private Double doubleField;

  //liquibase type: decimal(15,4)
  @Column(name="decimal_field")
  private BigDecimal decimalField;

  //liquibase type: numeric
  @Column(name="decimal_numeric_field")
  private BigDecimal decimalNumericField;
}
