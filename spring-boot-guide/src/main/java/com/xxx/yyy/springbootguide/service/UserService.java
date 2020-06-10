package com.xxx.yyy.springbootguide.service;

import com.xxx.yyy.springbootguide.model.User;
import com.xxx.yyy.springbootguide.model.UserSlave;

/**
 * @author: maoyan
 * @date: 2020/2/15 15:29:48
 * @description:
 */
public interface UserService {
    Boolean save(User user);

    User query(String username,String password);

    UserSlave query(int id);

    Boolean update(User user);

    void delete(int id);
}
