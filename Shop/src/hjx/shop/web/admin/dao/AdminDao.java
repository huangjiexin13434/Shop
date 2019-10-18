package hjx.shop.web.admin.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import hjx.shop.util.JDBCUtils;
import hjx.shop.vo.Category;
import hjx.shop.vo.Product;

public class AdminDao {

	public List<Category> getAllCategory() throws SQLException {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from category";
		return query.query(sql, new BeanListHandler<>(Category.class));
	}

	public int updateCategory(String cname,String cid) throws SQLException {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="update category set cname=? where cid=?";
		int i=query.update(sql,cname,cid);
		return i;
	}

	public int addCategory(Category category) throws SQLException {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="insert into category values(?,?)";
		int i= query.update(sql,category.getCid(),category.getCname());
		return i ;
	}

	public void delCategory(String cid) throws SQLException {
		String sql="delete from category where cid=?";
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql,cid);
	}

	public List<Product> getProductList() throws SQLException {
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product order by pid asc";
		return  queryRunner.query(sql, new BeanListHandler<>(Product.class));
	}
	
}
