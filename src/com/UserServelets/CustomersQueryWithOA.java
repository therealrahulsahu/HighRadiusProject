package com.UserServelets;

import com.database.MySQLTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CustomersQueryWithOA", urlPatterns = {"/customer_search_with_OA"})
public class CustomersQueryWithOA extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setHeader("Access-Control-Allow-Origin", "*");
		try{
			String result="";
			MySQLTool DB = new MySQLTool("jdbc:mysql://localhost:3306/project", "root", "root");
			String param;
			param = request.getParameter("by_cs_name");
			if(param == null){
				throw new Exception();
			}

			boolean byCsName = Boolean.parseBoolean(param);

			if(byCsName){
				String name = request.getParameter("cs_name");
				if(name == null){
					throw new Exception();
				}
				result = DB.getCustomerListByTotalOpenAmount(byCsName, name, "", 0);
			}else {
				param = request.getParameter("use_compare");
				if(param == null){
					throw new Exception();
				}
				String use_compare = param;

				param = request.getParameter("amount");
				if(param == null){
					throw new Exception();
				}
				int amount = Integer.parseInt(param);

				result = DB.getCustomerListByTotalOpenAmount(byCsName, "", use_compare, amount);
			}

			PrintWriter out = response.getWriter();
			out.print(result);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		}catch (Exception e){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
