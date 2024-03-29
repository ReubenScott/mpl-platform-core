package com.kindustry.common.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportTest02 {
  public static void main(String[] args) {
    // 初始化数据
    List<Map> list = new ArrayList<Map>();
    for (int i = 0; i < 10; i++) {
      Map map = new HashMap();
      map.put("id", i);
      map.put("name", "姓名" + i);
      list.add(map);
    }

    String[] alias = { "编号", "姓名" };// excel的列头
    String[] names = { "id", "name" };// 数据List中的Map的key值.
    FileOutputStream out = null;
    try {
      out = new FileOutputStream("d:\\success2.xls");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    exportExcel(list, alias, names, "学生信息表", 60000, out);
    System.out.println("----执行完毕----------");
  }

  /**
   * 对list数据源将其里面的数据导入到excel表单
   * 
   * @param fieldName
   *            [] 导出到excel文件里的表头名
   * @param columnIt
   *            [] 导出到excel文件里的表头NAME
   * @param sheetName
   *            工作表的名称
   * @param sheetSize
   *            每个sheet中数据的行数,此数值必须小于65536
   * @param output
   *            java输出流
   */
  public static boolean exportExcel(List list, String[] fieldName,
      Object[] columnIt, String sheetName, int sheetSize,
      OutputStream output) {
    HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
    if (sheetSize >= 65536) {
      sheetSize = 65536;
    }
    double sheetNo = Math.ceil(list.size() / sheetSize);
    for (int index = 0; index <= sheetNo; index++) {
      HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
      workbook.setSheetName(index, sheetName+index);//设置工作表的名称.
      HSSFRow row = sheet.createRow(0);// 产生一行
      HSSFCell cell;// 产生单元格

      // 写入各个字段的名称
      for (int i = 0; i < fieldName.length; i++) {
        cell = row.createCell(i); // 创建第一行各个字段名称的单元格
        cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格内容为字符串型
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        // //为了能在单元格中输入中文,设置字符集为UTF_16
        cell.setCellValue(fieldName[i]); // 给单元格内容赋值
      }

      int startNo = index * sheetSize;
      int endNo = Math.min(startNo + sheetSize, list.size());
      // 写入各条记录,每条记录对应excel表中的一行
      for (int i = startNo; i < endNo; i++) {
        row = sheet.createRow(i + 1 - startNo);
        HashMap map = (HashMap) list.get(i);
        for (int j = 0; j < columnIt.length; j++) {
          cell = row.createCell(j);
          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
          // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
          Object value = map.get(columnIt[j]);
          if (value != null) {
            cell.setCellValue(map.get(columnIt[j]).toString());
          } else
            cell.setCellValue("");
        }
      }
    }
    try {
      output.flush();
      workbook.write(output);
      output.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Output is closed ");
      return false;
    }

  }
}
