<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String pwd=request.getParameter("pwd"); //request는 공유됨
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>footer.jsp</h1>
		<h3>비밀번호:<%=pwd %></h3>
	</center>
</body>
</html>