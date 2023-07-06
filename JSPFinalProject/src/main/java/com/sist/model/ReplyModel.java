package com.sist.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class ReplyModel {
	@RequestMapping("reply/reply_insert.do")
	public String reply_insert(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String cno=request.getParameter("cno");
		String type=request.getParameter("type");
		String msg=request.getParameter("msg");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		ReplyVO vo=new ReplyVO();
		vo.setCno(Integer.parseInt(cno));
		vo.setType(Integer.parseInt(type));
		vo.setId(id);
		vo.setName(name);
		vo.setMsg(msg);
		
		//DAO연동
		ReplyDAO dao=ReplyDAO.newInstance();
		dao.replyInsert(vo);
		return "redirect:../food/food_detail.do?fno="+cno;
	}
}
