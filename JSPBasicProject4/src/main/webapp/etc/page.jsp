<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
	String msg="Hello JSP!!"; //멤버변수
	public String display() { //메소드
	return msg;
	
	/*
		public class page_jsp extends HttpServlet {
			String msg="Hello JSP!!"; //멤버변수
			public String display() { //메소드
			return msg;
		}
		public void _jspService() {
				String msg="";
			------------------------------
				this.msg //원래는 this.을 찍어야함 // 특히 super나올때
			------------------------------
		}
	}
	*/
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String msg="Hello JSP!(지역변수)";
	//page => Object
%>
<%=this.msg %>
<%-- <%=page.msg %> --%> <!-- page가 오브젝트 객체이기 때문에 page를 사용하면 안됨 -->
</body>
</html>