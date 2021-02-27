package com.kindustry.config.constant;

/**
 * 常量
 *
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/12/23 10:11
 */

public class ApplicationConstant {

  // 密钥
  public static final String CRYPTOKEY = "Vdsa<fq09094>!./sf:}{2115855^@%^!asf";

  public static final String ENCODING = "UTF-16";

  // 文件分隔符，在window中为\\，在linux中为/
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");

  // 设置臨時目录
  public static final String TEMP_DIR = System.getProperty("user.home") + FILE_SEPARATOR + "Temp" + FILE_SEPARATOR;

  // 设置生成目录
  public static final String ACCESSORY_DIR = System.getProperty("user.home") + FILE_SEPARATOR + "Accessory" + FILE_SEPARATOR;

  // 因子
  public static final float FACTOR = 1.15F;

  // 默认文件每次读取字节数
  public static final int BYTE_COUNT = 40960;

  // 日志级别 控制台不输出
  public static final int NONE = 0X453500;

  // 日志级别 控制台输出所有信息
  public static final int INFO = 0X453501;

  // 日志级别 控制台输出调试和错误信息
  public static final int DEBUG = 0X453502;

  // 日志级别 控制台只输出错误信息
  public static final int ERROR = 0X453503;

  public final static String CURRENT_USER = "_current_user";
  public final static String CURRENT_USER_ROLES = "_current_user_roles";
  public final static String CURRENT_USER_ORGS = "_current_user_orgs";
  public final static String TEMP_FILE = "tmp_file";
  public final static String TEMP_FILE_ATTACH = "attach_file";

  public static final String DEFAULT_ENCODING = "UTF-8";

  public final static int DEFAULT_BATCH_COUNT = 5000; // JDBC 数据库 批量大小

  public final static int DB2 = 1;

  public final static int ORACLE = 2;

  public final static int MYSQL = 3;

  public final static int pageUtils = 5;

}
