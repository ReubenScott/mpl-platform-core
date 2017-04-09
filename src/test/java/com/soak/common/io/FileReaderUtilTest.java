package com.soak.common.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class FileReaderUtilTest {

  @Before
  public void setUp() throws Exception {
  }

   @Test
  public void testcodeString() throws Exception {
     // String sql = "insert into CBOD_ECCMRAMR values (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?,?,?,?)";
     // 数据库文件 分割符号 0X1D : 29
     // split = new String(new byte[] { 29 });
     
 
     
    String filename = "D:\\home\\20160318\\YKJD_LN_DUEBILL.del";
//    YKJD_LN_DUEBILL
    // filename ="D:\\load.SQL";
    filename = "E:\\ftpdata\\P_063_CBOD_GLGLGHTD_20150701.del";
    // filename = "D:\\home\\portal\\soooner\\logs\\npvr\\npvr.log";

    System.out.println(IOHandler.getCharSetEncoding(filename));
  }

//  @Test
  public void testReadFileByBytes() {
    String filename = "D:\\home\\20160318\\YKJD_LN_DUEBILL.del";
    byte[] bt;
    try {
      bt = IOHandler.readByteArray(new FileInputStream(filename));
      // String encode = IOHandler.getCharSetEncoding(new File(filename));

      List<byte[]> list = IOHandler.splitBytes(bt, (byte) 10);
      List<String[]> result = new ArrayList<String[]>();

      for (byte[] lineByte : list) {
        String line = new String(lineByte, "GBK");
        System.out.println(line);
      }
      // result.add(new String(IOHandler.replace(lineByte, (byte) 29, (byte) 44), encode).split(","));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

  }

}
