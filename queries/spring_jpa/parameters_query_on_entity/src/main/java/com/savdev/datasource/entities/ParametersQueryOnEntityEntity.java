package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Persistence.COLUMN_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.FIELD_AGE;
import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.FIELD_NAME;
import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.PARAM_AGE;
import static com.savdev.datasource.entities.ParametersQueryOnEntityEntity.Queries.PARAM_NAME;

/**
 * Additionally to single-valued expressions used in queries in examples,
 * You can use operators for collection expressions:
 *  - Is empty: author.books IS EMPTY
 *    Restricts the query result to all Authors that don’t have any associated Book entities.
 *    You can negate the operator (IS NOT EMPTY) to restrict the query result to all Authors with associated Book entities.
 *  - Size: size(author.books) > 2
 *    Restricts the query result to all Authors who are associated with more than 2 Book entities.
 *  - Member of: :myBook member of author.books
 *    Restricts the query result to all Authors who are associated with a specific Book entity.
 */
@Entity
@Table(name = ParametersQueryOnEntityEntity.Persistence.TABLE_NAME)
@NamedQueries({
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_EQUAL,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_NAME + " = :" + PARAM_NAME),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_NOT_EQUAL,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_NAME + " <> :" + PARAM_NAME),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_GREATER_THAN,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_AGE + " > :" + PARAM_AGE),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_SMALLER_THAN,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_AGE + " < :" + PARAM_AGE),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_BETWEEN,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_AGE + " BETWEEN ?1 AND ?2"),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_LIKE,
    //With JPA you can't have the % in the NamedQuery, but you can have it in the value you assign the
    //With spring query, you can define it as: "WHERE " + FIELD_NAME + " LIKE %:" + PARAM_NAME + "%"
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_NAME + " LIKE :" + PARAM_NAME),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_IS_NULL,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_NAME + " IS NULL"),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_IS_NOT_NULL,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_NAME + " IS NOT NULL"),
  @NamedQuery(
    name = ParametersQueryOnEntityEntity.Queries.BY_IN,
    query = "SELECT e FROM " + ParametersQueryOnEntityEntity.Persistence.ENTITY_NAME + " e " +
      "WHERE " + FIELD_NAME + " IN :" + ParametersQueryOnEntityEntity.Queries.PARAM_NAMES)
})
public class ParametersQueryOnEntityEntity {

  public interface Persistence {
    String ENTITY_NAME = "ParametersQueryOnEntityEntity"; //same as entity
    String TABLE_NAME = "example";
    String COLUMN_NAME = "name";
  }

  public interface Queries {
    //quite often field name is the same as param name
    String FIELD_NAME = "name";
    String FIELD_AGE = "age";
    String PARAM_NAME = "name";
    String PARAM_AGE = "age";
    String PARAM_NAMES = "names";
    String BY_EQUAL = Persistence.ENTITY_NAME + ".byEqual";
    String BY_NOT_EQUAL = Persistence.ENTITY_NAME + ".byNotEqual";
    String BY_GREATER_THAN = Persistence.ENTITY_NAME + ".byGreaterThan";
    String BY_SMALLER_THAN = Persistence.ENTITY_NAME + ".bySmallerThan";
    String BY_BETWEEN = Persistence.ENTITY_NAME + ".byBetween";
    String BY_LIKE = Persistence.ENTITY_NAME + ".byLike";
    String BY_IS_NULL = Persistence.ENTITY_NAME + ".byIsNull";
    String BY_IS_NOT_NULL = Persistence.ENTITY_NAME + ".byIsNotNull";
    String BY_IN = Persistence.ENTITY_NAME + ".byIn";
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
