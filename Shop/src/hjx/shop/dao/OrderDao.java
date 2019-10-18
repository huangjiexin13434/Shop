package hjx.shop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Connector;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import hjx.shop.util.JDBCUtils;
import hjx.shop.vo.Order;
import hjx.shop.vo.OrderItem;

public class OrderDao {
	public void addToOrders(Order order) throws SQLException {
		QueryRunner query=new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn= JDBCUtils.getConnection();
		Date date= order.getOrdertime();
		Timestamp timestamp=new Timestamp(date.getTime());
		Object obj[] ={order.getOid(),timestamp,order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		query.update(conn, sql,obj);
		
	}

	public void addToOrderItem(List<OrderItem> orderItems) throws SQLException {
		QueryRunner query=new QueryRunner();
		String sql="insert into orderitem values(?,?,?,?,?)";
		Connection conn=JDBCUtils.getConnection();
		for(OrderItem orderItem:orderItems) {
			Object obj[]= {orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),
					orderItem.getProduct().getPid(),orderItem.getOrder().getOid()};
			query.update(conn, sql,obj);
		}
		
	}

	public static void updateOrder(Order order) throws SQLException {
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="update orders set address=? ,name=?,telephone=? where oid=?";
		queryRunner.update(sql,order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		
	}

	public List<Order> findAllOrders(String uid) throws SQLException {
		QueryRunner query=new QueryRunner();
		Connection conn=JDBCUtils.getConnection();
		String sql="select * from orders o,user u where o.uid=u.uid and o.uid=?";
		List<Order> orderList= query.query(conn, sql,new BeanListHandler<>(Order.class),uid);
		return orderList;
	}
	@Test
	public void test() throws SQLException {
		OrderDao orderDao=new OrderDao();
		List<Map<String, Object>> mapList= orderDao.findAllOrderItem("da4786dde0b54dd9a2ded11ec0740a31");
		for(Map<String,Object> map:mapList) {
			System.out.println("key:"+map);
		}
	}

	public List<Map<String, Object>> findAllOrderItem(String oid) throws SQLException {
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select i.itemid,i.count,i.subtotal,p.pid,p.pname,p.pimage,p.shop_price from product p,orderitem i where i.pid=p.pid and oid=?";
		List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(),oid);
		return mapList;
	}

}
