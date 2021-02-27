package com.kindustry.common.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Before;
import org.junit.Test;

import com.kindustry.common.io.FileUtility;
import com.kindustry.common.util.TranscodeUtility;

public class FileUtilTest {

  private static final String outputPath = "output.txt";
  private static String data = "你好，我是张三我今天很开心和你约会请多多指教";

  // @Before
  public void setUp() throws Exception {
  }

  // @Test
  public void testWriteFileStringStringStringArray() {
    fail("Not yet implemented");
  }

  // @Test
  public void testImportLineData() {
    fail("Not yet implemented");
  }

  // @Test
  public void testunZip() {
    FileUtility.unZip("E:\\20150802.zip", "D:\\home\\logs\\");
  }

   @Test
  public void testFuzzyLookupFiles() {
    // String date =
    File[] files = FileUtility.fuzzyLookupFiles("D:/logs", "?debug*.log");
    for (File file : files) {
      System.out.println(file.getAbsolutePath());
    }
  }
  
//  @Test
  public void testByte() {
    // String aa = FileUtil.toHexString( (byte)29);
    // byte aa = FileUtil.convertAscciiToByte("29");

    String uni = TranscodeUtility.strToUnicode("1");
    System.out.println(uni);
    String abc = TranscodeUtility.unicodeToString(uni);
    System.out.println(abc);

  }


  // @Test
  public static void appendToFile() {
    String fileName = "C:/temp/newTemp.txt";
    String content = "new append!";
    // 按方法A追加文件
    FileUtility.appendMethodA(fileName, content);
    FileUtility.appendMethodA(fileName, "append end. \n");
    // 显示文件内容
    FileUtility.readFileByLines(fileName);

    // 按方法B追加文件
    FileUtility.appendMethodB(fileName, content);
    FileUtility.appendMethodB(fileName, "append end. \n");
    // 显示文件内容
    FileUtility.readFileByLines(fileName);
  }

  public static void main11(String[] args) throws IOException {
    File inputFile = new File(outputPath);
    File outputFile = new File(outputPath);
    if (!inputFile.exists()) {
      inputFile.createNewFile();
    }
    if (!outputFile.exists()) {
      outputFile.createNewFile();
    }
    // 实例化输入输出流，并且获取相对应的FileChannel实例
    FileInputStream fis = new FileInputStream(inputFile);
    FileChannel inputChannel = fis.getChannel();
    FileOutputStream fos = new FileOutputStream(outputFile);
    FileChannel outputChannel = fos.getChannel();

    // 获取数据的字节数组
    byte[] outputBuffer = data.getBytes();
    // 分配一个字节缓冲(这个类在下一篇中介绍)
    ByteBuffer obb = ByteBuffer.allocate(1024);
    // 将字节数组读入字节缓冲中
    obb.put(outputBuffer);
    obb.flip(); // 调用该方法表示开始读取字节缓冲中的数据，即limit=position,position=0
    // 将字节写入文件中
    int n = outputChannel.write(obb);
    outputChannel.close(); // 关闭通道
    System.out.println("output n is " + n);
    fos.close(); // 关闭输出流
    // 再分配一个字节缓冲
    ByteBuffer ibb = ByteBuffer.allocate(1024);
    // 将数据从通道中读入字节缓冲中
    int in = inputChannel.read(ibb);
    // 初始化一个字节数组，这个字节数组的长度不能大于这个字节缓冲的limit-position的长度，不然会抛出java.nio.BufferUnderflowException
    byte[] inputBuffer = new byte[ibb.position()];
    // 准备从字节缓冲中读取数据
    ibb.flip();
    System.out.println("input n is " + in);
    // 将数据读入字节数组中
    ibb.get(inputBuffer);
    System.out.println(new String(inputBuffer));
    inputChannel.close();
    fis.close();
  }

}
