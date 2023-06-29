package com.sist.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.model.*;

@WebServlet("/controller")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd=request.getParameter("cmd");
		String jsp="";
		if(cmd.equals("list")) {
			ListModel model=new ListModel();
			jsp=model.execute(request);
		}
		else if(cmd.equals("insert")) {
			InsertModel model=new InsertModel();
			jsp=model.execute(request);
		}
		else if(cmd.equals("update")) {
			UpdateModel model=new UpdateModel();
			jsp=model.execute(request);
		}
		else if(cmd.equals("delete")) {
			DeleteModel model=new DeleteModel();
			jsp=model.execute(request);
		}
		RequestDispatcher rd=request.getRequestDispatcher("view/"+jsp);
		rd.forward(request, response);
	}

}
