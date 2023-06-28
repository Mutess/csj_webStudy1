package com.sist.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sist.dao.*;
public class FoodModel {
	public void foodListData(HttpServletRequest request) {
		FoodDAO16 dao=FoodDAO16.newInstance();
		String strPage=request.getParameter("page");
		if(strPage==null)
			strPage="1";
		int curPage=Integer.parseInt(strPage);
		List<FoodBean> list = dao.foodListData(curPage);
		int totalpage=dao.foodTotalPage();
		
		// request에 담아준다 => JSP로 보내준다
		request.setAttribute("curPage", curPage);
		request.setAttribute("totalPage", totalpage);
		request.setAttribute("list", list);
	}
}
