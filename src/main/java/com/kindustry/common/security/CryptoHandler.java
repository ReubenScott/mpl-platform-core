package com.kindustry.common.security;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.kindustry.system.context.SystemConstant;

public final class CryptoHandler {

  private final static StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();

  private final static StandardPBEByteEncryptor byteEncryptor = new StandardPBEByteEncryptor();

  static {
    stringEncryptor.setPassword(SystemConstant.CRYPTOKEY);
    byteEncryptor.setPassword(SystemConstant.CRYPTOKEY);
  }

  /**
   * <li>
   * 方法名称:encrypt</li> <li>
   * 加密方法
   * 
   * @param xmlStr
   *          需要加密的消息字符串
   * @return 加密后的字符串
   */
  public static String encrypt(String in) {
    if ((in == null) || (in.isEmpty())) {
      return in;
    }

    return stringEncryptor.encrypt(in);
  }

  /**
   * <li>
   * 方法名称:decrypt</li> <li>
   * 功能描述:
   * 
   * <pre>
   * 解密方法
   * </pre>
   * 
   * </li>
   * 
   * @param xmlStr
   *          需要解密的消息字符串
   * @return 解密后的字符串
   */
  public static String decrypt(String in) {
    if ((in == null) || (in.isEmpty())) {
      return in;
    }

    return stringEncryptor.decrypt(in);
  }

  public static byte[] encrypt(byte[] in) {
    if ((in == null) || (in.length == 0)) {
      return in;
    }

    return byteEncryptor.encrypt(in);
  }

  public static byte[] decrypt(byte[] in) {
    if ((in == null) || (in.length == 0)) {
      return in;
    }

    return byteEncryptor.decrypt(in);
  }

}