package com.xxx.yyy.springbootguide.entity.response;

import com.xxx.yyy.springbootguide.exception.constant.IResponseEnum;
import com.xxx.yyy.springbootguide.exception.constant.enums.CommonResponseEnum;
import lombok.Data;

/**
 * <p>基础返回结果</p>
 *
 * @author: maoyan
 * @date: 2020/5/15 14:45:59
 * @description:
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}