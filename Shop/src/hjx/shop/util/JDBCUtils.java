package hjx.shop.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static  ComboPooledDataSource dataSource=new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl=new ThreadLocal<>();
	public static ComboPooledDataSource getDataSource() throws SQLException {
		return dataSource;
	}
	public static void startTransAction() throws SQLException {
		Connection conn=getConnection();
		conn.setAutoCommit(false);
	}
	public static void rollback() throws SQLException {
		Connection conn=getConnection();
		conn.rollback();
	}
	public static void commit() throws SQLException {
		Connection conn=getConnection();
		conn.commit();
		tl.remove();
	}
	public static Connection getConnection() throws SQLException {
		Connection conn=tl.get();
		if(conn==null) {
			conn=dataSource.getConnection();
			tl.set(conn);
		}
		return conn;
	}
}
