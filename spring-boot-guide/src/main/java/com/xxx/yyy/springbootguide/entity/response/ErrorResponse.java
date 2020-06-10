package com.xxx.yyy.springbootguide.entity.response;

/**
 * <p>错误返回结果</p>
 *
 * @author: maoyan
 * @date: 2020/5/15 14:56:13
 * @description:
 */
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}
