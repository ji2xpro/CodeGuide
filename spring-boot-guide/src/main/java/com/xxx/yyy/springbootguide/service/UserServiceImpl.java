package com.xxx.yyy.springbootguide.service;

import com.xxx.yyy.springbootguide.config.datasource.DataSource;
import com.xxx.yyy.springbootguide.enums.DataSources;
import com.xxx.yyy.springbootguide.mapper.UserMapper;
import com.xxx.yyy.springbootguide.mapper.UserSlaveMapper;
import com.xxx.yyy.springbootguide.model.User;
import com.xxx.yyy.springbootguide.model.UserSlave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2020/2/15 15:33:47
 * @description:
 */
@Slf4j
@CacheConfig(cacheNames = "baseentity")
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSlaveMapper userSlaveMapper;

    @Override
    @Cacheable(value = "cache", key = "#username", unless="#result == null")
    public User query(String username, String password) {
        log.info("进入get方法查找username："+username);
        return userMapper.find(username,password);
    }

    @Override
    @Cacheable(value = "cache", key = "#id", unless="#result == null")
    @DataSource(DataSources.DB2)
    public UserSlave query(int id) {
        log.info("进入get方法查找id："+id);
        return userSlaveMapper.findById(id);
    }

    @Override
    @CachePut(value = "cache",key = "#user.username", unless="#result == null")
    public Boolean save(User user) {
        int i = userMapper.insertUser(user);
        if (i == 1){
            return true;
        }
        return false;
    }

    @Override
    //    @CachePut(value = "cache",key = "#redis.key", unless="#result == null")
    public Boolean update(User user) {
        return null;
    }

    @Override
    @CacheEvict(value = "user",key = "id",allEntries = true,beforeInvocation = true)
    public void delete(int id) {

    }
}
