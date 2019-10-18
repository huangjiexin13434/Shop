package hjx.shop.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hjx.shop.service.UserService;
import hjx.shop.vo.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BasicServlet {
	
	public void logout(HttpServletRequest request,HttpServletResponse response) throws SQLException, Exception {
		HttpSession session= request.getSession();
		session.removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	
	public void login(HttpServletRequest request,HttpServletResponse response) throws SQLException, Exception {
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserService userService=new UserService();
		User user= userService.login(username,password);
		System.out.println(user);
		String urlString="";
		HttpSession session=request.getSession();
		if(user!=null) {
			session.setAttribute("user", user);
			urlString=request.getContextPath()+"/default.jsp";
			response.sendRedirect(urlString);
		}else {
			request.setAttribute("error", "密码或用户名错误，请重新输入!!!");
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
}
