package com.savdev.datasource.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.sql.Blob;
import java.sql.Clob;

@Entity
@Table(name="large_objects")
public class LargeObjectsEntity {

  @Id
  private Long id;

  //everything gets loaded in memory, you might get OutOfMemoryException
//  @Basic(fetch= FetchType.LAZY)
//  @Lob
//  @Column(name="picture")
//  private byte[] picture;

  //everything gets loaded in memory, you might get OutOfMemoryException
  @Basic(fetch= FetchType.LAZY)
  @Lob
  private String content;

  //loaded with streaming, not the whole object is loaded at once
  @Column(name="clob_field")
  @Basic(fetch= FetchType.LAZY)
  @Lob
  private Clob clob;

//  //loaded with streaming, not the whole object is loaded at once
//  @Column(name="blob_field")
//  @Basic(fetch= FetchType.LAZY)
//  @Lob
//  private Blob blob;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Clob getClob() {
    return clob;
  }

  public void setClob(Clob clob) {
    this.clob = clob;
  }

//  public Blob getBlob() {
//    return blob;
//  }
//
//  public void setBlob(Blob blob) {
//    this.blob = blob;
//  }
}
