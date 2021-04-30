package com.savdev.datasource.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Access(AccessType.FIELD)
public class MixedAccess {

  public static final String LOCAL_AREA_CODE = "613";

  @Id
  private int id;

  @Transient
  private String phoneNum;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  @Access(AccessType.PROPERTY) @Column(name="custom_phone")
  protected String getPhoneNumberForDb() {
    if (null != phoneNum && phoneNum.length() == 10)
      return phoneNum;
    else
      return LOCAL_AREA_CODE + phoneNum;
  }

  protected void setPhoneNumberForDb(String num) {
    if (num.startsWith(LOCAL_AREA_CODE))
      phoneNum = num.substring(LOCAL_AREA_CODE.length());
    else
      phoneNum = num;
  }
}
