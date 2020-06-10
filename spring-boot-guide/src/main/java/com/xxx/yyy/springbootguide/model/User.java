package com.xxx.yyy.springbootguide.model;

import java.io.Serializable;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "sys_user")
@ApiModel
public class User extends BaseModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "userName")
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    @ApiModelProperty("密码")
    private String password;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", age=").append(age);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}