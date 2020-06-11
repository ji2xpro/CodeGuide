package com.xxx.yyy.unifiedexceptionhandling.constant;

/**
 * <pre>
 *  异常返回码枚举接口
 * </pre>
 *
 * @author: maoyan
 * @date: 2020/5/13 19:29:43
 * @description:
 */
public interface IResponseEnum {
    /**
     * 获取返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     * @return 返回信息
     */
    String getMessage();
}
