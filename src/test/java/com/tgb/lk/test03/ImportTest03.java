package com.tgb.lk.test03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.tgb.lk.util.ExcelUtil;

public class ImportTest03 {
  public static void main(String[] args) {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream("d:\\success3.xls");
      ExcelUtil<StudentVO> util = new ExcelUtil<StudentVO>(
          StudentVO.class);// 创建excel工具类
      List<StudentVO> list = util.importExcel("学生信息0", fis);// 导入
      System.out.println(list);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
