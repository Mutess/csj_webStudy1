package com.sist.common;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
// => Spring (공통모듈 => AOP)
// Spring => DI / AOP (사용법 : XML, Annotation)
public class commonModel {
	public static void commonRequestData(HttpServletRequest request) {
		// footer
		FoodDAO21 dao=FoodDAO21.newInstance();
		// => 공지사항 => 최신 뉴스
		// => 방문 맛집**
		List<FoodVO> fList=dao.foodTop7();
		request.setAttribute("fList", fList);
	}
}
