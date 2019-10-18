package hjx.shop.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import hjx.shop.dao.ProductDao;
import hjx.shop.vo.Category;
import hjx.shop.vo.PageBean;
import hjx.shop.vo.Product;


public class ProductService {
	ProductDao productDao=null;
	public ProductService() {
		productDao=new ProductDao();
	}
	public List<Product> findHostProductList() throws Exception {
		return  productDao.findHostProductList();
	}

	public List<Product> findnewProductList() throws Exception {
		return productDao.findnewProductList();
	}
	public List<Category> getAllCategorys() throws Exception {
		return productDao.getAllCategorys();
	}
	
	public PageBean<Product> findProductListbyCid(String cid, int currentPage, int currentCount) throws Exception {
		PageBean<Product> pageBean=new PageBean<>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		int totalCount= productDao.getProductTotalCount(cid);
		pageBean.setTotalCount(totalCount);
		//
		int totalPage=0;
		if(totalCount%currentCount==0) {
			totalPage=totalCount/currentCount;
		}else {
			totalPage=(totalCount/currentCount)+1;
		}
		pageBean.setTotalPage(totalPage);
		int index=(currentPage-1)*currentCount;
		System.out.println(index);
		List<Product> productList=productDao.getProductList(cid,index,currentCount);
		pageBean.setList(productList);
		//System.out.println(pageBean);
		return pageBean;
		
	}
	@Test
	public void test() throws Exception {
		ProductService productService=new ProductService();
		PageBean<Product> pageBean= productService.findProductListbyCid("1", 1, 12);
		for(Product l:pageBean.getList()) {
			System.out.println(l);
		}
	}
	public Product findproductInfoByPid(String pid) throws Exception {
		return productDao.findproductInfoByPid(pid);
		
	}
}
