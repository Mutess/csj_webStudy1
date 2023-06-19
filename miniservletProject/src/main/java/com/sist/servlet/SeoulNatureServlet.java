package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/SeoulNatureServlet")
public class SeoulNatureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전송방식 => 브라우저로 보낸다 (미리 알려준다)
		response.setContentType("text/html;charset=UTF-8");
		//html => text/html로 알려줌, xml => text/xml로 알려줌, json => text/plain
		// HTML을 저장함 => 브라우저에서 읽어가는 위치에 저장
		PrintWriter out = response.getWriter();
		//				  => 사용자의 브라우저에서 읽어가라
		//데이터 베이스 연결
		SeoulDAO dao = SeoulDAO.newInsatance();
		List<SeoulVO> list = dao.natureListData(1); //카테코리 30개
		//카테고리 정보를 오라클로부터 받는다
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}"); //브라우저와의 거리를 벌림
		out.println(".row{");
		out.println("margin:0px auto;"); // 가운데 정렬
		out.println("width:1024px} </style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1>서울 명소</h1>");
		out.println("<div class=row>");
		for(SeoulVO vo:list) {
			out.println("<div class=\"col-md-3\">"); // 한줄에 4개 출력
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#\">");
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:280px; height:250px\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getTitle()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>"); //row
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
