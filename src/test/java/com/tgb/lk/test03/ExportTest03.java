package com.tgb.lk.test03;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.tgb.lk.util.ExcelUtil;

/*
 * 使用步骤:
 * 1.新建一个类,例如StudentVO.
 * 2.设置哪些属性需要导出,哪些需要设置提示.
 * 3.设置实体数据
 * 4.调用exportExcel方法.
 */
public class ExportTest03 {
  public static void main(String[] args) {
    // 初始化数据
    List<StudentVO> list = new ArrayList<StudentVO>();

    StudentVO vo = new StudentVO();
    vo.setId(1);
    vo.setName("李坤");
    vo.setAge(26);
    vo.setClazz("五期提高班");
    vo.setCompany("天融信");
    list.add(vo);

    StudentVO vo2 = new StudentVO();
    vo2.setId(2);
    vo2.setName("曹贵生");
    vo2.setClazz("五期提高班");
    vo2.setCompany("中银");
    list.add(vo2);

    StudentVO vo3 = new StudentVO();
    vo3.setId(3);
    vo3.setName("柳波");
    vo3.setClazz("五期提高班");
    list.add(vo3);

    FileOutputStream out = null;
    try {
      out = new FileOutputStream("d:\\success3.xls");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    ExcelUtil<StudentVO> util = new ExcelUtil<StudentVO>(StudentVO.class);// 创建工具类.
    util.exportExcel(list, "学生信息", 65536, out);// 导出
    System.out.println("----执行完毕----------");
  }

}
