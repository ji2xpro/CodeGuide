package com.xxx.yyy.springbootguide.controller;

import com.xxx.yyy.springbootguide.enums.DataType;
import com.xxx.yyy.springbootguide.enums.ParamType;
import com.xxx.yyy.springbootguide.model.User;
import com.xxx.yyy.springbootguide.model.UserSlave;
import com.xxx.yyy.springbootguide.service.UserService;
import com.xxx.yyy.springbootguide.utils.RedisUtil;
import com.xxx.yyy.unifiedexceptionhandling.entity.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * swagger
 *
 * @author Levin
 * @since 2018/5/16 0016
 */
@Log4j2
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理", description = "用户管理", value = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping
    @ApiOperation(value = "根据用户名和密码查询用户（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = DataType.STRING, paramType = ParamType.QUERY, required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = DataType.STRING, paramType = ParamType.QUERY, required = true),
    })
    public User get(String username, String password) {
        log.info("多个参数用  @ApiImplicitParams");
        return userService.query(username,password);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户id查询用户（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, paramType = ParamType.PATH, required = true),
    })
    public UserSlave get(@PathVariable int id) {
        log.info("单个参数用  @ApiImplicitParam");
        return userService.query(id);
    }

    @GetMapping("get/{id}")
    @ApiOperation(value = "根据用户id查询用户（DONE）1111")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, paramType = ParamType.PATH, required = true),
    })
    public R<UserSlave> get11(@PathVariable int id){
        return new R<>(userService.query11(id));
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "新增用户（DONE）", notes = "新增用户：每次新增数据，要清空缓存组，保证缓存与数据库同步")
    public boolean post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.LONG, paramType = ParamType.PATH)
    public void delete(@PathVariable Long id) {
        log.info("单个参数用 ApiImplicitParam");
        redisUtil.del(String.valueOf(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public boolean put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
        return userService.update(user);
    }
}
