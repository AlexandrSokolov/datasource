
- [Accessing Entity State](#1-accessing-entity-state)
- [Simple Types Mapping](#2-simple-types-mapping)

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

### 2 Simple Types Mapping

##### 2.1 Primitive types vs wrapper types.

**Use wrappers all the time!**

- If they are nullable in the database, then use wrappers. 
- If they are not nullable, and you use wrappers, 
  then you'll get an exception if you try and insert a null into the database.
  **Having an inconsistent non-null value in a property is worse than NullPointerException**, 
  as the lurking bug is harder to track: more time will pass since the code is written until a problem is detected.
- If your data model doesn't dictate it, then go for a convention, use wrappers all the time. 
  That way people don't have to think, or decide that a value of 0 means null.
  
See also: [Using wrapper or primitive types in the mapping](https://stackoverflow.com/questions/7506802/using-wrapper-integer-class-or-int-primitive-in-hibernate-mapping)

##### 2.2 Date and Time Mappings

Use new Java 8 date and time types: `java.time.LocalDateTime`, `java.time.LocalDate`, `java.time.LocalTime`.

With old types for storing both date and time you can use:
- `java.util.Date`/`java.util.Calendar`
- `java.sql.Date`/`java.sql.Timestamp`

With old types, if you want to store only date or time, but not both, you must use: `@Temporal(TemporalType.TIME)`:
```java
  @Column(name="java_only_time")
  @Temporal(TemporalType.TIME)
  private Date javaOnlyTime;
```
Note: `Calendar` does not support only `TIME`!

##### 2.3 Duration

JPA 2.2 didn’t add support for java.time.Duration. You need a custom `javax.persistence.AttributeConverter` converter.

See: 
- [DurationConverter](simple_types/src/main/java/com/savdev/datasource/entities/converters/DurationConverter.java)
- [JPA Tips: How to map a Duration attribute](https://thorben-janssen.com/jpa-tips-map-duration-attribute/)

##### 2.4 Large Objects

You might get `OutOfMemoryException` when you map your files as:
```java
  @Basic(fetch= FetchType.LAZY)
  @Lob
  @Column(name="picture")
  private byte[] picture;
```

You need to use streams and define a mapping as `java.sql.Blob`/`java.sql.Clob`:
```java
  @Basic(fetch= FetchType.LAZY)
  @Lob
  private Clob clob;
```

See [LargeObjectsEntityTest](simple_types/src/test/java/com/savdev/datasource/entities/LargeObjectsEntityTest.java)

TODO: the topic is quire complicated. The current not resolved issues:
- https://stackoverflow.com/questions/3677380/proper-hibernate-annotation-for-byte
- Writing, reading clobs, blobs without hibernate
  https://stackoverflow.com/questions/6278894/how-to-create-a-java-sql-blob-object-in-java-se-1-5-0-with-a-byte-input
- Writing, reading clobs, blobs with hibernate
  Blob data = getSession().getLobHelper().createBlob(fis, file.length());
  https://stackoverflow.com/questions/20614973/read-write-blob-data-in-chunks-with-hibernate
- Try to use InputStream directly in the entity, reusable input stream!!!
- Mysql has 4 different types for blobs: TINYBLOB, BLOB, MEDIUMBLOB, LONGBLOB
  See 
  https://www.tutorialspoint.com/What-is-the-maximum-length-of-data-we-can-put-in-a-BLOB-column-in-MySQL
  https://stackoverflow.com/questions/3503841/jpa-mysql-blob-returns-data-too-long
```text
       0 < length <=      255  -->  `TINYBLOB`
     255 < length <=    65535  -->  `BLOB`
   65535 < length <= 16777215  -->  `MEDIUMBLOB`
16777215 < length <=    2³¹-1  -->  `LONGBLOB`
```
  See also a validation topic:
  https://stackoverflow.com/questions/67383238/schema-validation-for-java-sql-blob-the-right-expected-but-a-wrong-found-type
- see also http://www.java2s.com/Questions_And_Answers/JPA/Data-Type/InputStream.htm

### 3 Timezones issue

It is a good practice to set timezone explicitly when work with a datasource!

If you configure url to the database explicitly, include time zone.

Here is the configuration for WildFly and MySql server:

`jdbc:mysql://some.host.com:3306/db_name?useUnicode=true;characterEncoding=utf8;characterResultSets=utf8;serverTimezone=Europe/Berlin`

If you use standalone application you can set it with `hibernate.jdbc.time_zone`:
```xml
<persistence>
    <persistence-unit name="my-persistence-unit">
        ...
        <properties>
            <property name="hibernate.jdbc.time_zone" value="UTC"/>
            ...
        </properties>
    </persistence-unit>
</persistence>
```

Via the Spring Boot `application.properties` file:

`spring.jpa.properties.hibernate.jdbc.time_zone=UTC`

### 4 Fetching strategies

##### 4.1 Lazy Fetching

- lazy fetching
- lazy loading
- deferred loading
- on-demand fetching
- just-in-time reading
- indirection

They all mean pretty much the same thing, 
which is just that some data might not be loaded when the object is initially read from the database, 
but will be fetched only when referenced or accessed.

The only times when lazy loading of a basic mapping should be considered are:
- when there are many columns in a table (for example, dozens or hundreds) or 
- when the columns are large (for example, very large character strings or byte strings). 
  
It could take significant resources to load the data, 
and not loading it could save quite a lot of effort, time, and resources. 
**Unless either of these two cases is true, in the majority of cases 
lazily fetching a subset of object attributes will end up being more expensive than eagerly fetching them.**

By default `EAGER` fetching is used for a basic mapping. To overwrite it:

```java
  @Basic(fetch= FetchType.LAZY)
  @Column(name="clob_field")
  @Lob
  private String clobField;
```

##### 4.2 Lazy loading and detachment
You must understand the connection between lazy loading and detachment. 

TODO

