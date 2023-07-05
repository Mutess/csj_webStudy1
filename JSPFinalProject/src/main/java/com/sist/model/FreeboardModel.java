package com.sist.model;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.common.commonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;
public class FreeboardModel {
	@RequestMapping("board/list.do")
	public String board_list(HttpServletRequest request, HttpServletResponse response) {
		// JSP 첫줄에 <% %>
		String page=request.getParameter("page");
		if (page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		// DAO연동
		FreeboaredDAO1 dao=FreeboaredDAO1.newInstance();
		List<FreeBoardVO> list=dao.freeboardListData(curpage);
		//총페이지
		int totalpage=dao.freeboardTotalPage();
		
		//출력에 필요한 데이터를 모아서 전송
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("list", list);
		
		// board/list.jsp로 전송
		request.setAttribute("main_jsp", "../board/list.jsp");
		// main_jsp => 화면 출력
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	//데이터 추가
	@RequestMapping("board/insert.do")
	public String board_insert(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("main_jsp", "../board/insert.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("board/insert_ok.do")
	public String board_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		FreeBoardVO vo=new FreeBoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		FreeboaredDAO1 dao=FreeboaredDAO1.newInstance();
		dao.freeboardInsert(vo);
		return "redirect:../board/list.do";
	}
	// 상세보기
	// JSP 	DispatcherServlet Model DispatcherServlet jsp
	// Model : Model / DAO / VO
	// 		-----------------
	// 화면 => main
	// AJAX => 일반 jsp
	// _ok.do => redirect
	@RequestMapping("board/detail.do")
	public String board_detail(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		FreeboaredDAO1 dao=FreeboaredDAO1.newInstance();
		FreeBoardVO vo=dao.freeboardDetailData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../board/detail.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	//Ajax
	@RequestMapping("board/delete.do")
	public void board_delete(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		String pwd=request.getParameter("pwd");
		
		FreeboaredDAO1 dao=FreeboaredDAO1.newInstance();
		String res=dao.freeboardDelete(Integer.parseInt(no), pwd);
		
		try {
			PrintWriter out=response.getWriter();
			out.println(res); // => ajax에서 읽어서 처리
		} catch (Exception e) {}
	}
	@RequestMapping("board/update.do")
	public String board_update(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		FreeboaredDAO1 dao=FreeboaredDAO1.newInstance();
		FreeBoardVO vo=dao.freeboardUpdateData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../board/update.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("board/update_ok.do")
	public String board_update_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		FreeBoardVO vo=new FreeBoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		vo.setNo(Integer.parseInt(request.getParameter("no")));
		FreeboaredDAO1 dao=FreeboaredDAO1.newInstance();
		boolean bCheck=dao.freeboardUpdate(vo);
		
		request.setAttribute("bCheck", bCheck);
		request.setAttribute("no", vo.getNo());
		return "../board/update_ok.jsp";
	}
}
