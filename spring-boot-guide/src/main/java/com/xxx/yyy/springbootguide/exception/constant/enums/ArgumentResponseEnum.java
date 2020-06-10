package com.xxx.yyy.springbootguide.exception.constant.enums;

import com.xxx.yyy.springbootguide.exception.assertion.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>参数校验异常返回结果</p>
 *
 * @author: maoyan
 * @date: 2020/5/15 13:55:06
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements CommonExceptionAssert {
    /**
     * 绑定参数校验异常
     */
    VALID_ERROR(6000, "参数校验异常"),

    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

}