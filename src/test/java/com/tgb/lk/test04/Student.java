package com.tgb.lk.test04;

import java.util.Date;

public class Student {
  private int id;

  private String name;

  private int sex;

  private int clazz;

  private Date birthday;

  private String company;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public int getClazz() {
    return clazz;
  }

  public void setClazz(int clazz) {
    this.clazz = clazz;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  @Override
  public String toString() {
    return "Student [birthday=" + birthday + ", clazz=" + clazz + ", company=" + company + ", id=" + id + ", name=" + name + ", sex=" + sex + "]";
  }

}
