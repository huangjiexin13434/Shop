package hjx.shop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import hjx.shop.util.JDBCUtils;
import hjx.shop.vo.Category;
import hjx.shop.vo.Product;

public class ProductDao {

	public List<Product> findHostProductList() throws Exception {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product where is_hot=? order by pdate desc  limit ?,?;";
		return  query.query(sql,new BeanListHandler<>(Product.class),1, 0,9);
	}

	public List<Product> findnewProductList() throws SQLException {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?";
		return  query.query(sql,new BeanListHandler<>(Product.class), 0,9);
	}
	

	public List<Category> getAllCategorys() throws Exception {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from category";
		return query.query(sql, new BeanListHandler<>(Category.class));
	}
	

	public int getProductTotalCount(String cid) throws Exception {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select count(*) from product where cid=?";
		Long totalCount= (long) query.query(sql, new ScalarHandler(),cid);
		return totalCount.intValue(); 
	}

	public List<Product> getProductList(String cid,int index, int currentCount) throws Exception {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		//System.out.println("index "+index);
		String sql="select * from product where cid=? order by pdate desc limit ?,?";
		return query.query(sql, new BeanListHandler<>(Product.class),cid,index,currentCount);
	}
	@Test
	public void test() throws Exception {
		ProductDao productDao=new ProductDao();
		String cid="1";
		List<Product> products= productDao.getProductList(cid,0,12);
		for(Product product:products) {
			System.out.println(product);
		}
	}

	public Product findproductInfoByPid(String pid) throws Exception {
		QueryRunner query=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product where pid=?";
		return query.query(sql, new BeanHandler<>(Product.class),pid);
		
		
	}
}
