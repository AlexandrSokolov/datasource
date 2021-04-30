
- [Accessing Entity State](#1-accessing-entity-state)

### 1 Accessing Entity State

See also: [Which is better, field or property access?](https://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access)

##### 1.1 Field Access

The `@Id` annotation indicates not only that the id field is the persistent identifier or primary key for the entity 
but also that field access should be assumed.
JPA implementation uses reflection to read or write your entity attributes directly.

```java
@Entity
public class FieldAccess {

  @Id
  private int id;
}
```

Benefits:
- Better readability
- Omit getter or setter methods that shouldn’t be called by your application.
  For instance, we can hide the `@Version` field by not exposing a getter at all, if it is not needed.
- Flexible implementation of getter and setter methods
- No need to mark utility methods as @Transient
- Avoid bugs when working with proxies:
  
  Hibernate uses proxies for lazily fetched to-one associations 
  so that it can control the initialization of these associations.
  If you use property-based access, Hibernate initializes the attributes of the proxy object 
  when you call the getter method.
  That’s always the case if you use the proxy object in your business code.
  But quite a lot of equals and hashCode implementations access the attributes directly. 
  If this is the first time you access any of the proxy attributes, these attributes are still uninitialized.

Requirements:
- Annotating the fields, not methods.
- Public fields are disallowed.
- Getter and setter methods might or might not be present, but if they are present, they are ignored by the provider

##### 1.2 Property Access

JPA implementation calls the getter and setter methods to access your entity attributes.

`@Id` on the getter:

```java
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

  public void setSalary(long income) {
    this.income = income;
  }
}
```

**Note:** the `salary` property (will be mapped to `SALARY` column in database) is backed by the `income` field, 
which does not share the same name. 
This goes unnoticed by the provider because by specifying property access, 
we are telling the provider to ignore the entity fields and use only the getter and setter methods for naming.

Requirements:
- Getter and setter methods must present.
- Getter and setter must be either public or protected visibility. 
- The mapping annotations for a property must be on the getter method.

##### 1.3 Mixed Access

Combines field access with property access. Can be useful when:
- an entity subclass is added to an existing hierarchy that uses a different access type.
- you need to perform a simple transformation to the data when reading from or writing to the database

[Mixed access with `@Access`](accessing_entity_state/src/main/java/com/savdev/datasource/entities/MixedAccess.java)

Requirements:
- Add `@Access(AccessType.FIELD)` on class with a default access type.
- Annotate the additional field or property with the `@Access` and the opposite access type
  from what was specified at the class level:
```java
  @Access(AccessType.PROPERTY) @Column(name="custom_phone")
  protected String getPhoneNumberForDb() { ... }
```
- Marked as transient the corresponding field or property
  For example, because we are adding a persistent property to an entity 
  for which the default access type is through fields,
  the field in which the persistent property state is being stored in the entity 
  must be annotated with @Transient:
```java
  @Transient
  private String phoneNum;
```

If you omit `@Transient`:
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
The JPA would provide you with a 3 columns table:
- id 
- phone_num (using `phoneNum` field)
- custom_phone (using `@Column(name="custom_phone")` on getter)