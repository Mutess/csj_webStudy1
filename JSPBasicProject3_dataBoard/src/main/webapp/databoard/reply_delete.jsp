<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no=request.getParameter("no"); // 삭제할 게시물 번호
	String bno=request.getParameter("bno"); //댓글이 있는 위치로 이동
	
//	dao.replyDelete(Integer.parseInt(no));
	dao.replyDelete(Integer.parseInt(no));
	
	//이동
	response.sendRedirect("detail.jsp?no="+bno);
%>