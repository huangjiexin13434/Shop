package hjx.shop.vo;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<String,CartItem> cartItems =new HashMap<>();
	private double total=0;
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
