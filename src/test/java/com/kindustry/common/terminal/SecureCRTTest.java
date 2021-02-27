package com.kindustry.common.terminal;

import static org.junit.Assert.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.kindustry.common.io.FileUtility;
import com.kindustry.common.terminal.SecureCRT;
import com.kindustry.common.terminal.UserAuthInfo;
import com.kindustry.common.util.DateUtility;

public class SecureCRTTest {

  UserAuthInfo userAuthInfo92 ;
  UserAuthInfo userAuthInfo170 ;

  @Before
  public void setUp() {
     userAuthInfo92 = new UserAuthInfo("32.137.32.92" , 22, "etl", "etl123");
     userAuthInfo170 = new UserAuthInfo("32.137.126.170", 22, "etl", "etl123");
  }

//  @Test
  public void testUploadFile() {

    String directory = "/home/etl/data";
    String uploadFile = "E:\\Lock.bmp";
    String downloadFile = "/home/etl/data/ftpdata/P_063_WX_BINDRELATIONALINFO_20151228.OK";
    String saveFile = "D:\\P_063_WX_BINDRELATIONALINFO_20151228.OK";
    String deleteFile = "delete.txt";
    // sf.download(directory, downloadFile, saveFile);

    // sf.uploadFile(directory, uploadFile);
    // sf.delete(directory, deleteFile, sftp);
    // try {
    // sftp.cd(directory);
    // sftp.mkdir("ss");
    // System.out.println("finished");
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
  }

//  @Test
  public void testDownload() {
    fail("Not yet implemented");
  }

  @Test
  public void testDelete() {
    String like = "*20160616.del" ;
    SecureCRT.delete(userAuthInfo170, "/home/etl/data/BackUp", like);
  }

//  @Test
  public void testListFile() {
    fail("Not yet implemented");
  }

//  @Test
  public void testListDirectory() {
    fail("Not yet implemented");
  }

//  @Test
  public void testSyncFile() {
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    Date startDate = DateUtility.parseShortDate("2015-12-28");
    Long lstartTime = startDate.getTime();
    Long oneDay = 1000 * 60 * 60 * 24l;
    Long endTime = DateUtility.getLastDayOfMonth(startDate).getTime();
    Long time = lstartTime;
    while (time <= endTime) {
      Date eachDate = new Date(time);
      time += oneDay;
      String like = "*" + df.format(eachDate) + ".del";
//      String[] ss = SecureCRT.listFile(userAuthInfo92, "/home/etl/data/ftpdata","*20151228*");
      String[] result = SecureCRT.listFile(userAuthInfo92, "/home/etl/data/ftpdata",like);
      System.out.println(df.format(eachDate));
//      String[] result = StringUtil.fuzzyLookup(ss, like);
      System.out.println(result.length);
      for (String str : result) {
        String fromFile  =  "/home/etl/data/ftpdata/" +  str ;
        String toFile  =  "/home/etl/data/ftpdata/" +  str ;
//        SecureCRT.syncFile(userAuthInfo92, userAuthInfo170, fromFile, toFile);
      }
      
      SecureCRT.syncDirectory(userAuthInfo92, userAuthInfo170, "/home/etl/data/ftpdata", "/home/etl/data/ftpdata", like);
      break;
    }

  }

}
