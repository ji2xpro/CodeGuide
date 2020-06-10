package com.xxx.yyy.springbootguide;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxx.yyy.springbootguide.mapper.UserMapper;
import com.xxx.yyy.springbootguide.model.User;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @author: maoyan
 * @date: 2020/2/10 16:22:36
 * @description:
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class sqlTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        User user  = new User();
        user.setUsername("猫眼测试4");
        user.setPassword("MaoYan4");
        user.setAge(23);
        final int row1 = userMapper.insertUser(user);
        log.info("[添加结果] - [{}]", row1);

        final User u1 = userMapper.findByUsername("猫眼测试4");
        log.info("[根据用户名查询] - [{}]", u1);
    }


    @Test
    public void test1() {
        final User user  = new User();
        user.setUsername("猫眼测试");
        user.setPassword("MaoYan");
        user.setAge(11);
        userMapper.insertSelective(user);
        log.info("[user回写主键] - [{}]", user.getId());

        final int count = userMapper.countByUsername("猫眼测试");
        log.info("[调用自己写的SQL] - [{}]", count);

        // TODO 模拟分页
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        userMapper.insertSelective(user);
        // TODO 分页 + 排序 this.userMapper.selectAll() 这一句就是我们需要写的查询，有了这两款插件无缝切换各种数据库
        final PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).setOrderBy("id desc").doSelectPageInfo(() -> this.userMapper.selectAll());
        log.info("[lambda写法] - [分页信息] - [{}]", pageInfo.toString());

        PageHelper.startPage(1, 10).setOrderBy("id desc");
        final PageInfo<User> userPageInfo = new PageInfo<>(this.userMapper.selectAll());
        log.info("[普通写法] - [{}]", userPageInfo);
    }
}
