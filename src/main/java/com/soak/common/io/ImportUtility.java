package com.soak.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.soak.common.constant.CharSetType;
import com.soak.common.constant.DateStyle;
import com.soak.common.date.DateUtil;

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
            case 46: // TODO 需要验证 1900/1/1 0:00:00 24:00:00
            case 176:
              cellobjTmp = DateUtil.formatDate(cell.getDateCellValue(), DateStyle.TIMEFORMAT);
              break;
            case 22: // DateTime
              cellobjTmp = DateUtil.formatDate(cell.getDateCellValue(), DateStyle.DATETIMEFORMAT);
              break;
            case 31: // Date 2016年10月26日
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
              // TODO
              cellobjTmp = null;
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
   * 功能: Excel数据导入到数据库
   * 参数: originUrl[Excel表的所在路径]
   * 参数: startRow[从第几行开始]
   * 参数: endRow[到第几行结束
   * (0表示所有行;
   * 正数表示到第几行结束;
   * 负数表示到倒数第几行结束)]
   * 参数: clazz[要返回的对象集合的类型]
   */
  public static List<?> importExcel(String originUrl, int startRow, int endRow, Class<?> clazz) throws IOException {
    // 是否打印提示信息
    boolean showInfo = true;
    return doImportExcel(originUrl, startRow, endRow, showInfo, clazz);
  }

  /**
   * 功能:真正实现导入
   */
  private static List<Object> doImportExcel(String originUrl, int startRow, int endRow, boolean showInfo, Class<?> clazz) throws IOException {
    // 判断文件是否存在
    File file = new File(originUrl);
    if (!file.exists()) {
      throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
    }
    HSSFWorkbook wb = null;
    FileInputStream fis = null;
    List<Row> rowList = new ArrayList<Row>();
    try {
      fis = new FileInputStream(file);
      // 去读Excel
      wb = new HSSFWorkbook(fis);
      Sheet sheet = wb.getSheetAt(0);
      // 获取最后行号
      int lastRowNum = sheet.getLastRowNum();
      if (lastRowNum > 0) { // 如果>0，表示有数据
        out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：", showInfo);
      }
      Row row = null;
      // 循环读取
      for (int i = startRow; i <= lastRowNum + endRow; i++) {
        row = sheet.getRow(i);
        if (row != null) {
          rowList.add(row);
          out("第" + (i + 1) + "行：", showInfo, false);
          // 获取每一单元格的值
          for (int j = 0; j < row.getLastCellNum(); j++) {
            String value = getCellValue(row.getCell(j));
            if (!value.equals("")) {
              out(value + " | ", showInfo, false);
            }
          }
          out("", showInfo);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {

    }
    return returnObjectList(rowList, clazz);
  }

  /**
   * 功能:获取单元格的值
   */
  private static String getCellValue(Cell cell) {
    Object result = "";
    if (cell != null) {
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
          result = cell.getStringCellValue();
          break;
        case Cell.CELL_TYPE_NUMERIC:
          result = cell.getNumericCellValue();
          break;
        case Cell.CELL_TYPE_BOOLEAN:
          result = cell.getBooleanCellValue();
          break;
        case Cell.CELL_TYPE_FORMULA:
          result = cell.getCellFormula();
          break;
        case Cell.CELL_TYPE_ERROR:
          result = cell.getErrorCellValue();
          break;
        case Cell.CELL_TYPE_BLANK:
          break;
        default:
          break;
      }
    }
    return result.toString();
  }

  /**
   * 功能:返回指定的对象集合
   */
  private static List<Object> returnObjectList(List<Row> rowList, Class<?> clazz) {
    List<Object> objectList = null;
    Object obj = null;
    String attribute = null;
    String value = null;
    int j = 0;
    try {
      objectList = new ArrayList<Object>();
      Field[] declaredFields = clazz.getDeclaredFields();
      for (Row row : rowList) {
        j = 0;
        obj = clazz.newInstance();
        for (Field field : declaredFields) {
          attribute = field.getName().toString();
          value = getCellValue(row.getCell(j));
          setAttrributeValue(obj, attribute, value);
          j++;
        }
        objectList.add(obj);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objectList;
  }

  /**
   * 功能:给指定对象的指定属性赋值
   */
  private static void setAttrributeValue(Object obj, String attribute, String value) {
    // 得到该属性的set方法名
    String method_name = convertToMethodName(attribute, obj.getClass(), true);
    Method[] methods = obj.getClass().getMethods();
    for (Method method : methods) {
      /**
       * 因为这里只是调用bean中属性的set方法，属性名称不能重复
       * 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
       * （注：在java中，锁定一个方法的条件是方法名及参数）
       */
      if (method.getName().equals(method_name)) {
        Class<?>[] parameterC = method.getParameterTypes();
        try {
          /**
           * 如果是(整型,浮点型,布尔型,字节型,时间类型),
           * 按照各自的规则把value值转换成各自的类型
           * 否则一律按类型强制转换(比如:String类型)
           */
          if (parameterC[0] == int.class || parameterC[0] == java.lang.Integer.class) {
            value = value.substring(0, value.lastIndexOf("."));
            method.invoke(obj, Integer.valueOf(value));
            break;
          } else if (parameterC[0] == float.class || parameterC[0] == java.lang.Float.class) {
            method.invoke(obj, Float.valueOf(value));
            break;
          } else if (parameterC[0] == double.class || parameterC[0] == java.lang.Double.class) {
            method.invoke(obj, Double.valueOf(value));
            break;
          } else if (parameterC[0] == byte.class || parameterC[0] == java.lang.Byte.class) {
            method.invoke(obj, Byte.valueOf(value));
            break;
          } else if (parameterC[0] == boolean.class || parameterC[0] == java.lang.Boolean.class) {
            method.invoke(obj, Boolean.valueOf(value));
            break;
          } else if (parameterC[0] == java.util.Date.class) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
              date = sdf.parse(value);
            } catch (Exception e) {
              e.printStackTrace();
            }
            method.invoke(obj, date);
            break;
          } else {
            method.invoke(obj, parameterC[0].cast(value));
            break;
          }
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (SecurityException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // 正则表达式 用于匹配属性的第一个字母
  private static final String REGEX = "[a-zA-Z]";

  /**
   * 功能:根据属性生成对应的set/get方法
   */
  private static String convertToMethodName(String attribute, Class<?> objClass, boolean isSet) {
    /** 通过正则表达式来匹配第一个字符 **/
    Pattern p = Pattern.compile(REGEX);
    Matcher m = p.matcher(attribute);
    StringBuilder sb = new StringBuilder();
    /** 如果是set方法名称 **/
    if (isSet) {
      sb.append("set");
    } else {
      /** get方法名称 **/
      try {
        Field attributeField = objClass.getDeclaredField(attribute);
        /** 如果类型为boolean **/
        if (attributeField.getType() == boolean.class || attributeField.getType() == Boolean.class) {
          sb.append("is");
        } else {
          sb.append("get");
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
    /** 针对以下划线开头的属性 **/
    if (attribute.charAt(0) != '_' && m.find()) {
      sb.append(m.replaceFirst(m.group().toUpperCase()));
    } else {
      sb.append(attribute);
    }
    return sb.toString();
  }

  /**
   * 功能:输出提示信息(普通信息打印)
   */
  private static void out(String info, boolean showInfo) {
    if (showInfo) {
      System.out.print(info + (showInfo ? "\n" : ""));
    }
  }

  /**
   * 功能:输出提示信息(同一行的不同单元格信息打印)
   */
  private static void out(String info, boolean showInfo, boolean nextLine) {
    if (showInfo) {
      if (nextLine) {
        System.out.print(info + (showInfo ? "\n" : ""));
      } else {
        System.out.print(info);
      }
    }
  }

}