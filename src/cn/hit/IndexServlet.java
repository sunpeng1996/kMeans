package cn.hit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hit.servlet.BaseServlet;

public class IndexServlet extends BaseServlet {
	public String  body(HttpServletRequest request, HttpServletResponse response) {
		 return "index.jsp";
	}
		
}
