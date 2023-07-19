package com.sist.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class CartModel {
	@RequestMapping("cart/cart_insert.do")
	public String cart_insert(HttpServletRequest request, HttpServletResponse response) {
		String goods_no=request.getParameter("goods_no");
		String price=request.getParameter("price");
		String type=request.getParameter("type");
		String amount=request.getParameter("amount");
		
//		HttpSession tsession=request.getSession();
//		tsession.setAttribute(type, "");
		
		CartVO vo=new CartVO();
		
		vo.setGoods_no(Integer.parseInt(goods_no));
		vo.setPrice(Integer.parseInt(price));
		vo.setType(Integer.parseInt(type));
		vo.setAmount(Integer.parseInt(amount));
		
//		System.out.println(goods_no);
//		System.out.println(price);
//		System.out.println(amount);
//		System.out.println(type);
		
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		vo.setId(id);
		
		CartDAO dao=CartDAO.newInstance();
		dao.cartInsert(vo);
		return "redirect:../goods/goods_detail.do?no="+goods_no+"&type="+type;
	}
	@RequestMapping("cart/cart_buy.do")
	public void cart_buy(HttpServletRequest request, HttpServletResponse response) {
		String no=request.getParameter("cart_no");
		CartDAO dao=CartDAO.newInstance();
		dao.cartBuy(Integer.parseInt(no));
		
	}
}
