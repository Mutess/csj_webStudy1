package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.common.commonModel;
import com.sist.controller.RequestMapping;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class AdminPageModel {
	@RequestMapping("adminpage/adminpage_main.do")
	public String admin_main(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("adminpage_jsp", "../adminpage/adminpage_reserve.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/adminpage_reserve.do")
	public String admin_reserve(HttpServletRequest request, HttpServletResponse response) {
		ReserveDAO dao=ReserveDAO.newInstance();
		List<ReserveVO> list=dao.reserveAdminData();
		request.setAttribute("list", list);
		request.setAttribute("adminpage_jsp", "../adminpage/adminpage_reserve.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/admin_reserve_ok.do")
	public String admin_ok(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		ReserveDAO dao=ReserveDAO.newInstance();
		dao.reserveOk(Integer.parseInt(no));
		return "redirect:../adminpage/adminpage_reserve.do";
	}
	// 공지사항 처리
	// 이동 => request가 전송(return => .jsp) => forward(화면에 다른 화면을 덮어쓰는 기법)/ 미전송
	@RequestMapping("adminpage/notice_list.do")
	public String notice_list(HttpServletRequest request, HttpServletResponse response) {
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		NoticeDAO dao=NoticeDAO.newInstance();
		List<NoticeVO> list=dao.noticeListData(curpage);
		int totalpage=dao.noticeTotalPage();
		
		String[] msg= {"","일반공지","이벤트 공지","맛집공지","여행공지","레시피공지"};
		for(NoticeVO vo:list) {
			vo.setNotice_type(msg[vo.getType()]);
		}
		// 페이징
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		// 
		request.setAttribute("adminpage_jsp", "../adminpage/notice_list.jsp"); // 그위에 공지 사항 리스트를 올리는 방식
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp"); //메인을 올리고  
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/notice_insert.do")
	public String notice_insert(HttpServletRequest request, HttpServletResponse response) {
		
		
		request.setAttribute("adminpage_jsp", "../adminpage/notice_insert.jsp"); // 그위에 공지 사항 리스트를 올리는 방식
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp"); //메인을 올리고  
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/notice_insert_ok.do")
	public String notice_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String type=request.getParameter("type");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		NoticeVO vo=new NoticeVO();
		vo.setType(Integer.parseInt(type));
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setId(id);
		vo.setName(name);
		
		//DAO연동
		NoticeDAO dao=NoticeDAO.newInstance();
		dao.noticeInsert(vo);
		return "redirect:../adminpage/notice_list.do";
	}
	@RequestMapping("adminpage/notice_delete.do")
	public String notice_delete(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		
		//DAO연동
		NoticeDAO dao=NoticeDAO.newInstance();
		dao.noticeDelete(Integer.parseInt(no));
		return "redirect:../adminpage/notice_list.do";
	}
	// Model => request => JSP
	@RequestMapping("adminpage/notice_update.do")
	public String notice_update(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		NoticeDAO dao=NoticeDAO.newInstance();
		NoticeVO vo=dao.noticeUpdateDate(Integer.parseInt(no));
		request.setAttribute("vo", vo);
		request.setAttribute("adminpage_jsp", "../adminpage/notice_update.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/notice_update_ok.do")
	public String notice_update_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String type=request.getParameter("type");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String no=request.getParameter("no");
		
		NoticeVO vo=new NoticeVO();
		vo.setType(Integer.parseInt(type));
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setNo(Integer.parseInt(no));
		
		NoticeDAO dao=NoticeDAO.newInstance();
		dao.noticeUpdate(vo);
		return "redirect:../adminpage/notice_list.do";
	}
	// 답하기
	@RequestMapping("adminpage/replyboard_list.do")
	public String adminpage_replyboard_list(HttpServletRequest request, HttpServletResponse response) {
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
		List<ReplyBoardVO> list=dao.replyboardAdminListData(curpage);
		int totalpage=dao.replyboardAdminTotalPage();
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("adminpage_jsp", "../adminpage/replyboard_list.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/replyboard_insert.do")
	public String adminpage_replyboard_insert(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		
		request.setAttribute("no", no);
		request.setAttribute("adminpage_jsp", "../adminpage/replyboard_insert.jsp");
		request.setAttribute("main_jsp", "../adminpage/adminpage_main.jsp");
		commonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/replyboard_insert_ok.do")
	public String admimpage_replyboard_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String pno=request.getParameter("pno");
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		ReplyBoardVO vo=new ReplyBoardVO();
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setId(id);
		vo.setName(name);
		
		ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
		dao.replyBoardAdminInsert(Integer.parseInt(pno), vo);
		
		return "redirect:../adminpage/replyboard_list.do";
	}
}
