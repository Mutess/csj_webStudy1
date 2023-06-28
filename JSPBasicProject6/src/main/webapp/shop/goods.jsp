<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.util.*"%>
<jsp:useBean id="dao" class="com.sist.dao.GoodsDAO"/>
<% 
	String strPage=request.getParameter("page");
	if(strPage==null)
		strPage="1";
	int curPage=Integer.parseInt(strPage);
	List<GoodsBean> list=dao.goodListData(curPage);
	// 이미지카드 정렬
	for(GoodsBean vo:list) {
		String name=vo.getName();
		if(name.length()>27){
			name=name.substring(0,27)+"...";
			vo.setName(name);
		}
		vo.setName(name);
	}
	int totalpage=dao.goodsTotalPage();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
 <style type="text/css">
 .container{
   margin-top: 50px;
 }
 .row{
  margin:0px auto;
  width:960px;
 }
 </style>
</head>
<body>
	<div class="container">
	 <div class="row">
		<div class="text-right">
			<%
				String id=(String)session.getAttribute("id");
				if(id!=null){ //session에 저장이 됨 (로그인이 되었다면)
 			%>
 					<%=session.getAttribute("name") %>님이 로그인 하셨습니다
 					&nbsp;
 					<a href="logout.jsp" class="btn btn-sm btn-primary">로그아웃</a>
 			<%
				}
				else{
			%>
					<a href="login.jsp" class="btn btn-sm btn-danger">로그인</a>
			<%
			
				}
			%>
		</div>
		<div style="height: 10px;"></div>
		<div class="row">
	 <%-- 맛집 목록 --%>
	 <%
	 	for(GoodsBean vo:list)
	 	{
	 %>
	 		<div class="col-md-3">
    		<div class="thumbnail">
     			<a href="goods_detail.jsp?no=<%=vo.getNo()%>">
       			 <img src="<%=vo.getPoster() %>"  style="width:100%">
       			 <div class="caption">
        		  <p><%=vo.getName() %></p>
        		</div>
      			</a>
    		</div>
  		</div>
	 <%		
		}
	 %>
	</div>
	 </div>
	 <div style="height: 20px"></div>
	  <div class="row">
	  <%-- 페이지 --%>
	    <div class="text-center">
	     <a href="goods.jsp?page=<%=curPage>1?curPage-1:curPage %>" class="btn btn-sm btn-danger">이전</a>
	     <%=curPage %> page/ <%=totalpage %> pages
	     <a href="goods.jsp?page=<%=curPage<totalpage?curPage+1:curPage %>" class="btn btn-sm btn-primary">다음</a>
	    </div>
</body>
</html>