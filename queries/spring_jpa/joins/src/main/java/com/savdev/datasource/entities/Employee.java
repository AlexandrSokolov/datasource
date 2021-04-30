package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.Collection;

import static com.savdev.datasource.entities.Employee.Persistence.COLUMN_NAME;

@Entity(name = Employee.Persistence.ENTITY_NAME)
@Table(name = Employee.Persistence.TABLE_NAME)
@NamedQueries({
  @NamedQuery(
    name = Employee.Queries.WITH_JOIN,
    //you can use either `JOIN` or `INNER JOIN` syntax:
    query = "SELECT e.id, p.number " +
      "FROM " + Employee.Persistence.ENTITY_NAME + " e " +
      "   JOIN e.phones p"),
  @NamedQuery(
    name = Employee.Queries.WITH_LEFT_OUTER_JOIN,
    query = "SELECT e " +
      "FROM " + Employee.Persistence.ENTITY_NAME + " e " +
      "   LEFT JOIN e.phones p")
//  @NamedQuery(
//    name = Employee.Queries.JOIN_WITH_WHERE,
//    query = "SELECT e " +
//      "FROM " + Employee.Persistence.ENTITY_NAME + " e, " + Phone.Persistence.ENTITY_NAME + " p " +
//      "WHERE p = e.phones")
})
public class Employee {

  public interface Persistence {
    String ENTITY_NAME = "Employee"; //same as entity
    String TABLE_NAME = "employee";
    String COLUMN_NAME = "name";
  }

  public interface Queries {
    String WITH_JOIN = Employee.Persistence.ENTITY_NAME + ".withJoin";
    String WITH_LEFT_OUTER_JOIN = Employee.Persistence.ENTITY_NAME + ".withLeftOuterJoin";
    String JOIN_WITH_WHERE = Employee.Persistence.ENTITY_NAME + ".joinWithWhere";
  }

  @Id
  private long id;

  @Column(name=COLUMN_NAME, unique = false, nullable = true, length = 1024)
  private String name;

  @ManyToOne
  private Department department;

  @OneToMany
  private Collection<Phone> phones = new ArrayList<Phone>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Collection<Phone> getPhones() {
    return phones;
  }

  public void setPhones(Collection<Phone> phones) {
    this.phones = phones;
  }
}
