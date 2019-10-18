package hjx.shop.web.admin.service;

import java.sql.SQLException;
import java.util.List;

import hjx.shop.vo.Category;
import hjx.shop.vo.Product;
import hjx.shop.web.admin.dao.AdminDao;

public class AdminService {
	AdminDao adminDao=null;
	public AdminService() {
		adminDao=new AdminDao();
	}

	public List<Category> getAllCategory() throws SQLException {
		
		 return adminDao.getAllCategory();
	}

	public boolean updateCategory(String cname,String cid) throws SQLException {
		return adminDao.updateCategory(cname,cid)>0?true:false;
		
	}

	public boolean addCategory(Category category) throws SQLException {
		// TODO Auto-generated method stub
		return adminDao.addCategory(category)>0?true:false;
	}

	public void delCategory(String cid) throws SQLException {
		adminDao.delCategory(cid);
		
	}

	public List<Product> getProductList() throws SQLException {
		List<Product> productList=adminDao.getProductList();
		return productList;
	}

	

}
