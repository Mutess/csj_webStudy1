<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String name="홍길동";
	// request에 저장 (데이터 추가)
/* 	request.setAttribute("name", name); */
	
%>
<c:set var="name" value="심청이"/> <%-- EL에서 출력이 가능하게 변수를 설정한다. --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>자바에서 출력</h1>
	이름1:<%=name %><br>
	이름2:<%=request.getAttribute("name") %><br>
	
<h1>EL</h1>
	이름 3:${name } <%-- request.getAttribute("name") --%>
	<br>
	<%-- JQuery와 충돌방지 
		 츨력 => $
		 VueJS => {{}}
		 React => {}
	--%>
	이름 4:<c:out value="${name }"/><br>
	이름 5:<c:out value="<%=name %>"/>
	<%-- 자바스크립트에서 JSTL 사용이 가능 --%>
	<%-- 연결할때 JSON으로 다른 프로그램 연결 --%>
</body>
</html>