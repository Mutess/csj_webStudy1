<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	JSP
	---
	 1. 동작 순서
	 	--------------------------
	 	1) client 요청(주소창을 이용해서 서버에 연결)
	 	http://localhost:8080/JSPBasicProject2/jsp/request(%EB%82%B4%EC%9E%A5%EA%B0%9D%EC%B2%B4).jsp
	 	----   --------- ---- ----------------
	 protocol  serverIP	 port		  | ContextPath
	 	--------------------- ----------------------------------------------------------------------
	 			  | 서버 관련									| 클라이언트 요청 관련
	 			  ------------------------------------------------------------ URL
	 			  								| URI					
	 	2) DNS을 거쳐서 => localhost(도메인) => ip변경
	 	3) ip/port를 이용해서 서버에 연결
	 		new Socket(ip,port) => TCP
	 	----------------------------------
	 	4) web Server
	 		httpd
	 		-----
	 		  = HTML, XML, CSS, JSON, => web Server 자체에서 처리후에 브라우저로 전송
	 		  = JSP / Servlet은 처리하지 못한다.
	 		    ---------------------------
	 		    			|
	 		    	web Container (WAS) => Java로 변경
	 		    						=> 컴파일
	 		    						=> 실행
	 		    						   ---
	 		    						   	실행결과를 메모리에 모아둔다
	 	5) 메모리에 출력한 내용을 브라우저로 응답
	 	
	 JSP (Java Server Page) : 서버에서 실행되는 자바파일
	 -------------------------------------------
	 	_jspInit() => web.xml => 초기화
	 	_jspService() => 사용자 요청을 처리하고 결과값을 HTML로 전송
	 	-------------- 공백
	 	{
	 		영역에 소스 코딩 => JSP
	 	}
	 	_jspDestroy() => 새로고침, 화면이동시... 메모리에서 해제
	 public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
	 {
	 	final javax.servlet.jsp.PageContext pageContext;
	    javax.servlet.http.HttpSession session = null;
	    final javax.servlet.ServletContext application;
	    final javax.servlet.ServletConfig config;
	    javax.servlet.jsp.JspWriter out = null;
	    final java.lang.Object page = this;
	    javax.servlet.jsp.JspWriter _jspx_out = null;
	    javax.servlet.jsp.PageContext _jspx_page_context = null;
	    -------------------------------------------------------- 내장 객체
	    
	    소스 코딩 영역
	    ==> JSP
	 }
	 		
	 2. 지시자
	 	page 형식> <%@ page 속성="" 속성="" %>
	 	----
	 	JSP파일에 대한 정보
	 	속성 : 
	 		 contextType=""
	 		 		= 브라우저에 어떤 파일인지 알려준다.
	 		 		  ----- HTML / XML / JSON (외에는 일반 텍스트)
	 		 		  		----   ---   ----
	 		 		  		  |		|	 text/plain => Restful
	 		 		  		  |		text/xml
	 		 		  		text/html
	 		 import : 라이브러리 읽기
	 		 import="java.util.*, java.io.*"
	 		 errorPage : error시에 이동하는 페이지 지정
	 		 buffer="8kb" => 16kb 32kb...
	 		 
	 3. 스크립트 사용법
	 	자바가 코딩되는 영역
	 	<%! %> : 선언문 (메소드, 멤버변수) => 사용빈도가 거의 없다
	 	<%  %> : 자바코딩(일반자바) => 제어문, 메소드 호출, 지역변수..
	 	<%= %> : 화면 출력
	 			 out.println(여기에 들어가는 코딩)
	 	JSP = Model1은 2003년에 유행 => Model2(MVC) => Domain(MSA)
	 		  -------------------		 |				|
	 		  						   Spring4	  Spring5/Spring6
	 	=> 표현식 , 스크립트릿
	 	   ${}	  JSTL
	 	=> JSP안에서는 태그형으로 제작	 		  
	 4. 내장 객체
	 	=> 165page
	 	9가지 지원
	 		***= request => HttpServletRequest
	 		request는 관리자 => 톰캣
	 		1) 서버정보 / 클라이언트 브라우저 정보
	 			getServerInfo()
	 			getPort()
	 			getMethod()
	 			getProtocol()
	 			***getRequestURL()
	 			***getRequestURI()
	 			***getContextPath()
	 		2) 사용자 요청 정보
	 			데이터 전송시 => 데이터가 request에 묶여서 들어온다
	 				= 단일 데이터
	 					getParameter()
	 				= 다중 데이터
	 					getParameterValues() => checkbox, select => multiline
	 				= 한글 변환 (디코딩)
	 					setCharacterEncoding => UTF-8
	 				= 키를 읽는다
	 					getParameterNames()
	 					받는 파일명?no=1&name=aaa
	 					-------
	 					 a.jsp?no=1&name=aaa&hobby=a&hobby=b&hobby=c
	 					 					 ------------------------
	 					 					 getParameterValues() => 리턴값이 여러개라서 이걸로 받아야됨
	 		3) 추가 정보 => MVC
	 			setAttribute() : request 데이터 추가 전송
	 			getAttribute() : 전송된 데이터 읽기
	 		***= response => HttpServletResponse
	 			= Header 정보
	 				다운로드 => 파일명, 크기
	 				setHeader()
	 			= 응답 정보
	 				= HTML전송 => text/html
	 				= Cookie전송 => addCookie
	 			= 화면 이동
	 				= sendRedirect()
	 		***= session => HttpSession
	 		***= out 	 => JspWriter
	 		***= application => ServletContext
	 		***= pageContext => PageContext
	 		= page => Object (this)
	 		= exception => Exception => try ~ catch
	 		= config => ServletConfig => web.xml
	 	--------------------------------------
	 		페이지 입출력 
	 			request, response, out
	 	--------------------------------------
	 		외부 환경 정보 
	 			config
	 	--------------------------------------
	 		서블릿 관련
	 			application, pageContext, session
	 	--------------------------------------
	 		예외 처리 관련 
	 			exception
	 	--------------------------------------
	 5. 액션 태그
	 6. include
	 7. cookie
	 8. JSTL
	 9. EL
	 10. MVC
	 
	 String a=request.getParameter("a");
	 String b=request.getParameter("b");
	 String c=request.getParameter("c");
	 
	 <jsp:setProperty name="vo" property="*"/>
 --%>
<%
	pageContext.include("");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
.container {
	margin-top:50px;
}
.row {
	margin: 0px auto;
	width: 100%; 
}
h1 {
	text-align: center;
}
</style>
</head>
<!-- 170page : getParameter, getParameterValues -->
<body>
	<div class="container">
		<h1>개인 정보</h1>
		<div class="row">
		<form method="post" action="request_ok.jsp">
			<table class="table">
			<tr>
				<th class="text-center" width=20%>이름</th>
				<td	width=80%>
					<input type="text" name="name" size=15 class="input-sm">
				</td>
			</tr>
			<tr>
				<th class="text-center" width=20%>성별</th>
				<td	width=80%>
					<%-- 라디오버튼은 받드시 그룹 (name통일) --%>
					<input type="radio" name="sex" value="남자" checked>남자
					<input type="radio" name="sex" value="여자">여자
				</td>
			</tr>
			<tr>
				<th class="text-center" width=20%>전화번호</th>
				<td	width=80%>
					<%--
						getParameter("tel") => name="tel"
					 --%>
					<select name="tel" class="input-sm">
						<option>010</option>
					</select>
					<input type="text" name="tel2" size=15 class="input-sm">
				</td>
			</tr>
			<tr>
				<th class="text-center" width=20%>소개</th>
				<td	width=80%>
					<textarea rows="8" cols="50" name="content"></textarea>
				</td>
			</tr>
			<tr>
				<th class="text-center" width=20%>취미</th>
				<td	width=80%> <%-- 배열로 받음 --%>
					<input type="checkbox" name="hobby" value="운동">운동
					<input type="checkbox" name="hobby" value="등산">등산
					<input type="checkbox" name="hobby" value="낚시">낚시
					<input type="checkbox" name="hobby" value="공부">공부
					<input type="checkbox" name="hobby" value="자전거">자전거
					<input type="checkbox" name="hobby" value="여행">여행
					<input type="checkbox" name="hobby" value="컴퓨터">컴퓨터
				</td>
			</tr>
			<tr>
				<td colspan="2" class="text-center">
					<button class="btn btn-sm btn-danger">전송
				</td>
			</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>