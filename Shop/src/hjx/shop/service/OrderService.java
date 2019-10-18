package hjx.shop.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import hjx.shop.dao.OrderDao;
import hjx.shop.util.JDBCUtils;
import hjx.shop.vo.Order;
import hjx.shop.vo.OrderItem;

public class OrderService {
	public void submitOrder(Order order) {
		OrderDao orderDao=new OrderDao();
		try {
			JDBCUtils.startTransAction();
			orderDao.addToOrders(order);
			List<OrderItem> orderItems=order.getOrderItems();
			orderDao.addToOrderItem(orderItems);
		} catch (SQLException e) {
			try {
				JDBCUtils.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		try {
			JDBCUtils.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateOrder(Order order) throws SQLException {
		OrderDao.updateOrder(order);
		
	}

	

	public List<Order> findAllOrders(String uid) throws SQLException {
		OrderDao orderDao=new OrderDao();
		List<Order> orderList=orderDao.findAllOrders(uid);
		return orderList;
	}

	public List<Map<String, Object>> findAllOrderItem(String oid) throws SQLException {
		OrderDao orderDao=new OrderDao();
		List<Map<String, Object>> mapList= orderDao.findAllOrderItem(oid);
		return mapList;
	}
}
