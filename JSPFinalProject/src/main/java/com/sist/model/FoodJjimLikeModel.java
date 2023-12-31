package com.sist.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;
public class FoodJjimLikeModel {
	@RequestMapping("jjim/jjim_insert.do")
	public String jjim_insert(HttpServletRequest request, HttpServletResponse response) {
		String fno=request.getParameter("fno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		FoodJJimVO vo=new FoodJJimVO();
		vo.setId(id);
		vo.setFno(Integer.parseInt(fno));
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodJjimInsert(vo); //vo를 dao에 있는 foodJjimInsert값 추가
		// 화면이동 (서버)
		// sendRedirect() => 재호출 => .do(request가 초기화) 즉 보낼값이 있으면 사용 불가
		// forward() => 새로운 데이터를 전송 (request에 값을 담아서 전송)
		// return "redirect:"
		return "redirect:../food/food_detail.do?fno="+fno; //request를 초기화 후 fno를 전송
	}
	//찜 목록 출력
	@RequestMapping("jjim/jjim_cancel.do")
	public String jjim_cancel(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("no");
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodJjimcancel(Integer.parseInt(no));
		return "redirect:../mypage/mypage_jjim_list.do";
	}
	@RequestMapping("like/like_insert.do")
	public String like_insert(HttpServletRequest request, HttpServletResponse response) {
		String fno=request.getParameter("fno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		FoodLikeVO vo=new FoodLikeVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setId(id);
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		dao.foodlikeInsert(vo);
		return "redirect:../food/food_detail.do?fno="+fno;
	}
}
