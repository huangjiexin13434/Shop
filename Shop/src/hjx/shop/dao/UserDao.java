package hjx.shop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;
import hjx.shop.util.JDBCUtils;
import hjx.shop.vo.User;

public class UserDao {
	/*`uid` varchar(32) NOT NULL,
	  `username` varchar(20) DEFAULT NULL,
	  `password` varchar(20) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  `email` varchar(30) DEFAULT NULL,
	  `telephone` varchar(20) DEFAULT NULL,
	  `birthday` date DEFAULT NULL,
	  `sex` varchar(10) DEFAULT NULL,
	  `state` int(11) DEFAULT NULL,
	  `code` varchar(64) DEFAULT NULL,*/
	public int regist(User user) throws SQLException {		
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="insert into user(uid,username,password,name,email,telephone,sex,state,code)"+
					"values(?,?,?,?,?,?,?,?,?)";
		Object param[]= {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getSex(),user.getState(),user.getCode()};
		int rows= query.update(sql, param);
		return rows;
	}
	@Test
	public void test() throws SQLException {
		UserDao userDao=new UserDao();
		User user= userDao.login("ddd", "dddd");
		System.out.println(user);
	}
	public User login(String username, String password) throws SQLException {
		boolean flag=false;
		Connection conn= JDBCUtils.getConnection();
		String sql="select * from user where username=? and password=?";
		QueryRunner query=new QueryRunner();
		return query.query(conn, sql, new BeanHandler<>(User.class),username,password);
	}
}
