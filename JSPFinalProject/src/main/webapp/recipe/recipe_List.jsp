<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.row {
	margin: 0px auto;
	width: 860px;
}
</style>
</head>
<body>
	<div class="wrapper row3">
	  <main class="container clear"> 
	    <h2 class="sectiontitle">레시피</h2>
	    <!-- main body --> 
	    <!-- ################################################################################################ -->
	    <div class="content"> 
	      <!-- ################################################################################################ -->
	      <div id="gallery">
	        <figure>
	          <header class="heading">
	           총 <span style="color: green; font-size: 25pt">
	           	<fmt:formatNumber value="${count }" pattern="###,###"/>
	           </span>개의 맛있는 레시피가 있습니다.
	          </header>
	          <ul class="nospace clear">
	           <c:forEach var="vo" items="${list }" varStatus="s">
	              <li class="one_quarter ${s.index%4==0?'first':'' }"><a href="#"><img src="${vo.poster }" title="${vo.title }"></a></li>
	           </c:forEach> 
	          </ul>
	            
	        </figure>
	      </div>
	      <!--  class=current -->
	      <!-- ################################################################################################ --> 
	      <!-- ################################################################################################ -->
	      <nav class="pagination">
	        <ul>
	         <%-- startPage : 1 , 11 , 21 , 31... --%>
	         <c:if test="${startPage>1 }">
	          <li><a href="../recipe/recipe_List.do?page=${startPage-1 }&type=${type}">&laquo; Previous</a></li>
	         </c:if>
	         
	         <c:forEach var="i" begin="${startPage }" end="${endPage }">
	            <li ${curpage==i?"class=current":"" }><a href="../recipe/recipe_List.do?page=${i }&type=${type}">${i }</a></li>
	         </c:forEach>
	         
	          
	         <c:if test="${endPage<totalpage }">
	          <li><a href="../recipe/recipe_List.do?page=${endPage+1 }&type=${type}">Next &raquo;</a></li>
	         </c:if>
	        </ul>
	      </nav>
	      <!-- ################################################################################################ --> 
	    </div>
	    <!-- ################################################################################################ --> 
	    <!-- / main body -->
	    <div class="clear"></div>
	  </main>
	</div>
</body>
</html>