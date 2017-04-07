package com.soak.common.ftp;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.soak.common.terminal.FtpZilla;
import com.soak.common.terminal.UserAuthInfo;

public class FtpZillaTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testDownFile() {
    UserAuthInfo userAuthinfo = new UserAuthInfo("32.137.32.41", 21, "sjxf", "sjxf");
    
//    String filePath = "/home/sjxf/data/data_ykdt/20160224.zip" ;
    String today = new SimpleDateFormat("yyyyMMdd").format(new Date()) ;  // new Date()为获取当前系统时间      
    String filePath = "/home/sjxf/data/data_ykdt/" + today +".zip" ;
    FtpZilla.downFile(userAuthinfo , "E:/BackUp" , filePath );

    String[] stra = new String[]{"/home/sjxf/data/data_ykdt/20160229.zip"  } ;

//     fa.downFile("E:/BackUp" , stra );
     
    // fa.uploadDir("D:\\home", "/home/userbalance/home");
    // 将FTP服务器上文件下载到本地

    // fa.rename("/home/userbalance", "新建文本文档", "新建文本文档123");
    // fa.remove("/home/userbalance", "新建文本文档2");

    /*
     * // 上传 将本地文件上传到FTP服务器上 try { // File sourceFile = new File("D:/新建文本文档.txt"); // System.out.println(sourceFile.getCanonicalPath()); // FileInputStream in = new
     * FileInputStream(sourceFile); // fa.uploadFile("/home/userbalance", "你好.txt", in);
     * 
     * 
     * } catch (FileNotFoundException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
     */

  }
  
  public void testListFiles() {
    UserAuthInfo userAuthinfo = new UserAuthInfo("32.137.32.116", 21, "ftpuser", "ftpuser");
    List<String> list = FtpZilla.listFiles(userAuthinfo , "/home/userbalance", new ArrayList());
    for (String aa : list) {
      System.out.println(aa);
    }

  }

}
