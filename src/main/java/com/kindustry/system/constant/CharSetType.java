package com.kindustry.system.constant;

import java.util.StringTokenizer;


/**
 * 
 * 获取数据库类型
 */
public enum CharSetType {  
  
  GBK {
    @Override
    public String getSubset() {
      return "GB2312,GBK,GB18030";
    }

    @Override
    public String getValue() {
      return "GB18030";
    }
  }, 
  UTF8 { 
    @Override
    public String getSubset() {
      return "UTF-8";
    }

    @Override
    public String getValue() {
      return "UTF-8";
    }
  };

  public abstract String getSubset();

  public abstract String getValue();

  
  public static String getCharSetEncoding(String charset) {
    String encoding = null;
    for (CharSetType ctype : values()) {
      StringTokenizer st = new StringTokenizer(ctype.getSubset(), ",");
      while (st.hasMoreElements()) {
        String tmpEncoding = (String) st.nextElement();
        if(tmpEncoding.equals(charset)){
          encoding = ctype.getValue();
          break ;
        }
      }
    }
    
    // 补充 列举字符的  不足
    if(encoding == null ){
      encoding = charset ; 
    }
    
    return encoding;
  }

}