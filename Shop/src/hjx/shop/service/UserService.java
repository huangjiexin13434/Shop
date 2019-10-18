package hjx.shop.service;

import java.sql.SQLException;

import hjx.shop.dao.UserDao;
import hjx.shop.vo.User;

public class UserService {
	UserDao userDao=new UserDao();
	
	public boolean regist(User user) throws SQLException {
		
		return  userDao.regist(user)>0?true:false;
	}

	public User login(String username, String password) throws SQLException {
		return userDao.login(username,password);
		
	}

}
