package com.tgb.lk.test04;

import com.tgb.lk.util.ExcelVOAttribute;

public class StudentVO {

  @ExcelVOAttribute(name = "姓名", column = "B", isExport = true, prompt = "姓名为必填项哦!")
  private String name;

  @ExcelVOAttribute(name = "性别", column = "C", combo = { "男", "女" })
  private String sex;

  @ExcelVOAttribute(name = "班级", column = "D", combo = { "五期提高班", "六期提高班", "七期提高班" })
  private String clazz;

  @ExcelVOAttribute(name = "生日", column = "E")
  private String birthday;

  @ExcelVOAttribute(name = "公司", column = "F")
  private String company;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getClazz() {
    return clazz;
  }

  public void setClazz(String clazz) {
    this.clazz = clazz;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  @Override
  public String toString() {
    return "StudentVO [birthday=" + birthday + ", clazz=" + clazz + ", company=" + company + ", name=" + name + ", sex=" + sex + "]";
  }
}
