<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="wrapper row1">
  <header id="header" class="clear"> 
    <!-- ################################################################################################ -->
    <div id="logo" class="fl_left">
      <h1><a href="../main/main.do">Food & Recipe</a></h1>
    </div>
    <div class="fl_right">
      <ul class="inline">
        <li><i class="fa fa-envelope-o"></i><input type="text" name=id class="input-sm" size=15></li>
        <li><i class="fa fa-envelope-o"></i><input type="password" name=pwd class="input-sm" size=15></li>
        <li><input type="button" value=로그인 class="btn btn-sm btn-danger" size=15></li>
      </ul>
    </div>
    <!-- ################################################################################################ --> 
  </header>
</div>
<!-- ################################################################################################ --> 
<!-- ################################################################################################ --> 
<!-- ################################################################################################ -->
<div class="wrapper row2">
  <nav id="mainav" class="clear"> 
    <!-- ################################################################################################ -->
    <ul class="clear">
      <li class="active"><a href="../main/main.do">홈</a></li>
      <li><a class="drop" href="#">맛집</a>
        <ul>
          <li><a href="pages/gallery.html">지역별 맛집찾기</a></li>
          <li><a href="pages/full-width.html">맛집 예약</a></li>
          <li><a href="pages/sidebar-left.html">맛집 추천</a></li>
        </ul>
      </li>
      <li><a class="drop" href="#">레시피</a>
        <ul>
          <li><a href="pages/gallery.html">레시피</a></li>
          <li><a href="pages/full-width.html">쉐프</a></li>
          <li><a href="pages/sidebar-left.html">레시피</a></li>
        </ul>
      </li>
      <li><a class="drop" href="#">서울여행</a>
        <ul>
          <li><a href="pages/gallery.html">명소</a></li>
          <li><a href="pages/full-width.html">자연 & 관광</a></li>
          <li><a href="pages/sidebar-left.html">쇼핑</a></li>
          <li><a href="pages/sidebar-left.html">여행코스</a></li>
        </ul>
      </li>
      <li><a href="#">레시피 스토어</a></li>
      <li><a class="drop" href="#">커뮤니티</a>
        <ul>
          <li><a href="pages/gallery.html">공시사항</a></li>
          <li><a href="pages/full-width.html">자유게시판</a></li>
          <li><a href="pages/sidebar-left.html">묻고 답하기</a></li>
        </ul>
      </li>
      <li><a href="#">마이페이지</a></li>
    </ul>
    <!-- ################################################################################################ --> 
  </nav>
</div>
<!-- ################################################################################################ --> 
<!-- ################################################################################################ --> 
<!-- ################################################################################################ -->
</body>
</html>