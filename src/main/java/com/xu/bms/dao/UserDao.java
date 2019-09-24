package com.xu.bms.dao;

import com.xu.bms.entity.User;

/**
 * @author xu
 * @date 20/9/2019 下午4:58
 */
public interface UserDao {
    User selectByUsernameAndPassword(String username, String password);
}
