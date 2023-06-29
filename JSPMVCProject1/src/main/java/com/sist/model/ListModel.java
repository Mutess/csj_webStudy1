package com.sist.model;

import javax.servlet.http.HttpServletRequest;
/*
		사용자 요청 => controller ==> Model을 찾는다.
						|				|
						|			  요청처리
						|			  ------ 결과물 request, response에 담는다
						|			  
				request,session을 전송한다
						|
					jsp를 찾는다
						|
					jsp에 request,session 전송
 */	
public class ListModel {
	public String execute(HttpServletRequest request) {
		request.setAttribute("msg", "목록출력");
		return "list.jsp";
	}
}
