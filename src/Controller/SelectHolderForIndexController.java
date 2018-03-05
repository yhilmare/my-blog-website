package Controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.blog_holder;
import net.sf.json.JSONObject;


public class SelectHolderForIndexController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		blog_holder holder = (blog_holder) this.getServletContext().getAttribute("holder");
		if(holder == null){
			response.getWriter().write("{'code':'error'}");
			return;
		}
		String pass = holder.getHolder_pwd();
		holder.setHolder_pwd("");
		JSONObject obj = JSONObject.fromObject(holder);
		response.getWriter().write(obj.toString());
		holder.setHolder_pwd(pass);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
