<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	메소드방식: <%= request.getMethod() %><br>
	<%-- post방식 : 자바스크립트, <form> --%>
	서버주소:<%= request.getServerName() %><br>
	서버포트:<%= request.getServerPort() %><br>
	***클라이언트:<%= request.getRemoteAddr()%> <br> <%-- 조회수 증가 될때 같은 아이피나 아이디를 통해 조회하는 경우 조회수가 1번만 증가되게 해야함 --%>
	<%-- WebSocket : 실시간 상담 --%>
	***URL:<%= request.getRequestURL() %><br>
	***URI:<%= request.getRequestURI() %><br>
	***contextPath:<%= request.getContextPath() %><br>
	브라우저:<%= request.getHeader("User-Agent") %>
</body>
</html>