package com.tgb.lk.test04;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tgb.lk.util.ExcelUtil;

/*
 * 使用步骤:
 * 1.新建一个类,例如StudentVO.
 * 2.设置哪些属性需要导出,哪些需要设置提示.
 * 3.设置实体数据
 * 4.调用exportExcel方法.
 * 本例向您提供以下问题的解决方案:
 * 1.实体对象存放的值需要转换为其他文字的情况,例如:实例中有0,1表示男,女;而导入导出的excel中是中文的"男","女".
 * 2.实体对象的时间类型处理.
 * 
 */
public class ExportTest04 {
  public static void main(String[] args) {
    // 初始化数据
    List<Student> list = new ArrayList<Student>();

    Student stu = new Student();
    stu.setId(1);
    stu.setName("李坤");
    stu.setSex(0);
    stu.setClazz(5);
    stu.setCompany("天融信");
    stu.setBirthday(new Date());
    list.add(stu);

    Student stu2 = new Student();
    stu2.setId(2);
    stu2.setName("曹贵生");
    stu2.setSex(0);
    stu2.setClazz(5);
    stu2.setCompany("中银");
    list.add(stu2);

    Student stu3 = new Student();
    stu3.setId(3);
    stu3.setName("李学宇");
    stu3.setSex(1);
    stu3.setClazz(6);
    list.add(stu3);

    FileOutputStream out = null;
    try {
      out = new FileOutputStream("d:\\success4.xls");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    ExcelUtil<StudentVO> util = new ExcelUtil<StudentVO>(StudentVO.class);
    util.exportExcel(convertStu2VO(list), "学生信息", 60000, out);
    System.out.println("----执行完毕----------");
  }
  
  //将student对象转换为studentVO用于导出.
  private static List<StudentVO> convertStu2VO(List<Student> list) {
    List<StudentVO> list2 = new ArrayList<StudentVO>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d HH:mm:ss");
    for (int i = 0; i < list.size(); i++) {
      StudentVO stuVo = new StudentVO();
      Student stu = list.get(i);
      stuVo.setName(stu.getName());
      switch (stu.getSex()) {
      case 0:
        stuVo.setSex("男");
        break;
      case 1:
        stuVo.setSex("女");
        break;
      default:
        break;
      }
      //处理时间
      if (stu.getBirthday() != null) {
        stuVo.setBirthday(sdf.format(stu.getBirthday()));
      }

      switch (stu.getClazz()) {
      case 5:
        stuVo.setClazz("五期提高班");
        break;
      case 6:
        stuVo.setClazz("六期提高班");
        break;
      case 7:
        stuVo.setClazz("七期提高班");
      default:
        break;
      }
      list2.add(stuVo);
    }

    return list2;
  }

}
