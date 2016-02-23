package cn.hit.servlet;
/*
 * 
 * 此servlet暂时作废
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinalServlet extends BaseServlet {
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Map<Integer, ArrayList<String>>  map = (Map<Integer, ArrayList<String>>) request.getAttribute("map");
			String pointWay = (String) request.getAttribute("pointWay");
			String classWay = (String) request.getAttribute("classWay");
			int classnum = (Integer) request.getAttribute("classnum");
			//
			System.out.println("通过Map.entrySet遍历Key和value");
			for(Map.Entry<Integer, ArrayList<String>> entry : map.entrySet()){
					System.out.println("key=="+ entry.getKey() + "and value=" + entry.getValue());
			}
			
			
		}
}
