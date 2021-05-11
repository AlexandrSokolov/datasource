package com.savdev.datasource.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

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

  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
