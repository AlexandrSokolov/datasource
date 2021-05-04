package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="date_time_types")
public class DateAndTimeTypesEntity {

  @Id
  private Long id;

  ///////   new java 8 date time api ///////////////////
  //liquibase: datetime
  @Column(name="local_date_time")
  private LocalDateTime localDateTime;

  //liquibase: date
  @Column(name="local_date")
  private LocalDate localDate;

  //liquibase: time
  @Column(name="local_time")
  private LocalTime localTime;

  private Duration duration;

  //liquibase: datetime
  @Column(name="instant_field")
  private Instant instantField;

  //liquibase: datetime
  @Column(name="offset_date_time")
  private OffsetDateTime offsetDateTime;

  //liquibase: datetime
  @Column(name="zoned_date_time")
  private ZonedDateTime zonedDateTime;

  ///////   old java 8 date time api ///////////////////
  //liquibase: datetime
  @Column(name="calendar_field")
  private Calendar calendar;

  //liquibase: date
  @Column(name="calendar_date_field")
  private Calendar calendarDate;

  //liquibase: time
  //with `@Temporal(TemporalType.TIME)` it does not work.
  // Calendar cannot persist time only!
  //@Column(name="calendar_time_field")
  //private Calendar calendarTime;

  //liquibase: datetime
  @Column(name="java_date")
  private Date javaDate;

  //liquibase: date
  @Column(name="java_only_date")
  private Date javaOnlyDate;

  //liquibase: date
  // without `@Temporal(TemporalType.TIME)` we get a schema-validation exception:
  //found [time (Types#TIME)], but expecting [datetime(6) (Types#TIMESTAMP)]
  @Column(name="java_only_time")
  @Temporal(TemporalType.TIME)
  private Date javaOnlyTime;

  //liquibase: datetime
  @Column(name="sql_date")
  private java.sql.Date sqlDate;

  //liquibase: timestamp
  @Column(name="sql_timestamp")
  private Timestamp sqlTimestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }
}
