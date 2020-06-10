package com.xxx.yyy.springbootguide.exception.constant;

import com.xxx.yyy.springbootguide.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: maoyan
 * @date: 2020/5/13 19:31:35
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum implements BusinessExceptionAssert {

    /**
     * Bad licence type
     */
    BAD_LICENCE_TYPE(7001, "Bad licence type."),
    /**
     * Licence not found
     */
    LICENCE_NOT_FOUND(7002, "Licence not found."),

    /**
     * Organization not found
     */
    ORGANIZATION_NOT_FOUND(6001, "Organization not found.")
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