<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	int a=10;
%>
<%-- <%@ include file="b.jsp" %> --%>
<jsp:include page="b.jsp"></jsp:include> <%-- HTML만 복사해서 에러가 안남 --%>
묶을떄 같이 이름의 변수가 있으면 오류가남
</body>
</html>