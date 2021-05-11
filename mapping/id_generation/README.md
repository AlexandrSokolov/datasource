
Id generation using database features:
- [Using sequences](#id-generation-using-sequences)
- [Using sequences when database does not support them](#id-generation-using-sequences-in-case-when-database-does-not-support-sequences)
- [Using auto-incremented columns](#id-generation-using-auto-incremented-columns)

Id generation programmatically on the server-side:
- [Using a table](#id-generation-using-a-table)

- [Auto id generation](#automatic-id-generation)

### Id generation using sequences

It uses a database sequence to generate unique values.
The best option if you use Hibernate, and your database supports sequences. 

It requires additional select statements to get the next value from a database sequence.
If your application has to persist a huge number of new entities, 
you can use some Hibernate specific optimizations to reduce the number of statements.

Advantages: it allows you using JDBC batching optimisation.
Disadvantages: not very portable. MySql for instance does not support sequences.

### Id generation using sequences in case when database does not support sequences

Note: the solution has the same problems as [id generation using a table](#id-generation-using-a-table)
although, on Java `GenerationType.SEQUENCE` is used.

See [How to replace the TABLE identifier generator with either SEQUENCE or IDENTITY in a portable way](https://vladmihalcea.com/how-to-replace-the-table-identifier-generator-with-either-sequence-or-identity-in-a-portable-way/)

Hibernate uses `SequenceStyleGenerator` regardless of the database. 
If a database does not support sequences, Hibernate emulates them using a `hibernate_sequence` table:
```java
@Entity
@Table(name = "sequence_id_via_table")
public class SequenceViaTableIdEntity {

  public static final String SEQUENCE_GENERATOR = "id_sequence_gen";

  @Id
  @SequenceGenerator(
    name = SEQUENCE_GENERATOR,
    //`hibernate_sequence` - default sequence name. table with the same name as a sequence name is expected:
    sequenceName = "hibernate_sequence",
    initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR)
  private Long id;
}
```
Sequence table:
```sql
CREATE TABLE hibernate_sequence (next_val BIGINT NULL);
```

Note: from a point of logic it is very similar to id generation via table:
```sql
select next_val as id_val from hibernate_sequence for update;
update hibernate_sequence set next_val= 101 where next_val=51
insert into sequence_id_via_table (name, id) values ('test name', 1);
insert into sequence_id_via_table (name, id) values ('test name', 2);
insert into sequence_id_via_table (name, id) values ('test name', 3);
```

### Id generation using auto-incremented columns

It relies on an auto-incremented database column and lets the database generate a new value with each insert operation.
Use it with databases, without sequences support (MySql) and in case JDBC batching optimisation is not needed.

Advantages: 
From a database point of view, this is very efficient because the auto-increment columns are highly optimized, 
and it doesn’t require any additional statements.

Disadvantage:
Hibernate requires a primary key value for each managed entity and therefore has to perform the insert statement immediately. 
This prevents it from using different optimization techniques like JDBC batching.

During insertion, id value is not sent explicitly:
```sql
CREATE TABLE identity_id (
    id BIGINT AUTO_INCREMENT NOT NULL, 
    name VARCHAR(512) NULL, 
    CONSTRAINT PK_IDENTITY_ID PRIMARY KEY (id));

insert into identity_id (name) values ('test name');
```

### Id generation using a table

It simulates a sequence by storing and updating its current value in a database table.
A better approach is [to emulate sequences with a table](#id-generation-using-sequences-in-case-when-database-does-not-support-sequences)

Advantage: it is the most flexible and portable way to generate identifiers.

Disadvantages: 
It requires the use of pessimistic locks which put all transactions into a sequential order. 
This slows down your application.

The JPA provider allocates a list of IDs (`tbl.gen_value=50`):
```sql
select tbl.gen_value from id_gen tbl where tbl.gen_name='custom_table_id' for update;
```
Then it updates the upper boundary:
```sql
update id_gen set gen_value=100  where gen_value=50 and gen_name='custom_table_id'
```
Now during inserts, the JPA provider uses ID from the pool, without requesting for `id_gen` table:
```sql
insert into table_id (name, id) values ('test name', 1);
insert into table_id (name, id) values ('test name', 2);
insert into table_id (name, id) values ('test name', 3);
```
### Automatic ID generation

Do not use auto generation strategy. Choose it explicitly.

`AUTO` is the default strategy for `@GeneratedValue`. The JPA provider will choose an appropriate strategy for the underlying database:
```java
@Entity
public class Admin {

    @Id
    @GeneratedValue
    private Long id;
}
```
The problem, that the JPA provider might change its logic. 
For instance Hibernate for MySql in old versions used auto-incremented columns (`GenerationType.IDENTITY`).
In Hibernate 5, it was changed to `GenerationType.TABLE`.
Then again, it was changed and by default `GenerationType.IDENTITY` will be used.

See [Problems with GenerationType.AUTO in Hibernate 5](https://thorben-janssen.com/5-things-you-need-to-know-when-using-hibernate-with-mysql/#2_Mappings_Problems_with_GenerationTypeAUTO_in_Hibernate_5)

### UUID generation

You can set your UUID programmatically before persisting data. This way you can still use batch insertion with MySQL.

Advantage:

Disadvantage: 
setting the UUID manually results in an additional select query for each insert. 
With UUID, Hibernates queries the database to check for the existing record. 
If it doesn’t exist it inserts, otherwise it updates.