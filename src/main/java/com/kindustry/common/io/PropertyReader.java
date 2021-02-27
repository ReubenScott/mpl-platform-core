package com.kindustry.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取配置文件信息
 */
public class PropertyReader {

  private volatile static PropertyReader reader;
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  private PropertyReader() {

  }

  public static PropertyReader getInstance() {
    if (reader == null) {
      synchronized (PropertyReader.class) {
        if (reader == null) {
          reader = new PropertyReader();
        }
      }
    }
    return reader;
  }

  /**
   * 获取配置文件信息 config.properties 方式一 ： ResourceBundle.getBundle("config");
   * 
   * @param propertyName
   * 
   * @author kindustry
   */
  public Properties load(String propertyName) {
    Properties properties = new Properties();
    ResourceBundle rb = ResourceBundle.getBundle(propertyName);
    for (Enumeration<String> en = rb.getKeys(); en.hasMoreElements();) {
      String key = en.nextElement();
      properties.put(key, rb.getString(key));
    }
    return properties;
  }

  /**
   * 获取配置文件信息 config.properties 方式二 ： PropertyReader.getInstance().read("conf.properties");
   * 
   * @param propertyPath
   * @return
   * @author kindustry
   */
  public Properties read(String propertyName) {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyName);
    Properties properties = new Properties();
    try {
      properties.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }

}
