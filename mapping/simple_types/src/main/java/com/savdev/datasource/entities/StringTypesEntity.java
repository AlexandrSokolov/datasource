package com.savdev.datasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="string_types")
public class StringTypesEntity {

  @Id
  private Long id;

  //liquibase type: char
  @Column(name="char_field")
  private Character charField;

  //liquibase type: nchar
  @Column(name="nchar_field")
  private Character ncharField;

  //liquibase type: varchar
  @Column(name="varchar_field")
  private String varcharField;

  //liquibase type: nvarchar
  @Column(name="nvarchar_field")
  private String nvarcharField;

  //for text files, for instance:
  //liquibase type: clob
  // without `@Lob` you get org.hibernate.tool.schema.spi.SchemaManagementException validation exception:
  // `found [longtext (Types#LONGVARCHAR)], but expecting [varchar(255) (Types#VARCHAR)]`
  @Column(name="clob_field")
  @Lob
  private String clobField;


}
