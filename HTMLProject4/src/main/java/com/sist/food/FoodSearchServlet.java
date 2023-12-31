// service는 doGet, doPost를 동시에 처리가 가능
package com.sist.food;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
/*
	@GetMapping => GET
	@PostMapping => POST
	@RequestMapping => GET/POST
	-------------------------------에러나면 400에러
 */

@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// 사용자 요청값을 받는다.
		request.setCharacterEncoding("UTF-8");
		String addr=request.getParameter("addr"); //addr ==> name값
		if(addr==null)
			addr="마포"; //기본값으로 하나는 뿌리고 시작
		
		String strPage=request.getParameter("page"); //이렇게 작성을 안하면 500에러 발생
		if(strPage==null)
			strPage="1"; //그래서 디폴트로 1을 잡고 시작
		
		int curPage=Integer.parseInt(strPage);
		
		FoodDAO1 dao = FoodDAO1.newInsatance();
		List<FoodVO> list=dao.FoodFindData(addr, curPage);
		int totalPage = (int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count=dao.foodRowCount(addr);
		final int BLOCK=5; //한 블록에 5개씩 출력
		// curpage=1 => startPage=1
		// 2 ==> (2-1)/5 => 1/5*5 => 0 (5-1)/5*5 ==> 0
		int startPage=((curPage-1)/BLOCK*BLOCK)+1;
		// (6-1)5*5 => 5+1 => 6
		int endPage=((curPage-1)/BLOCK*BLOCK)+BLOCK;
		// < [1][2][3][4][5] >
		// curpage => 1~5일때 => startPage=1, endPage=5
		// < [6][7][8][9][10] > <- 이걸 누르면 11로 넘어감
		// curpage => 6~10 => startPage=6 , endPage=10 => 1,6,11
		//화면
		/*
			<ul class="pagination">
			  <li><a href="#">1</a></li>
			  <li><a href="#">2</a></li>
			  <li><a href="#">3</a></li>
			  <li><a href="#">4</a></li>
			  <li><a href="#">5</a></li>
			</ul>
		 */
		if(endPage > totalPage)
			endPage=totalPage; // 마지막 페이지가 23페이지면 total이 5단위로 끊어지지만 23페이지까지만 출력해야하기에 23페이지로 만들어줘야함
		PrintWriter out = response.getWriter();
		//페이징 기법
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}"); //브라우저와의 거리를 벌림
		out.println(".row{");
		out.println("margin:0px auto;"); // 가운데 정렬
		out.println("width:1024px} </style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<form method=post action=FoodSearchServlet>");
		out.println("<input type=text name=addr size=25 class=input-sm>");
		out.println("<input type=submit value=검색 class=\"btn btn-sm btn-danger\">"); //공백이 들어가면 ""을 넣어줘야함
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<div style=\"height:30px\"></div>");
		
		for(FoodVO vo:list) {
			out.println("<div class=\"col-md-3\">"); // 한줄에 4개 출력
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#\">");
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getName()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>"); //row
		out.println("<div style=\"height:10px\"></div>");
		out.println("<div class=row>");
		out.println("<div class=text-center>");
		out.println("<ul class=pagination>");
		out.println("<li><a href=#>&lt;</a></li>");
		for(int i = startPage; i <=endPage;i++) {
			out.println("<li "+(curPage==i?"class=active":"")+"><a href=FoodSearchServlet?page="+i+">"+i+"</a></li>");
		}
		out.println("<li><a href=#>&gt;</a></li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>"); //container
		out.println("</body>");
		out.println("</html>");
	}

}

