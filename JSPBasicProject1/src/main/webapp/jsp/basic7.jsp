<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  errorPage="error.jsp"%>
<%
	int a = 10/0; 
/*
	500 : 자바 문법 오류
	404 : 파일을 못찾는 오류
	400 : bad request
		  ?no=10 ==> boolean no면 오류
	412 : 한글 변환 코드 문제
		  UTF-8 ==> UFT-8
	200 : 정상
	403 : 접근거부

*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%=a %>
</body>
</html>