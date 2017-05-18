package com.soak.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.soak.common.constant.CharSetType;

/**
 * CSV操作(导出和导入)
 * 
 * @author 林计钦
 * @version 1.0 Jan 27, 2014 4:30:58 PM
 */
public class ExportUtility {
  
  protected static final Logger logger = LoggerFactory.getLogger(ExportUtility.class);


  /***
   * 查询到处数据为 CSV
   * 
   * @param filePath
   *          CSV DEL 文件路径
   * 
   * @param separator
   *          字段分隔符 0X1D : 29 ; 逗号 (char)44
   * 
   * @param quotechar
   *          引用字符 空字符 '\0' (char)0
   * 
   */
  public static void exportCSV(String filePath, List<String[]> dataList) {
    try {
      OutputStreamWriter outWriter = new OutputStreamWriter(new FileOutputStream(filePath), CharSetType.GBK.getValue());
      CSVWriter writer = new CSVWriter(outWriter, ',', '"', "\r\n");
      writer.writeAll(dataList);
      writer.close();// 关闭文件流
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      
    }
  }


  public static void main(String[] args) {
    String[] str = { "省", "市", "区", "街", "路", "里", "幢", "村", "室", "园", "苑", "巷", "号" };
    File inFile = new File("C://in.csv"); // 读取的CSV文件
    File outFile = new File("C://out.csv");// 写出的CSV文件
    String inString = "";
    String tmpString = "";
    try {
      BufferedReader reader = new BufferedReader(new FileReader(inFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
      while ((inString = reader.readLine()) != null) {
        for (int i = 0; i < str.length; i++) {
          tmpString = inString.replace(str[i], "," + str[i] + ",");
          inString = tmpString;
        }
        writer.write(inString);
        writer.newLine();
      }
      reader.close();
      writer.close();
    } catch (FileNotFoundException ex) {
      System.out.println("没找到文件！");
    } catch (IOException ex) {
      System.out.println("读写文件出错！");
    }
  }
  
  
}
