<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<jsp:useBean id="dao" class="com.sist.dao.FoodDAO16"></jsp:useBean>
<%
 	String strPage=request.getParameter("page");
	if(strPage==null)
		strPage="1";
	int curPage=Integer.parseInt(strPage);
	List<FoodBean> list = dao.foodListData(curPage);
	int totalpage=dao.foodTotalPage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
	margin-top: 50px;
}
.row {
	margin: 0px auto;
	width: 960px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
		<%
			for(FoodBean vo:list){
		%>
		
				<div class="col-md-3">
				     <div class="thumbnail">
				       <a href="#" target="_blank">
				         <img src="<%=vo.getPoster() %>" style="width: 100%">
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
		<div style="height: 10px"></div>
		<div class="text-center">
			<a href="food.jsp?page=<%=curPage>1?curPage-1:curPage %>" class="btn btn-sm btn-danger">이전</a>
			<%=curPage %> page / <%=totalpage %> page
			<a href="food.jsp?page=<%=curPage<totalpage?curPage+1:curPage %>" class="btn btn-sm btn-danger">다음</a> 
		</div>
	</div>
</body>
</html>