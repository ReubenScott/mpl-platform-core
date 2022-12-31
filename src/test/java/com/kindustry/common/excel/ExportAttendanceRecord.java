package com.kindustry.common.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.kindustry.common.excel.vo.AttendanceRecord;
import com.kindustry.common.excel.vo.StudentVO;
import com.kindustry.common.io.excel.ExcelUtil;
import com.kindustry.common.util.JsonUtility;

/*
 * 使用步骤:
 * 1.新建一个类,例如StudentVO.
 * 2.设置哪些属性需要导出,哪些需要设置提示.
 * 3.设置实体数据
 * 4.调用exportExcel方法.
 */
public class ExportAttendanceRecord {

	@Test
	public void testExportInnoxAttendanceRecord() {
		// 指定文件
		String jsonPath = "attendanceStatusRecords.json";
		String nodeName = "result";

		List<String> result = JsonUtility.readJsonArray(jsonPath, nodeName);
		String monthRecod = result.get(result.size() - 1);
		System.out.println(monthRecod);

		nodeName = "workTimeExtenders";
		monthRecod = JsonUtility.getJson(monthRecod, nodeName);

		List<AttendanceRecord> attendanceRecords = JsonUtility.jsonToList(monthRecod, AttendanceRecord.class);
		System.out.println(attendanceRecords);

//		AttendanceRecord[] recodes = JsonUtility.jsonToArray(monthRecod, AttendanceRecord[].class);
//		System.out.println(recodes);

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("d:\\勤務表・作業時間_月.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ExcelUtil<AttendanceRecord> util = new ExcelUtil<AttendanceRecord>(AttendanceRecord.class);// 创建工具类.
		util.exportExcel(attendanceRecords, "作業時間", 65536, out);// 导出
	}

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
