package com.kindustry.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 打卡记录
 * 
 */
public class PunchRecord {

  private String uid; // 

  private String seqno; // 

  private String cardID; // 

  private String empno; // 

  private String empname; // 

  private String deptname; // varchar(10)

  private Timestamp punchTime; // 打卡时间
  

  public static void main(String[] args) {
    PunchRecord imitation = new PunchRecord();
    System.out.println("11111111111111111111111111111");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    try {
      Date tt2 = sdf.parse("2017-08-07 16:20:52");
      Timestamp v = new java.sql.Timestamp(tt2.getTime()) ;
      System.out.println(v);
//      BeanUtil.setProperty(imitation, "punchTime", new Date("2017-08-07 16:20:52"));
//      BeanUtil.setProperties(imitation, "punchTime",v);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    
    
    
//    try {
//      BeanUtils.setProperty(imitation, "punchTime", "2017-08-07 16:20:52");
//      System.out.println(imitation.getPunchTime());
//    } catch (IllegalAccessException e) {
//      e.printStackTrace();
//    } catch (InvocationTargetException e) {
//      e.printStackTrace();
//    }
//    Field[] fields = imitation.getClass().getDeclaredFields();  
//    for (Field field : fields) {  
//        String classType = field.getType().toString();  
//        int lastIndex = classType.lastIndexOf(".");  
//        classType = classType.substring(lastIndex + 1);  
//        System.out.println("fieldName：" + field.getName() + ",type:" + classType );  
//    }  
//    
    
  }


  private String location; // 开始时间

  private Boolean state; // tinyint(1)0:通过,1:不通过;

  private String remark; // 描述

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getSeqno() {
    return seqno;
  }

  public void setSeqno(String seqno) {
    this.seqno = seqno;
  }

  public String getCardID() {
    return cardID;
  }

  public void setCardID(String cardID) {
    this.cardID = cardID;
  }

  public String getEmpno() {
    return empno;
  }

  public void setEmpno(String empno) {
    this.empno = empno;
  }

  public String getEmpname() {
    return empname;
  }

  public void setEmpname(String empname) {
    this.empname = empname;
  }

  public String getDeptname() {
    return deptname;
  }

  public void setDeptname(String deptname) {
    this.deptname = deptname;
  }

  public Timestamp getPunchTime() {
    return punchTime;
  }

  public void setPunchTime(Timestamp punchTime) {
    this.punchTime = punchTime;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean getState() {
    return state;
  }

  public void setState(Boolean state) {
    this.state = state;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  
}
