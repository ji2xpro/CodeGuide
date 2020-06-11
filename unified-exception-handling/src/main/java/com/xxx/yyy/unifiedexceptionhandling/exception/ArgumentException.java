package com.xxx.yyy.unifiedexceptionhandling.exception;


import com.xxx.yyy.unifiedexceptionhandling.constant.IResponseEnum;

/**
 * <p>参数异常</p>
 * <p>在处理业务过程中校验参数出现错误, 可以抛出该异常</p>
 * <p>编写公共代码（如工具类）时，对传入参数检查不通过时，可以抛出该异常</p>
 *
 * @author: maoyan
 * @date: 2020/5/14 19:34:34
 * @description:
 */
public class ArgumentException extends  BaseException {

    private static final long serialVersionUID = 1L;

    public ArgumentException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public ArgumentException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
