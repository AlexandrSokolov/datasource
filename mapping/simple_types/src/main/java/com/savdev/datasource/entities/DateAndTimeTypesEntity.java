package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name="date_time_types")
public class DateAndTimeTypesEntity {

  @Id
  private Long id;

  ///////   new java 8 date time api ///////////////////
  //liquibase: datetime
  @Column(name="instant_field")
  private Instant instantField;

  //liquibase: datetime
  @Column(name="offset_date_time")
  private OffsetDateTime offsetDateTime;

  //liquibase: datetime
  @Column(name="zoned_date_time")
  private ZonedDateTime zonedDateTime;

  //liquibase: datetime
  @Column(name="local_date_time")
  private LocalDateTime localDateTime;

  //liquibase: date
  @Column(name="local_date")
  private LocalDate localDate;

  //liquibase: time
  @Column(name="local_time")
  private LocalTime localTime;

  ///////   old java 8 date time api ///////////////////
  //liquibase: datetime
  @Column(name="java_date")
  private Date javaDate;

  //liquibase: date
  @Column(name="java_only_date")
  private Date javaOnlyDate;

  //liquibase: datetime
  @Column(name="sql_date")
  private java.sql.Date sqlDate;

  //liquibase: timestamp
  @Column(name="sql_timestamp")
  private Timestamp sqlTimestamp;
}
