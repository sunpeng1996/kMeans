package cn.hit.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelDao {
	private String ExcelFilename = null;
	
	public ExcelDao(String excelFilename) {
		super();
		ExcelFilename = excelFilename;
	}

	public String getExcelFilename() {
		return ExcelFilename;
	}

	public void setExcelFilename(String excelFilename) {
		ExcelFilename = excelFilename;
	}

	public ArrayList<String> getDrugNameList(int begin,int end) {
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new File(this.ExcelFilename));
		} catch (Exception e) {
			System.out.println("file not found");
			throw new RuntimeException(e);
		}
		Sheet s1 = wb.getSheet(0);
		ArrayList<String> drugnameList = new ArrayList<String>();
		for(int i = begin; i < end ;++i){
			drugnameList.add(s1.getCell(2, i).getContents());
		}
		return drugnameList;
	}
	public void loadMsg(ArrayList<String> msgList,int begin){
		Workbook wb = null;
		WritableWorkbook book = null;
		try {
			wb = Workbook.getWorkbook(new File("F:/data/druginfo.xls"));
			book = Workbook.createWorkbook(new File("F:/data/druginfo.xls"),wb); 
		} catch(Exception e) {
			System.out.println("file not found");
			throw new RuntimeException(e);
		}
		WritableSheet sheet = book.getSheet(0);
		int end = msgList.size();
		int i = begin;
		try {
			for(String msg: msgList){
				Label label = new Label(75, i, msg);// 第一个参数指定单元格的列数、第二个参数指定单元格的行数，第三个指定写的字符串内容  
				sheet.addCell(label);
				i++;
			}
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
        try {
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
