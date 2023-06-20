<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
//	response.sendRedirect("response_2.jsp");

	RequestDispatcher rd = request.getRequestDispatcher("response_2.jsp");
	rd.forward(request, response);
	System.out.println(request);
	
	// forward는 파일명(원본)을 안바뀌는데 안에 내용이 바뀌는 기법
%>
