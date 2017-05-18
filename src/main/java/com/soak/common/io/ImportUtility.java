package com.soak.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.soak.common.constant.CharSetType;

public class ImportUtility {

  protected static final Logger logger = LoggerFactory.getLogger(ImportUtility.class);

  /***
   * CSV（默认以逗号分割的） 文件入库 默认 引用字符 '"' 默认从首行开始导入
   */
  public static List<String[]> importCsv(String filepath) {
    return importCsv(filepath, (char) 44);
  }

  public static List<String[]> importCsv(String filepath, char separator) {
    return importCsv(filepath, separator, '"');
  }

  public static List<String[]> importCsv(String filepath, char separator, char quotechar) {
    return importCsv(filepath, separator, quotechar, 0);
  }

  /**
   * 导入
   * 
   * @param file
   *          csv文件(路径+文件)
   * @return
   */
  public static List<String[]> importCsv(String filepath, char separator, char quotechar, int skipLines) {
    List<String[]> dataList = new ArrayList<String[]>();
    try {
      // TODO 检查文件编码 不靠谱
      String encode = IOHandler.getCharSetEncoding(filepath);
      if (!encode.equals(CharSetType.GBK.getValue())) {
        logger.warn("IOHandler get File : {}  character set  : {}  incorrect", filepath, encode);
        encode = CharSetType.GBK.getValue(); //
      }
      dataList = importCsv(new FileInputStream(filepath), CharSetType.GBK, separator, quotechar, skipLines);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("function importCsv filepath : [{}] Exception: {}", new Object[] { filepath, e.toString() });
    }

    return dataList;
  }

  public List<String[]> importCsv(InputStream in, char separator, char quotechar, int skipLines) {
    return importCsv(in, CharSetType.GBK, separator, quotechar, skipLines);
  }

  public static List<String[]> importCsv(InputStream in, CharSetType encoding, char separator, char quotechar, int skipLines) {
    List<String[]> dataList = new ArrayList<String[]>();
    CSVReader reader = null;

    try {
      reader = new CSVReader(new BufferedReader(new InputStreamReader(in, encoding.getValue())), separator, quotechar, skipLines);
      reader.readNext();
      String[] csvRow = null;// row
      while ((csvRow = reader.readNext()) != null) {
        dataList.add(csvRow);
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return dataList;
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