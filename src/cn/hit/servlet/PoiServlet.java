package cn.hit.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.hit.utils.ExcelReader;

/**
 * 解析上传的excel文件
 * @author sunpeng123
 *
 */
public class PoiServlet  extends BaseServlet{ 
	
			
			public  void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				try {
					String path = (String) req.getAttribute("path");
		            // 对读取Excel表格标题测试
		            InputStream is = new FileInputStream(path);
		            ExcelReader excelReader = new ExcelReader();
		            String[] title = excelReader.readExcelTitle(is);
		            System.out.println("获得Excel表格的标题:");
		            for (String s : title) {
		                System.out.print(s + " ");
		            }

		            // 对读取Excel表格内容测试
		            InputStream is2 = new FileInputStream(path);
		            Map<Integer, String> map = excelReader.readExcelContent(is2);
		            System.out.println("获得Excel表格的内容:");
		            for (int i = 1; i <= map.size(); i++) {
		                System.out.println(map.get(i));
		            }
		        } catch (FileNotFoundException e) {
		            System.out.println("未找到指定路径的文件!");
		            e.printStackTrace();
		        }
		    
				super.doPost(req, resp);
			}
			
}
	
		  