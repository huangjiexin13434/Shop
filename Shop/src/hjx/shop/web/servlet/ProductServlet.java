package hjx.shop.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import hjx.shop.service.ProductService;
import hjx.shop.vo.Cart;
import hjx.shop.vo.CartItem;
import hjx.shop.vo.Category;
import hjx.shop.vo.PageBean;
import hjx.shop.vo.Product;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BasicServlet {

	/*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		if ("index".equals(methodName)) {
			index(request, response);
		} else if ("productInfoByPid".equals(methodName)) {
			productInfoByPid(request, response);
		} else if ("productListbyCid".equals(methodName)) {
			productListbyCid(request, response);
		} else if ("categoryList".equals(methodName)) {
			categoryList(request, response);
		}

	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *//*
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
*/
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductService productService = new ProductService();
		try {

			List<Product> hostProductList = productService.findHostProductList();

			List<Product> newProductList = productService.findnewProductList();
			// System.out.println(hostProductList);
			request.setAttribute("hostProductList", hostProductList);
			request.setAttribute("newProductList", newProductList);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void productInfoByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("proinfo");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");
		// System.out.println(cid+" "+currentPage);
		String pid = request.getParameter("pid");
		ProductService productService = new ProductService();

		// Cooket
		String pids = "";
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					String str[] = pids.split("-");
					List<String> list = Arrays.asList(str);
					LinkedList<String> link = new LinkedList<>(list);
					if (link.contains(pid)) {
						link.remove(pid);
						link.addFirst(pid);
					} else {
						link.addFirst(pid);
					}
					StringBuffer buffer = new StringBuffer();
					for (int i = 0; i < link.size() && i < 6; i++) {
						String k = link.get(i);
						buffer.append(k);
						buffer.append("-");
					}
					pids = buffer.toString();
				}
				//System.out.println("pids     " + pids);
				Cookie cookie_pid = new Cookie("pids", pids);
				response.addCookie(cookie_pid);
			}
		} else {
			Cookie cookie_pid = new Cookie("pids", pid);
			response.addCookie(cookie_pid);
		}
		try {
			Product product = productService.findproductInfoByPid(pid);
			request.setAttribute("product", product);
			request.setAttribute("cid", cid);
			request.setAttribute("currentPage", currentPage);

			request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void productListbyCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("productList    2222");
		String cid = request.getParameter("cid");
		// String cid="1"; //测试的时候用
		String currentPageString = request.getParameter("currentPage");
		int currentPage = 1;
		if (currentPageString == null || "".equals(currentPageString)) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(currentPageString);
		}
		ProductService productService = new ProductService();

		int currentCount = 12;
		try {
			PageBean<Product> pageBean = productService.findProductListbyCid(cid, currentPage, currentCount);
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("cid", cid);
			// System.out.println("productList "+cid);
			// 历史菜单
			Cookie cookies1[] = request.getCookies();
			List<Product> historyProduct = new ArrayList<>();
			if (cookies1 != null) {
				for (Cookie cookie : cookies1) {
					if ("pids".equals(cookie.getName())) {
						String value = cookie.getValue();
						String split[] = value.split("-");
						for (int i = 0; i < split.length; i++) {
							Product pro = productService.findproductInfoByPid(split[i]);
							historyProduct.add(pro);
						}
						//System.out.println(historyProduct);
					}
				}
			}
			request.setAttribute("historyProduct", historyProduct);

			request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("bbbbb");
		ProductService productService = new ProductService();
		List<Category> categoryList;
		try {

			categoryList = productService.getAllCategorys();
			Gson gson = new Gson();
			String gsString = gson.toJson(categoryList);
			// System.out.println(gsString);
			response.setContentType("html/text;charset=utf-8");
			response.getWriter().write(gsString);

			// request.setAttribute("categoryList", categoryList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void addProductToCar(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int buyNum= Integer.parseInt(request.getParameter("buyNum"));
		System.out.println(buyNum);
		String pid=request.getParameter("pid");
		CartItem cartItem=new CartItem();
		ProductService productService=new ProductService();
		HttpSession session= request.getSession();
		cartItem.setBuyNum(buyNum);
		Product product=productService.findproductInfoByPid(pid);
		cartItem.setProduct(product);
		
		double subTotal=buyNum*product.getShop_price();
		cartItem.setSubTotal(subTotal);
		
		
		Cart  cart=(Cart) session.getAttribute("cart");
		if(cart==null) {
			cart=new Cart();
		}
		//{23=CartItem [product=Product [pid=23, pname=sonim XP7700 4G手机, market_price=1799.0, shop_price=1699.0, pimage=products/1/c_0023.jpg, pdate=2015-11-09, is_hot=1, pdesc=三防智能手机 移动/联通双4G 安全 黑黄色 双4G美国军工IP69 30天长待机 3米防水防摔 北斗, pflag=0, category=null], buyNum=1, subTotal=1699.0]}
		Map<String,CartItem> cartItems= cart.getCartItems();
		double newSubTotal=0;
		if(cartItems.containsKey(pid)) {
			 CartItem cartItem2= cartItems.get(pid);
			 int oldbuyNum= cartItem2.getBuyNum();
			 int newbuyNum=0;
			 newbuyNum+=oldbuyNum+buyNum;
			 //System.out.println("oldbuyNum  "+oldbuyNum+" newbuyNum  "+newbuyNum);
			 cartItem2.setBuyNum(newbuyNum);
			 
			 double oldSubTotal= cartItem2.getSubTotal();
			 
			 newSubTotal=oldSubTotal+subTotal;
			 cartItem2.setSubTotal(newSubTotal);
		}else {
			newSubTotal=subTotal;
			cartItems.put(pid, cartItem);
		}
		//System.out.println(cartItems);
		double total=cart.getTotal();
		total=total+newSubTotal;
		cart.setTotal(total);	
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	public void delProductCar(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid=request.getParameter("pid");
		HttpSession session=request.getSession();
		Cart cart= (Cart) session.getAttribute("cart");
		Map<String,CartItem> cartItems= cart.getCartItems();
		
		if(cartItems.containsKey(pid)) {
			CartItem cartItem= cartItems.get(pid);
			double total=cart.getTotal();
			double subTotal= cartItem.getSubTotal();
			total=total-subTotal;
			cart.setTotal(total);
			cartItems.remove(pid);
		}
		
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void clearCart(HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession session=request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	public void updateProductbuyNum(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//System.out.println("come in");
		String pid= request.getParameter("pid");
		int buyNum=Integer.parseInt(request.getParameter("buyNum"));
		HttpSession session=request.getSession();
		Cart cart= (Cart) session.getAttribute("cart");
		
			Map<String,CartItem> cartItems=cart.getCartItems(); 
			PrintWriter write= response.getWriter();
			if(cartItems.containsKey(pid)) {
				CartItem cartItem= cartItems.get(pid);
				int oldbuyNum=cartItem.getBuyNum();
				//System.out.println(cartItem);
				cartItem.setBuyNum(buyNum);
				double subTotal=cartItem.getProduct().getShop_price()*buyNum;
				cartItem.setSubTotal(subTotal);
				
				//
				double oldTotal=cart.getTotal();
				int newbuyNum=0;
				double newTotal=0;
				if(oldbuyNum<buyNum) {   //判断是否大于该原来的商品数量
					newbuyNum=buyNum-oldbuyNum;    
					double mid=cartItem.getProduct().getShop_price()*newbuyNum;
					newTotal=oldTotal+mid;
				}else {
					newbuyNum= oldbuyNum-buyNum;
					double mid=cartItem.getProduct().getShop_price()*newbuyNum;
					newTotal=oldTotal-mid;
				}
				//System.out.println("subTotal: "+subTotal+" oldTotal: "+oldTotal+" newTotal:"+newTotal+" mid:"+mid);
				
				cart.setTotal(newTotal);
				String jsString="{\"subTotal\":"+subTotal +",\"total\":\""+newTotal+"\"}";
				write.write(jsString);
			}
		
	}
}
