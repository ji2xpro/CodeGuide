package com.xxx.yyy.springbootguide.enums;

import com.xxx.yyy.unifiedexceptionhandling.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: maoyan
 * @date: 2020/6/11 15:03:15
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum implements BusinessExceptionAssert {

    /**
     *
     */
    BAD_LICENCE_TYPE(7001, "Bad licence type."),
    /**
     *
     */
    ITEM_NOT_FOUND(7002, "item not found.")

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
