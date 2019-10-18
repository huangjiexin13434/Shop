package hjx.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.sun.mail.iap.Response;

import hjx.shop.service.OrderService;
import hjx.shop.util.JDBCUtils;
import hjx.shop.util.UURandom;
import hjx.shop.vo.Cart;
import hjx.shop.vo.CartItem;
import hjx.shop.vo.Order;
import hjx.shop.vo.OrderItem;
import hjx.shop.vo.Product;
import hjx.shop.vo.User;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BasicServlet {
	public  void myOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		User user= (User) session.getAttribute("user");
		if(user==null) {
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
		}
		try {
			OrderService orderService=new OrderService();
			List<Order> orderList= orderService.findAllOrders(user.getUid());
			for(Order order:orderList) {
				//key:{itemid=53d01562c9e24436b1c135be71e7f6b5, pimage=products/1/c_0003.jpg, subtotal=1499.0, pname=华为荣耀6, count=1, pid=3}
				String oid=order.getOid();
				List<Map<String, Object>> mapList= orderService.findAllOrderItem(oid);
				for(Map<String,Object> map:mapList) {
					OrderItem orderitem=new OrderItem();
					orderitem.setItemid(map.get("itemid").toString());
					orderitem.setCount(Integer.parseInt(map.get("count").toString()));
					orderitem.setSubtotal(Double.parseDouble(map.get("subtotal").toString()));
					//更简单的方法：不用一个一个封装
					Product product=new Product();
					BeanUtils.populate(product, map);
					orderitem.setProduct(product);
					order.getOrderItems().add(orderitem);
				}
			}
			/*for(Order order:orderList) {
				System.out.println(order);
			}*/
			
			req.setAttribute("orderList", orderList);
			req.getRequestDispatcher("/order_list.jsp").forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public  void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null) {
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
			return;
		}
		
		//该订单中有多少订单项
		//List<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		Order order=new Order();
		order.setOid(UURandom.getUUID());
		order.setOrdertime(new Date());
		Cart cart=(Cart) session.getAttribute("cart");
		order.setTotal(cart.getTotal());
		order.setState(0);
		order.setAddress(null);
		order.setTelephone(null);
		order.setUser(user);
		List<OrderItem> orderItems=new ArrayList<>();
		for(Map.Entry<String, CartItem> entry:cart.getCartItems().entrySet()) {
			CartItem cartItem=entry.getValue();
			OrderItem orderItem=new OrderItem();
			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setItemid(UURandom.getUUID());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItem.setOrder(order);
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		OrderService orderService=new OrderService();
		orderService.submitOrder(order);
		session.setAttribute("order", order);
		resp.sendRedirect(req.getContextPath()+"/order_info.jsp");
	}
	public  void confirmOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> properties=  req.getParameterMap();
		Order order=new Order();
		try {
			BeanUtils.populate(order, properties);
			System.out.println(order);
			OrderService orderService=new OrderService();
			orderService.updateOrder(order);
			
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		resp.sendRedirect(req.getContextPath()+"/header.jsp");
	}
	
}
