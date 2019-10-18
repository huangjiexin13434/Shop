package hjx.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import hjx.shop.service.UserService;
import hjx.shop.util.UURandom;
import hjx.shop.vo.User;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Map<String, String[]> paramets= request.getParameterMap();
		User user=new User();
		try {
			BeanUtils.populate(user, paramets);
			System.out.println(user);
			user.setUid(UURandom.getUUID());
			user.setTelephone(null);
			user.setState(0);
			user.setCode(UURandom.getUUID());
			//user.setBirthday();
			//System.out.println(user);
			UserService userService=new UserService();
			try {
				boolean isSuccess= userService.regist(user);
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				if(isSuccess) {
					System.out.println("true");
				}else {
					System.out.println("false");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
