<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%--
	_jspService() { ==> 브라우저에 실행하는 메소드 => service
		==============
			 JSP
		==============
	}
 --%>
<%
	// 자바 영역
	// DAO
	MemberDAO dao=MemberDAO.newInstance();
	request.setCharacterEncoding("UTF-8"); //한글 깨짐 방지
	String dong=request.getParameter("dong"); // DB는 null이 위험하기 때문에 null일떄랑 아닐 때를 구분
	int count=0;
	List<ZipcodeVO> list=null;
	if (dong!=null) {
		count=dao.postfindcount(dong); //0이면 검색한 결과가 없음
		list=dao.postfind(dong);
	}
	// Model1(JSP) => MV 구조 (자바하고 HTML만 분리) => MVC로 컨트롤러 제작
%>
<%--
	실행시에 => JSP => servlet 클래스로 변경
				 톰캣
	post.jsp=> post_jsp.java
			   -------------
			   		 |
			   	  compile
			   	  	 |
			   	  post_jsp.class
			   	  	 |
			   	  인터프리터
			   	     |
			   	   메모리에 HTML만 출력한다.
			   	   -------------------- 버퍼 => 브라우저에서 읽어서 출력
			   변환 => 다른 프로그램과 연결 WAS
			   						---- 톰캣
			   						Web Application Server
			   1) 구성요소
			   	  => AWS : Java / Tomcat => 프로젝트 파일을 묶어서 올려준다
			   	  server : Web Server => 아파치 / IIS
			   	  			 | WAS(미들웨어) => 톰캣 (연습용 웹서버가 내장)
			   	  client : 브라우저
	JSP => Java Server Page => 서버에서 실행되는 자바파일
	===== 스크립트 형식
	1) 지시자
		= page
			<%@ page 속성=값... %> : JSP 파일 정보
			속성
			= contentType : 브라우저와 연결 => 파일형태를 알려주는 역할
			  text/html  => html
			  text/xml   => xml
			  text/plain => json
			  -------------------------default:영어 => charset=UTF-8
			= import : 다른 라이브러리 로딩시에 사용
				<%@ page import="java.util.*, java.io.*, ..."%>
				<%@ page import="java.util.*"%>
				<%@ page import="java.io.*"%>
			= errorPage : error가 발생했을때 이동하는 파일을 지정
				<%@ page errorPage="error.jsp"%>
			= 파일의 확장자는 변경이 가능 (.do, .nhn...)
			
		= include 지시자 : JSP안에 특정위치 다른 JSP를 포함
			<%@ include file="a.jsp"%>
		= taglib 지시자 : 자바의 제어문, 변수선언, String
						=> 태그로 만들어서 제공
			<% 
				for(int i=0;i<=10;i++){
			%>	
				<ul>	
				</ul>
			<%
				}
			%>
			
			==> Spring에서 JSP사용시 <MVC>
			<c:forEach var="i" begin="0" end="10" step="1">
				<ul>
				</ul>
			</c:forEach>
			
	1-1) 스크립트릿
			자바언어 분리
			선언식 : <%! 메소드 선언, 전연변수 %> => 사용빈도가 없다
			표현식 : <%= 화면 출력내용 %> => out.println()
			스크립트릿 : <% 일반자바 : 지역변수, 메소드 호출, 객체 생성, 제어문 %>
	
	2) 내장 객체
			자바에서 지원하는 객체
			***= request : 사용자가 보내준 데이터 관리
			***= response : 응답 (HTML, Cookie)
			***= session : 서버에 사용자 정보 일부 저장, 장바구니...
			***= application : 서버 관리 => getRealPath()
			***= out <%= %>
			***= pageContext => 페이지 흐름
				 <jsp:include> <jsp:forward> => JSP액션태그로 변경
			= config (web.xml)
			***= exception (try~catch)
			***= page(this)
	3) JSP 액션 태그
		<jsp:useBean> : 클래스 객체 생성
		<jsp:setProperty> : set메소드에 값을 채운다
		<jsp:getProperty> : get메소드 호출
		<jsp:param> : 데이터 전송
		<jsp:include> : 특정 위치에 다른 JSP를 추가
		<jsp:forward> : 화면 이동
			= sendRedirect() => request를 초기화
			= forward() => request를 계속 유지
			
	4) JSTL / EL
		=> JSTL : 태그로 라이브러리를 만들어 준다 (자바에 for문도 가능)
		=> EL : 화면 출력 => <%= %> (X) => ${}
		
	5) MVC : 자바 / HTML을 분리해서 코딩
		   (Model) (View)
		   --------------
		   		연결 => Controller(서블릿)
		   	 => Back - Front
		   	 => 98%(MVC), 2%(Model1)
		   	 => ASP , PHP => Spring => Java(Kotlin)
 --%>
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
	width: 450px; 
}
h1 {
	text-align: center;
}
td, th {
	font-size: 12px;
}
a {
	text-decoration: none;
	color: black
}
a:hover {
	text-decoration: underline;
	color: cyan;
}
</style>
<script type="text/javascript">
let ok=(zip,addr)=>{
	//opener => js7.html (팝업창을 열어주는 파일)
	//우편번호 000-000
	//우편번호를 - 기준으로 잘라서 입력함
	opener.frm.post1.value=zip.substring(0,3);
	opener.frm.post2.value=zip.substring(4,7);
	opener.frm.addr.value=addr;
	self.close(); //자신 => post.jsp를 닫음
	
}
</script>
</head>
<body>
	<div class="container">
		<h1>우편번호 검색</h1>
		<div class="row">
		  <form method="post" action="post.jsp">
			<table class="table">
				<tr>
					<td>
					우편번호 : <input type="text" name=dong size=15 class="input-sm">
							<input type=submit value="검색" class="btn btn-sm btn-danger">
					</td>
				</tr>
			</table>
			</form>
			<div style="height:20px"></div>
			<%
				if(list!=null) { // 처음에 dong값이 null값이기 때문에 값이 입력될때! DB에 연결해라
			%>
					<table class=table>
						<tr>
							<th width=20% class="text-center">우편번호</th>
							<th width=80% class="text-center">주소</th>
						</tr>
						<%
							if(count==0) {
						%>
							<tr>
								<td colspan="2" class="text-center">
									<h3>검색된 결과가 없습니다.</h3>
								</td>
							</tr>
						<%
							}
							else {
								for (ZipcodeVO vo:list) {
						%>
								<tr>
									<td width="20%" class="text-center">
										<%=vo.getZipcode() %>
									</td>
									<td width="80%">
										<a href="javascript:ok('<%=vo.getZipcode()%>','<%=vo.getAddress()%>')"> <%=vo.getAddress()%></a>
									</td>
								</tr>
						<%
								}
							}
						%>
					</table>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>