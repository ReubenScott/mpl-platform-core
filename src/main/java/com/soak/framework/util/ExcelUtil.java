package com.soak.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soak.framework.date.DateStyle;
import com.soak.framework.date.DateUtil;

public class ExcelUtil {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * Excel 转JAVA类型
   * 
   * @param rs
   * @return
   */
  public static String convertCellToString(Cell cell) {
    String cellobjTmp;
    if (cell == null) {
      cellobjTmp = null;
    } else {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING: // 字符串
          cellobjTmp = cell.getStringCellValue().trim();
          break;
        case Cell.CELL_TYPE_NUMERIC: // 数字 日期
          double cellValue = cell.getNumericCellValue();
          switch (cell.getCellStyle().getDataFormat()) {
            case 20: // Time
            case 21: // Time
            case 46: // TODO 需要验证    1900/1/1 0:00:00  24:00:00
            case 176:  
              cellobjTmp = DateUtil.formatDate(cell.getDateCellValue(), DateStyle.TIMEFORMAT);
              break;
            case 22: // DateTime
              cellobjTmp = DateUtil.formatDate(cell.getDateCellValue(), DateStyle.DATETIMEFORMAT);
              break;
            case 31: // Date  2016年10月26日
            case 58: // Date
              // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
              cellobjTmp = DateUtil.formatDate(cell.getDateCellValue(), DateStyle.SHORTDATEFORMAT);
              break;
            case 0: // 数字
              // 返回数值类型的值
              long longVal = Math.round(cellValue);
              // 判断是否含有小数位.0
              if (cellValue - longVal == 0) {
                cellobjTmp = String.valueOf(longVal);
              } else {
                cellobjTmp = String.valueOf(cellValue);
              }
              break;
            default:
              cellobjTmp = String.valueOf(cellValue);
          }
          break;
        case Cell.CELL_TYPE_BOOLEAN: // Boolean
          cellobjTmp = String.valueOf(cell.getBooleanCellValue());
          break;
        case Cell.CELL_TYPE_FORMULA: // 公式
          // cellobjTmp = String.valueOf(cell.getCellFormula());
          try {
            cellobjTmp = String.valueOf(cell.getNumericCellValue());
          } catch (IllegalStateException e) {
            try {
              cellobjTmp = String.valueOf(cell.getRichStringCellValue());
            } catch (IllegalStateException e1) {
              //TODO
              cellobjTmp = null ;
            }
          }
          break;
        case Cell.CELL_TYPE_BLANK: // 空值
          cellobjTmp = "";
          break;
        case Cell.CELL_TYPE_ERROR: // 故障
          cellobjTmp = "";
          break;
        default:
          cellobjTmp = cell.toString();
          break;
      }
    }
    return cellobjTmp;
  }
  
  /**
   * 设置表头样式
   * @param sheet
   * @param region
   * @param cs
   */
  public static Sheet createHead(Workbook workbook ,  String sheetName , String title , int width) {
    // Sheet样式
    CellStyle sheetStyle = workbook.createCellStyle();
    // 背景色的设定
//    sheetStyle.setFillBackgroundColor(XSSFColor.GREY_25_PERCENT.index);
    // 前景色的设定
//    sheetStyle.setFillForegroundColor(XSSFColor.GREY_25_PERCENT.index);
    // 填充模式
    sheetStyle.setFillPattern(CellStyle.NO_FILL);
    
    Sheet sheet ;
    if(StringUtil.isEmpty(sheetName)){
      sheet = workbook.createSheet();// 创建一个Excel的Sheet
    } else{
      sheet = workbook.createSheet(sheetName);// 创建一个Excel的Sheet
    }
    
    // 设置列的样式
    for (int i = 0; i < width; i++) {
      sheet.autoSizeColumn(i, true); //  自适应列宽度
    }
    
    Row row = sheet.createRow(0);  // 创建第一行
    Cell cell = row.createCell(0);  // 创建第一列
    
    cell.setCellValue(title);  // 设置标题

    row.setHeight((short) 900);  // 设置行高
    
    /**
     * 合并单元格 
     * 第一个参数：第一个单元格的行数（从0开始） 
     * 第二个参数：第二个单元格的行数（从0开始） 
     * 第三个参数：第一个单元格的列数（从0开始） 
     * 第四个参数：第二个单元格的列数（从0开始）
     */
    CellRangeAddress range = new CellRangeAddress(0, 0, 0, width );  // 合并单元格
    sheet.addMergedRegion(range);
    
    
    // 设置字体
    Font font = workbook.createFont();
    font.setFontName("黑体");
    font.setFontHeightInPoints((short)22);// 字体大小
    font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 加粗

    // 标题样式
    CellStyle headstyle = workbook.createCellStyle();
    headstyle.setFont(font);  
    headstyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
    headstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
    headstyle.setLocked(true);
    headstyle.setWrapText(true);// 自动换行
    
    cell.setCellStyle(headstyle);
    
    return sheet ;
  }

  
  
  /**
   * 设置合并 单元格样式
   * @param sheet
   * @param region
   * @param cs
   */
  public static CellStyle getColumnHeadStyle(Workbook workbook) {
    // 字体样式
    Font font = workbook.createFont();
    font.setFontName("宋体");
    font.setFontHeightInPoints((short) 10);
    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    
    // 列头的样式
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setFont(font);
    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
    cellStyle.setLocked(true);
    cellStyle.setWrapText(true);
//    columnHeadStyle.setLeftBorderColor(XSSFColor.BLACK.index);// 左边框的颜色
//    columnHeadStyle.setRightBorderColor(XSSFColor.BLACK.index);// 右边框的颜色
//    columnHeadStyle.setBorderBottom(CellStyle.BORDER_NONE); // 设置单元格的边框为粗体
//    columnHeadStyle.setBottomBorderColor(XSSFColor.BLACK.index); // 设置单元格的边框颜色
    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
//    columnHeadStyle.setFillForegroundColor(XSSFColor.WHITE.index);
    
    cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
    cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
//    columnHeadStyle.setBorderLeft((short)1);// 边框的大小
    cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
//    columnHeadStyle.setBorderRight((short)1);// 边框的大小
    cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
    
    return cellStyle;
  }
  

  
  /**
   * 普通单元格样式
   * @param sheet
   * @param region
   * @param cs    
   */
  public static CellStyle getOrdinaryCellStyle(Workbook workbook) {
    // 字体样式
    Font font = workbook.createFont();
    font.setFontName("宋体");
    font.setFontHeightInPoints((short) 10);
    
    // 普通单元格样式
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setFont(font);
    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
    cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
    cellStyle.setLocked(true);
    cellStyle.setWrapText(true);

    cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
//  columnHeadStyle.setBorderBottom(CellStyle.BORDER_NONE); // 设置单元格的边框为粗体
    cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
//    columnHeadStyle.setBorderLeft((short)1);// 边框的大小
    cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
//    columnHeadStyle.setBorderRight((short)1);// 边框的大小
    cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
//  columnHeadStyle.setLeftBorderColor(XSSFColor.BLACK.index);// 左边框的颜色
//  columnHeadStyle.setRightBorderColor(XSSFColor.BLACK.index);// 右边框的颜色
//  columnHeadStyle.setBottomBorderColor(XSSFColor.BLACK.index); // 设置单元格的边框颜色
//style.setFillForegroundColor(XSSFColor.WHITE.index);// 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
    
    return cellStyle;
  }
  
  
  /**
   * 设置合并 单元格样式
   * @param sheet
   * @param region
   * @param cs
   */
  public static void setRegionStyle(Sheet sheet, CellRangeAddress region, CellStyle cs) {
    for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
      Row row = sheet.getRow(i);
      if (region.getFirstColumn() != region.getLastColumn()) {
        for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
          Cell cell = row.getCell((short) j);
          cell.setCellStyle(cs);
        }
      }
    }
  }

  /**
   * 设置合并 单元格边框
   * @param sheet
   * @param region
   * @param cs
   */
  public static void setRegionBorder(int border, CellRangeAddress region, Sheet sheet,Workbook wb){  
    RegionUtil.setBorderBottom(border,region, sheet, wb);  
    RegionUtil.setBorderLeft(border,region, sheet, wb);  
    RegionUtil.setBorderRight(border,region, sheet, wb);  
    RegionUtil.setBorderTop(border,region, sheet, wb);  
  
  }  

  /**
   * 读取office xls
   * 
   * @param filePath
   */
  public void readExcel(String filePath) {

    Workbook wb;
    try {
      wb = WorkbookFactory.create(new FileInputStream(filePath));
      Sheet sheet = wb.getSheetAt(0);

      wb.getNumberOfSheets();
      // Iterate over each row in the sheet
      Iterator<Row> rows = sheet.rowIterator();
      while (rows.hasNext()) {
        Row row = rows.next();
        System.out.println("Row #" + row.getRowNum());
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext()) {
          Cell cell = cells.next();
          switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
              System.out.println(cell.getNumericCellValue());
              break;
            case Cell.CELL_TYPE_STRING:
              System.out.println(cell.getStringCellValue());
              break;
            case Cell.CELL_TYPE_FORMULA:
              System.out.println(cell.getCellFormula());
              break;
            case Cell.CELL_TYPE_BLANK:
              System.out.println("");
              break;
            case Cell.CELL_TYPE_BOOLEAN:
              System.out.println(cell.getBooleanCellValue());
              break;
            default:
              System.out.println("unsuported sell type ：" + cell.getCellType());
              break;
          }
        }
      }
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String args[]) {

    HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
    HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet

    sheet.createFreezePane(1, 3);// 冻结

    // 设置列宽
    sheet.setColumnWidth(0, 1000);
    sheet.setColumnWidth(1, 3500);
    sheet.setColumnWidth(2, 3500);
    sheet.setColumnWidth(3, 6500);
    sheet.setColumnWidth(4, 6500);
    sheet.setColumnWidth(5, 6500);
    sheet.setColumnWidth(6, 6500);
    sheet.setColumnWidth(7, 2500);

    // Sheet样式
    HSSFCellStyle sheetStyle = workbook.createCellStyle();
    // 背景色的设定
    sheetStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
    // 前景色的设定
    sheetStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    // 填充模式
    sheetStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
    // 设置列的样式
    for (int i = 0; i <= 14; i++) {
      sheet.setDefaultColumnStyle((short) i, sheetStyle);
    }
    // 设置字体
    HSSFFont headfont = workbook.createFont();
    headfont.setFontName("黑体");
    headfont.setFontHeightInPoints((short) 22);// 字体大小
    headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

    // 另一个样式
    HSSFCellStyle headstyle = workbook.createCellStyle();
    headstyle.setFont(headfont);
    headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
    headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
    headstyle.setLocked(true);
    headstyle.setWrapText(true);// 自动换行

    // 另一个字体样式
    HSSFFont columnHeadFont = workbook.createFont();
    columnHeadFont.setFontName("宋体");
    columnHeadFont.setFontHeightInPoints((short) 10);
    columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

    // 列头的样式
    HSSFCellStyle columnHeadStyle = workbook.createCellStyle();
    columnHeadStyle.setFont(columnHeadFont);
    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
    columnHeadStyle.setLocked(true);
    columnHeadStyle.setWrapText(true);
    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
    columnHeadStyle.setBorderRight((short) 1);// 边框的大小
    columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
    columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);

    HSSFFont font = workbook.createFont();
    font.setFontName("宋体");
    font.setFontHeightInPoints((short) 10);
    // 普通单元格样式
    HSSFCellStyle style = workbook.createCellStyle();
    style.setFont(font);
    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中
    style.setWrapText(true);
    style.setLeftBorderColor(HSSFColor.BLACK.index);
    style.setBorderLeft((short) 1);
    style.setRightBorderColor(HSSFColor.BLACK.index);
    style.setBorderRight((short) 1);
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．

    // 另一个样式
    HSSFCellStyle centerstyle = workbook.createCellStyle();
    centerstyle.setFont(font);
    centerstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
    centerstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
    centerstyle.setWrapText(true);
    centerstyle.setLeftBorderColor(HSSFColor.BLACK.index);
    centerstyle.setBorderLeft((short) 1);
    centerstyle.setRightBorderColor(HSSFColor.BLACK.index);
    centerstyle.setBorderRight((short) 1);
    centerstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
    centerstyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
    centerstyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．

    try {
      // 创建第一行
      HSSFRow row0 = sheet.createRow(0);
      // 设置行高
      row0.setHeight((short) 900);
      // 创建第一列
      Cell cell0 = row0.createCell(0);
      cell0.setCellValue(new HSSFRichTextString("中非发展基金投资项目调度会工作落实情况对照表"));
      cell0.setCellStyle(headstyle);
      /**
       * 合并单元格 第一个参数：第一个单元格的行数（从0开始） 第二个参数：第二个单元格的行数（从0开始） 第三个参数：第一个单元格的列数（从0开始） 第四个参数：第二个单元格的列数（从0开始）
       */
      CellRangeAddress range = new CellRangeAddress(0, 0, 0, 7);

      sheet.addMergedRegion(range);
      // 创建第二行
      HSSFRow row1 = sheet.createRow(1);
      Cell cell1 = row1.createCell(0);
      cell1.setCellValue(new HSSFRichTextString("本次会议时间：2009年8月31日       前次会议时间：2009年8月24日"));

      cell1.setCellStyle(centerstyle);
      // 合并单元格
      range = new CellRangeAddress(1, 2, 0, 7);
      sheet.addMergedRegion(range);
      // 第三行
      HSSFRow row2 = sheet.createRow(3);
      row2.setHeight((short) 750);
      Cell cell = row2.createCell(0);
      cell.setCellValue(new HSSFRichTextString("责任者"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(1);
      cell.setCellValue(new HSSFRichTextString("成熟度排序"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(2);
      cell.setCellValue(new HSSFRichTextString("事项"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(3);
      cell.setCellValue(new HSSFRichTextString("前次会议要求/n/新项目的项目概要"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(4);
      cell.setCellValue(new HSSFRichTextString("上周工作进展"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(5);
      cell.setCellValue(new HSSFRichTextString("本周工作计划"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(6);
      cell.setCellValue(new HSSFRichTextString("问题和建议"));
      cell.setCellStyle(columnHeadStyle);
      cell = row2.createCell(7);
      cell.setCellValue(new HSSFRichTextString("备 注"));
      cell.setCellStyle(columnHeadStyle);

      // 访问数据库，得到数据集
      /*
       * List<DeitelVO> deitelVOList = getEntityManager().queryDeitelVOList(); int m = 4; int k = 4; for (int i = 0; i < deitelVOList.size(); i++) {
       * 
       * DeitelVO vo = deitelVOList.get(i); String dname = vo.getDname(); List<Workinfo> workList = vo.getWorkInfoList(); HSSFRow row = sheet.createRow(m); cell =
       * row.createCell(0); cell.setCellValue(new HSSFRichTextString(dname)); cell.setCellStyle(centerstyle); // 合并单元格
       * 
       * range = new CellRangeAddress(m, m + workList.size() - 1, 0, 0);
       * 
       * sheet.addMergedRegion(range); m = m + workList.size();
       * 
       * for (int j = 0; j < workList.size(); j++) {
       * 
       * Workinfo w = workList.get(j); // 遍历数据集创建Excel的行
       * 
       * row = sheet.getRow(k + j); if (null == row) { row = sheet.createRow(k + j); } cell = row.createCell(1); cell.setCellValue(w.getWnumber()); cell.setCellStyle(centerstyle);
       * cell = row.createCell(2); cell.setCellValue(new HSSFRichTextString(w.getWitem())); cell.setCellStyle(style); cell = row.createCell(3); cell.setCellValue(new
       * HSSFRichTextString(w.getWmeting())); cell.setCellStyle(style); cell = row.createCell(4); cell.setCellValue(new HSSFRichTextString(w.getWbweek()));
       * cell.setCellStyle(style); cell = row.createCell(5); cell.setCellValue(new HSSFRichTextString(w.getWtweek())); cell.setCellStyle(style); cell = row.createCell(6);
       * cell.setCellValue(new HSSFRichTextString(w.getWproblem())); cell.setCellStyle(style); cell = row.createCell(7); cell.setCellValue(new HSSFRichTextString(w.getWremark()));
       * cell.setCellStyle(style); } k = k + workList.size(); }
       */
      // 列尾
      int footRownumber = sheet.getLastRowNum();
      HSSFRow footRow = sheet.createRow(footRownumber + 1);
      Cell footRowcell = footRow.createCell(0);
      footRowcell.setCellValue(new HSSFRichTextString("                    审  定：XXX      审  核：XXX     汇  总：XX"));

      footRowcell.setCellStyle(centerstyle);
      range = new CellRangeAddress(footRownumber + 1, footRownumber + 1, 0, 7);

      sheet.addMergedRegion(range);

      String filename = "未命名.xls";// 设置下载时客户端Excel的名称

      FileOutputStream fos = new FileOutputStream(new File(filename));
      workbook.write(fos);
      fos.flush();
      fos.close();
      /*
       * HttpServletResponse response = getResponse(); HttpServletRequest request = getRequest(); filename = Util.encodeFilename(filename, request);
       * response.setContentType("application/vnd.ms-excel"); response.setHeader("Content-disposition", "attachment;filename=" + filename); OutputStream ouputStream =
       * response.getOutputStream(); workbook.write(ouputStream); ouputStream.flush(); ouputStream.close();
       */

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
