package com.kindustry.common.security;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Before;
import org.junit.Test;

import com.kindustry.common.io.PropertyReader;
import com.kindustry.config.constant.ApplicationConstant;

public class StandardEncryptorTest {

  @Before
  public void setUp() throws Exception {}

  @Test
  public void testbyte2hex() {
    byte[] b = new byte[29];
    try {
      String s = new String(b, "GB2312");
      System.out.println(s);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Test
  public void testCorrespond() {

    Properties properties = PropertyReader.getInstance().read("conf.properties");
    // String publicFilePath = "E:\\test_public_key.pem";
    String publickey = properties.getProperty("public_key"); // RSAForCommunication.readWantedText1(publicFilePath);

    String word = "这是用来测试，加密效果的一句话，可以试试 有多长。但是目前的情况来看，是可以长场产出噶哈哈哈哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈啊啊啊哈哈哈";
    String encode = "";
    try {
      byte[] a = Correspond.encrypt(word.getBytes(), publickey);
      encode = Base64.encode(a);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("-------加密之后的密文：");
    System.out.println(encode);

    // String filePath = "E:\\test_private_key.pem";
    String private_key = properties.getProperty("private_key"); // CryptoFunctions.readWantedText1(filePath);
    byte[] a;
    try {
      a = Base64.decode(encode);
      byte[] b = Correspond.decrypt(a, private_key);
      String deCodeStr = new String(b, "utf-8");
      System.out.println("--------密文解密为：");
      System.out.println(deCodeStr);
    } catch (Exception ex) {
      System.out.println("1");
      ex.printStackTrace();
    }
  }

  @Test
  public void testStandardEncryptor() throws Exception {
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    // 加密所需的salt(盐)
    // textEncryptor.setPassword("G0CvDz7oJn6");
    textEncryptor.setPassword(ApplicationConstant.CRYPTOKEY);
    // 要加密的数据（数据库的用户名或密码）
    String username = textEncryptor.encrypt("root");
    String password = textEncryptor.encrypt("root123");
    System.out.println("username:" + username);
    System.out.println(textEncryptor.decrypt(username));
    System.out.println("password:" + password);
    System.out.println(textEncryptor.decrypt(password));

    // stringEncryptor = new StandardPBEStringEncryptor();
    // stringEncryptor.setPassword("G0CvDz7oJn6");

    username = CryptoHandler.encrypt("jdbc:postgresql://localhost/cashier?searchpath=public");
    password = CryptoHandler.encrypt("org.postgresql.Driver");
    System.out.println("username:" + username);
    System.out.println(CryptoHandler.decrypt(username));
    System.out.println("password:" + password);
    System.out.println(CryptoHandler.decrypt(password));

  }
}
