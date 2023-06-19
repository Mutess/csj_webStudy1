package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;


@WebServlet("/FoodDetailBeforeServlet")
public class FoodDetailBeforeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode=request.getParameter("mode");
		String fno=request.getParameter("fno");
		//저장
		Cookie cookie = new Cookie("food_"+fno, fno);
		cookie.setMaxAge(60*60*24); //쿠키저장 기간 설정 : 60*60*24 = 하루
		cookie.setPath("/"); //root에 저장함
		response.addCookie(cookie);
		// 본인의 브라우저에 저장
		// 단점 : 보안취약, 저장값은 무조건 String
		/*
			1. 쿠키생성
				Cookie cookie = new Cookie("food_"+fno, fno);
			2. 저장 기간을 설정
			3. 저장 ROOT를 결정
			4. 브라우저로 전송
			5. response는 HTML/Cookie를 브라우저로 전송하는 역할
				=> 한개의 서블릿이나 JSP에서 한개만 전송이 가능
		 */
		response.sendRedirect("MainServlet?mode="+mode+"&fno="+fno);
	}

}
