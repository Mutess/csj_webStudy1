<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	EL에서 지원하는 내장객체 (581page)
	***1) requestScope => request.setAttribute()
	***2) sessionScope => session.setAttribute()
	3) param		   => request.getParameter()
	4) paramValues	   => request.getParameterValues()
 --%>
 <% 
 	String name="홍길동";
 	request.setAttribute("name", "홍길동");
 	session.setAttribute("name", "심청이"); // request와 session이 동시에 있으면 request가 우선순위 
 	// request > session
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
이름:${name}, ${requestScope.name } <%--이건 request안에 값이 설정되어 있을때 출력이 가능 --%>
<%= request.getAttribute("name") %>
<%-- 변수명 => key를 설정 --%>
</body>
</html>