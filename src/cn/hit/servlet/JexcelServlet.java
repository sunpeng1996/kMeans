package cn.hit.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.fileupload.FileItem;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.CORBA.PRIVATE_MEMBER;

import service.KMeans_Operator;
import domain.ClassDistance;
import domain.DataObject;
import domain.DataVector;
import domain.PointDistance;
import domain.ViewObject;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class JexcelServlet extends BaseServlet {

	private String ExcelFilename = null;
	private KMeans_Operator ko = new KMeans_Operator();
	List<String> headlist = null ;
	
	public Map<Integer, ArrayList<Double>> getDrugNameList() {
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new File(ExcelFilename));
		} catch (Exception e) {
			System.out.println("file not found");
			throw new RuntimeException(e);
		}
		Sheet s1 = wb.getSheet(0);
		int sColumns = s1.getColumns();//获取sheet表的总行数
		int sRows = s1.getRows();//获取sheet表的总行数
		System.out.println("该文件中的总行数和总列数分别为：" + sRows + ":::" + sColumns);
		//创建一个有序的集合，TreeMap
		int id = 0;
		Map<Integer, ArrayList<Double>>  map = new TreeMap<Integer, ArrayList<Double>>();
		 headlist = new ArrayList<String>();
		for(int cTemp = 1 ; cTemp < sColumns;  ++cTemp ){//获取表头列名称
				String contentHeadString = s1.getCell(cTemp,0).getContents();
				headlist.add(contentHeadString);
		}
		//System.out.println(headlist);//获取到了，搞定
		
			for(int row = 1; row <  sRows ; ++row){ //依次遍历每一行
				id++;
				//创建一个个的ArrayList,用于封装DataObject对象
				ArrayList<Double> list = new ArrayList<Double>();
				for(int  column = 1 ; column <   sColumns ; ++column){//遍历每一列
						//NumberCell
						Double  content = Double.parseDouble(s1.getCell(column , row).getContents())  ;
						//content =	s1.getCell(column , row).getContents();
						list.add(content);
						//System.out.println(content);
				}
				map.put(id, list);
			}				
		return map;
	}
	
	

	public  void doPost(HttpServletRequest request, HttpServletResponse response){
		
		
		ExcelFilename = (String) request.getAttribute("path");
		System.out.println("文件路径"+ ExcelFilename);
		Map<Integer, ArrayList<Double>> map = getDrugNameList();
		System.out.println("通过Map.entrySet遍历Key和value");
		for(Map.Entry<Integer, ArrayList<Double>> entry : map.entrySet()){
				System.out.println("key=="+ entry.getKey() + "and value=" + entry.getValue());
		}
	
		DataVector dv = new DataVector();
		for(Map.Entry<Integer, ArrayList<Double>> entry : map.entrySet()){
			 int key = entry.getKey();
			 DataObject dObject  = new DataObject(entry.getValue());
			 dObject.setId(key);				 
			 dv.add(dObject);
		}
		System.out.println(dv);
		int num = dv.size();
		
		System.out.println(request.getAttribute("classWay"));
		System.out.println(request.getAttribute("pointWay"));
		System.out.println(request.getAttribute("classnum"));
		
		String way = (String) request.getAttribute("way");
		
		System.out.println("我想看看他是什么方式"+way);
		int classnum = Integer.parseInt(request.getAttribute("classnum")+"");
		
		ViewObject vo1= ko.Normal(dv, classnum, PointDistance.valueOf(""+request.getAttribute("pointWay")));
		ViewObject vo2 = ko.Cluster(dv, classnum, PointDistance.valueOf(""+request.getAttribute("pointWay")), ClassDistance.valueOf(""+request.getAttribute("classWay")));
		System.out.println(vo1+"走你！");
		System.out.println(vo2+"走你！!!!!!!!ZOUZOUZOUZ222222222222222222");
		int num1 = vo1.getInit().size();
		System.out.println(num1);
		if(way.equals("point")){
				try {										
					request.getSession().setAttribute("dvstart", vo1.getInit());//在session中存放DataObject对象
					request.getSession().setAttribute("num1", num1);//在session中存放列数
					request.getSession().setAttribute("headlist", headlist);//表头数据存到session域中
					request.getSession().setAttribute("dvend", vo1.getResult());//存放结束质心位置
					request.getSession().setAttribute("classnum", vo1.getClassnum());//存放分层数量
					request.getRequestDispatcher("/jsps/pointView.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else if(way.equals("class")){
			request.getSession().setAttribute("iterationArrayList", vo2.getIterationArrayList());//存放数据列表
			System.out.println("这是分层聚类的数据。。。。。。。。。");
			try {
					request.getRequestDispatcher("/jsps/classView.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
