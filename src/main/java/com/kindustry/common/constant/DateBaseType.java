package com.kindustry.common.constant;

/**
 * 
 * 获取数据库类型
 */
public enum DateBaseType {
  
  DB2 { 
    @Override
    public String getName() {
      return "DB2";
    }

    @Override
    public String getValue() {
      return "DB2";
    }
  }, 
  ORACLE { 
    @Override
    public String getName() {
      return "ORACLE";
    }

    @Override
    public String getValue() {
      return "ORACLE";
    }
  },
  PostgreSQL {
	    @Override
	    public String getName() {
	      return "PostgreSQL";
	    }

	    @Override
	    public String getValue() {
	      return "PostgreSQL";
	    }
  },
  MySQL {
    @Override
    public String getName() {
      return "MySQL";
    }

    @Override
    public String getValue() {
      return "MySQL";
    }
  };

  public abstract String getName();

  public abstract String getValue();

  
  public static DateBaseType getDateBaseType(String typeValue) {
    for (DateBaseType ctype : values()) {
      if (typeValue.startsWith(ctype.getValue())) {
        return ctype;
      }
    }
    return null;
  }

}