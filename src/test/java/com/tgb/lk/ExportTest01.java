package com.tgb.lk;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExportTest01 {

	public static void main(String[] args) throws IOException {
		
		HSSFWorkbook wb = new HSSFWorkbook();// excel文件对象
		HSSFSheet sheetlist = wb.createSheet("sheetlist");// 工作表对象

		FileOutputStream out = new FileOutputStream("d:\\success1.xls");
		String[] textlist = { "列表1", "列表2", "列表3", "列表4", "列表5" };

		sheetlist = setHSSFValidation(sheetlist, textlist, 0, 500, 0, 0);// 第一列的前501行都设置为选择列表形式.
		sheetlist = setHSSFPrompt(sheetlist, "promt Title", "prompt Content",0, 500, 1, 1);// 第二列的前501行都设置提示.
		sheetlist = setHSSFPrompt(sheetlist, "中文标题", "中文内容",0, 500, 3, 3);// 第二列的前501行都设置提示.
		sheetlist = setHSSFPrompt(sheetlist, "promt Title", "prompt Content",0, 500, 5, 5);// 第二列的前501行都设置提示.
		
		wb.write(out);
		out.close();
		System.out.println("----执行完毕----------");
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * @param sheet 要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow 结束行
	 * @param firstCol   开始列
	 * @param endCol  结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
			String[] textlist, int firstRow, int endRow, int firstCol,
			int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	/**
	 * 设置单元格上提示
	 * @param sheet  要设置的sheet.
	 * @param promptTitle 标题
	 * @param promptContent 内容
	 * @param firstRow 开始行
	 * @param endRow  结束行
	 * @param firstCol  开始列
	 * @param endCol  结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
			String promptContent, int firstRow, int endRow ,int firstCol,int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow,firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions,constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

}
