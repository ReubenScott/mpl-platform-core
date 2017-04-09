package com.soak.common.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.soak.common.io.ExcelUtil;

public class ExcelUtilTest {

  ExcelUtil excelUtil;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    excelUtil = new ExcelUtil();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testReadExcel() {
    // excelUtil.readExcel("D:/workspace/中非发展基金投资项目调度会工作落实情况对照表.xlsx");
    excelUtil.readExcel("D:/workspace/test.xls");
  }

  @Test
  public void testCreateExcel() {
//    excelUtil.readExcel(filePath);
  }

}
