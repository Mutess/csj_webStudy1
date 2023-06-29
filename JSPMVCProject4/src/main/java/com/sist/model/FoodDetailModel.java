package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.FoodDAO20;
import com.sist.dao.FoodVO;

public class FoodDetailModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		FoodDAO20 dao=FoodDAO20.newInstance();
		String fno=request.getParameter("fno");
		FoodVO vo=dao.fooddetailData(Integer.parseInt(fno));
		String address=vo.getAddress();
		String addr1=address.substring(0, address.lastIndexOf("지"));
		String addr2=address.substring(address.lastIndexOf("지")+3);
		String temp=addr1.trim().substring(addr1.indexOf(" ")+1);
		String addr3=temp.substring(0,temp.indexOf(" "));
		request.setAttribute("vo", vo);
		request.setAttribute("addr1", addr1);
		request.setAttribute("addr2", addr2);
		request.setAttribute("addr3", addr3);
		return "food/food_detail.jsp";
	}

}
