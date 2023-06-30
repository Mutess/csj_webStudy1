package com.sist.controller;

import java.io.*;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
/*
		MVC 동작 과정
		----------
		1. 요청 (JSP) => DispatcherServlet을 찾는다 
			(*.do)				|
			list.do				|
			insert.do			|
			.........		서버에서 받을 수 있는 부분 URI, URL
							URI => Model을 찾는다
		2. DispatcherServlet(Controller)
			=> Front Controller : 요청 => Model 연결 => request를 전송
										----------
											요청 처리 기능을 가지고 있다.
			/JSPMVCFinalProject/insert.do?a=1 / 뒤에 a=1은 DispatcherServlet이 못 가져옴
			  
		3. MVC목적 : 보안 (JSP => 배포 : 소스를 통으로 배포함)
					역할분단 (Front(jsp), Back(java, dao))
					자바와 HTML을 분리하는 이유
					---
					확장성, 재사용, 변경이 쉽다 (JSP는 한번 사용하면 버린다.)
					MVC, MVVP, MVP, MVVM
		4. 동작 순서
						.do							request
			JSP(링크, 버튼) ------- DisPatcherServlet -------- Model (DAO <=> 오라클)
															 결과값을 request에 담아준다
															 request.setAttribute()
				JSP		 ------- DispatcherServlet ---------
				---		 request				   	request를 넘겨준다
				 |
			request.getAttribute() => $()
		
		5. DispatcherServlet은 최대한 고정
		   ---------------------------
		
		6. 등록 (Model클래스) => XML로 세팅 (메뉴판)
		
		7. 메소드 찾기 => 어노테이션 (메소드 자동호출이 가능)
		----------------------------------------
		어려움 : 맥/위도우 연동
		
		Spring controller 구조
 */
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.net.*;
import java.util.*;

@WebServlet("*.do") // *은 구분자
public class DisPatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> clsList=new ArrayList<String>();
//	초기화 => XML에 등록된 클래스 읽기 (메뉴)
	public void init(ServletConfig config) throws ServletException {
		try {
			URL url=this.getClass().getClassLoader().getResource(".");// 현재 폴더
			File file=new File(url.toURI());
//			System.out.println(file.getPath());
//			C:\webDev\webStudy1\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\JSPMVCFinalProject\WEB-INF
			String path=file.getPath();
			path=path.substring(0, path.lastIndexOf(File.separator));
			System.out.println(path);
			path=path+File.separator+"application.xml";
			
			//XML 파싱
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			// 파서기 (XML => DocumentBuilder, HTML => Jsoup)
			DocumentBuilder db=dbf.newDocumentBuilder();
			// 파서
			Document doc=db.parse(new File(path));
			// 필요한 데이터 읽기
			// root태그 => beans
			Element beans=doc.getDocumentElement(); //테이블 이름을 가지고 올때 사용
			System.out.println(beans.getTagName());
			
			// 같은 태그를 묶어서 사용
			NodeList list = beans.getElementsByTagName("bean");
			for(int i=0;i<list.getLength();i++) {
				// bean 태그를 1개씩 가지고 온다
				Element bean=(Element)list.item(i);
				String id=bean.getAttribute("id");
				String cls=bean.getAttribute("class");
				System.out.println(id+":"+cls);
				clsList.add(cls);
			}
		} catch (Exception ex) {}
	}
//	웹에서 사용자 요청 => servlet / jsp
//	servlet : 화면 출력은 하지 않는다 (연결)
//	화면 : jsp(View)
/*
	Controller : Servlet
		Spring : DispatcherServlet
		Struts : ActionServlet
		Struts2 : FilerDispatcher
					   ---------- 배달부 (request)
	Model : 요청처리 => java
	view : 화면 출력 => jsp
					 ---- HTML
	----------------------------
 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String path=request.getRequestURI();
			path=path.substring(request.getContextPath().length()+1);
			//http://localhost
			///JSPMVCFinalProject/food/category.do
			// ------------------ getContextPath()
			//food/category.do
			for (String cls:clsList) {
				// class 정보 읽기 => 리플렉션
				Class clsName=Class.forName(cls);
				// 메모리 할당
				Object obj=clsName.getDeclaredConstructor().newInstance();
				// 메소드를 읽어 온다
				Method[] methods = clsName.getDeclaredMethods();
				for (Method m:methods) {
					RequestMapping rm=m.getAnnotation(RequestMapping.class);
					if(rm.value().equals(path)) {
						String jsp=(String)m.invoke(obj, request,response);
						if (jsp==null) { //void (ajax)
							return;
						}else if (jsp.startsWith("redirect:")) {
							// sendRedirect
							response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
						}else {
							RequestDispatcher rd=request.getRequestDispatcher(jsp);
							rd.forward(request, response);
						}
						return;
					}
				}
			}
		}catch (Exception e) {}
	}

}
