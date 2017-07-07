package com.kindustry.common.io;

import org.junit.Test;

import com.kindustry.common.io.IOHandler;

public class IOHandlerTest {

  @Test
  public void testGetCharSetEncoding() {
    String path = "E:/ftpdata/P_063_CBOD_ECCIFIDI_20170413.del";
    String  code = IOHandler.getCharSetEncoding(path);
    System.out.println(code);
    
  }
  

//  @Test
  public void testGetCharSetEncodingByYRL() {
    String path = "E:/ftpdata/P_063_CBOD_CMEMPEMP_20160616.del";
//    String  code = IOHandler.codeString(path);
//    URL url = CreateStationTreeModel.class.getResource("/resource/" + "配置文件");  
//    URLConnection urlConnection = url.openConnection();  
//    InputStream inputStream = urlConnection.getInputStream();  
//    String charsetName = getFileEncode(url);  
//    System.out.println(charsetName);  
  }

}
