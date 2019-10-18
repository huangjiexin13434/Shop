package hjx.shop.util;

import java.util.UUID;

public class UURandom {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
}
