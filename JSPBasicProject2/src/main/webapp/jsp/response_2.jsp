<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	여기는 request가 초기화가 된 상태라 ID/PW값이 전달되지 않았음
 --%>
<%
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	System.out.println(request);
%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
ID:<%=id %>
pwd:<%=pwd %>
</body>
</html>