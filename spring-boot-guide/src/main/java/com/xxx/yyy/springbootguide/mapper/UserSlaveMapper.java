package com.xxx.yyy.springbootguide.mapper;

import com.xxx.yyy.springbootguide.model.UserSlave;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserSlaveMapper extends Mapper<UserSlave> {

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    UserSlave findById(@Param("id") int id);


    UserSlave findById1(int id);
}