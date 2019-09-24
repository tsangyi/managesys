package com.xu.bms.service.impl;

import com.xu.bms.dao.UserDao;
import com.xu.bms.dao.imp.UserDaoImpl;
import com.xu.bms.entity.User;
import com.xu.bms.service.LoginService;

/**
 * @author xu
 * @date 20/9/2019 下午4:53
 */
public class LoginServiceImpl implements LoginService {
    private UserDao userDao = new UserDaoImpl();


    @Override
    public boolean login(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        User user = userDao.selectByUsernameAndPassword(username, password);
        return user != null && user.getPassword().equals(password);
    }
}
