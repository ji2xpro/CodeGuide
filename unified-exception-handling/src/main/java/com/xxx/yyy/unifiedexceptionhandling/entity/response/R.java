package com.xxx.yyy.unifiedexceptionhandling.entity.response;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 通用返回结果，同{@link CommonResponse}
 *
 * @author: maoyan
 * @date: 2020/6/10 14:41:16
 * @description:
 * @see CommonResponse
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class R<T> extends CommonResponse<T> {

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.message = msg;
    }

    public R(Throwable e) {
        super();
        this.message = e.getMessage();
        this.code = -1;
    }
}
