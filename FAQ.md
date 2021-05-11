
- [ID generation strategies](mapping/id_generation/README.md)
- [Entity access options](mapping/README.md#1-accessing-entity-state)
- [What if an entity subclass is added to an existing hierarchy that uses a different access type?](mapping/README.md#13-mixed-access)
- [What if you need to perform a simple transformation to the data when reading from or writing to the database?](mapping/README.md#13-mixed-access)
- [How does it work (`income` and its getter and setter)?](mapping/README.md#12-property-access):
```java
@Entity
public class SomeEntity {

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
```
- [If you omit `@Transient`](mapping/README.md#13-mixed-access):
```java
@Entity
@Access(AccessType.FIELD)
public class MixedAccess {

  @Id
  private int id;

  //@Transient
  private String phoneNum;

  @Access(AccessType.PROPERTY) @Column(name="custom_phone")
  protected String getPhoneNumberForDb() {}
}
```
- [primitive vs wrappers in the mapping?](mapping/README.md#21-primitive-types-vs-wrapper-types)
- [Date and Time Mappings](mapping/README.md#22-date-and-time-mappings)
- [Time duration](mapping/README.md#23-duration)
- [Large objects mapping](mapping/README.md#24-large-objects)
- [Timezones issue](mapping/README.md#3-timezones-issue)
- [Lazy Fetching](mapping/README.md#41-lazy-fetching)
- [Issue with lazy Fetching](mapping/README.md#42-lazy-loading-and-detachment)
  
- Types of queries
- Possible issues with SQL queries.
- Where should queries be defined? Compare options.
- Sql Injection with passing parameters.
- Modifying the resulting entity
- Support of `LIKE`, issue (jpa vs spring)
- Issues with joins (urelated, returning the collection)


#### mixed access should be enabled with `@Access` 

