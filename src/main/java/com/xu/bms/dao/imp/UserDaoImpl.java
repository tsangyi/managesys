package com.xu.bms.dao.imp;

import com.xu.bms.dao.UserDao;
import com.xu.bms.entity.User;
import com.xu.bms.util.JDBCUtils;

import java.sql.*;
import java.util.List;

/**
 * @author xu
 * @date 20/9/2019 下午5:00
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User selectByUsernameAndPassword(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        String sql = "select * from user where username = ? and password = ?";
        List<User> users = JDBCUtils.executeQuery(User.class, sql, username, password);
        System.out.println("users:"+users);
        if (users == null || users.size() == 0){
            return null;
        }
        return users.get(0);
    }
}
