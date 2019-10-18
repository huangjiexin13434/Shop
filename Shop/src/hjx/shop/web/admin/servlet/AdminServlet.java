package hjx.shop.web.admin.servlet;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hjx.shop.service.ProductService;
import hjx.shop.util.UURandom;
import hjx.shop.vo.Category;
import hjx.shop.vo.Product;
import hjx.shop.web.admin.dao.AdminDao;
import hjx.shop.web.admin.service.AdminService;
import hjx.shop.web.servlet.BasicServlet;

public class AdminServlet extends BasicServlet {
	public void productEdit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String pid=request.getParameter("pid");
		ProductService productService=new ProductService();
		AdminService adminService=new AdminService();
		Product product= productService.findproductInfoByPid(pid);
		List<Category> categorieList= adminService.getAllCategory();
		System.out.println(categorieList);
		System.out.println(product);
		request.setAttribute("product", product);
		request.setAttribute("categorieList",categorieList);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}
	public void productList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AdminService adminService=new AdminService();
		List<Product> productList= adminService.getProductList();
		request.setAttribute("productList", productList);
		
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	public void delCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String cid=request.getParameter("cid");
		AdminService adminService=new AdminService();
		adminService.delCategory(cid);
		categoryList(request, response);
	}
	public void addCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String cname=request.getParameter("cname");
		
		AdminService adminService=new AdminService();
		Category category=new Category();
		category.setCid(UURandom.getUUID());
		category.setCname(cname);
		boolean flag= adminService.addCategory(category);
		System.out.println(flag);
		categoryList(request, response);
	}
	public void updateCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String cid=request.getParameter("cid");
		String cname= request.getParameter("cname");
		AdminService categoryService=new AdminService();
		boolean flag= categoryService.updateCategory(cname,cid);
		//System.out.println(flag);
		categoryList(request,response);
	}
	public void categoryList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AdminService categoryService=new AdminService();
		List<Category> categoryList= categoryService.getAllCategory();
		/*for(Category category:categoryList) {
			System.out.println(category);
		}*/
		request.setAttribute("categoryList",categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}
	
}
