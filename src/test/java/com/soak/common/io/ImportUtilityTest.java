package com.soak.common.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ImportUtilityTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testcodeString() {
    String filepath = "E:/bufmeta.csv";
    List<String[]> datas = ImportUtility.importCsv(filepath);
    for(String[] line : datas){
      for(String column : line){
        System.out.print(column + " ");
      }
      System.out.println("");
    }

  }

  // @Test
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
