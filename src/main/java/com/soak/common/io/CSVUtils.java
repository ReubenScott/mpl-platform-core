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
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soak.common.constant.CharSetType;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * CSV操作(导出和导入)
 * 
 * @author 林计钦
 * @version 1.0 Jan 27, 2014 4:30:58 PM
 */
public class CSVUtils {
  
  protected static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);

  

  /**
   * 导入
   * 
   * @param file
   *          csv文件(路径+文件)
   * @return
   */
  public static List<String> importCsvFile(String filepath) {
    List<String> dataList = new ArrayList<String>();

    CSVReader csvReader = null;

    try {
      // importFile为要导入的文本格式逗号分隔的csv文件，提供getXX/setXX方法
      csvReader = new CSVReader(new FileReader(filepath), ',');

      if (csvReader != null) {
        // first row is title, so past
        csvReader.readNext();
        String[] csvRow = null;// row
        

//        String line = "";
//        while ((line = br.readLine()) != null) {
//          dataList.add(line);
//        }

        while ((csvRow = csvReader.readNext()) != null) {
          for (int i = 0; i < csvRow.length; i++) {
            String temp = csvRow[i];
            System.out.println(temp);

          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return dataList ;
  }

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
