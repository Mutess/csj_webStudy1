<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.oreilly.servlet.*, com.oreilly.servlet.multipart.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String path=application.getRealPath("/image");
	int max=1024*1024*100;
	String enctype="UTF-8";
	MultipartRequest mr=new MultipartRequest(request,path,max,enctype,new DefaultFileRenamePolicy()); //업로드

	String name=mr.getOriginalFileName("upload");
	//이동
	response.sendRedirect("list.jsp?fn="+name);
%>