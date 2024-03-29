package com.xxx.yyy.unifiedexceptionhandling.exception.assertion;

import com.xxx.yyy.unifiedexceptionhandling.constant.IResponseEnum;
import com.xxx.yyy.unifiedexceptionhandling.exception.BaseException;
import com.xxx.yyy.unifiedexceptionhandling.exception.BusinessException;

import java.text.MessageFormat;

/**
 * <p>业务异常断言</p>
 *
 * @author: maoyan
 * @date: 2020/5/13 19:31:13
 * @description:
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg, t);
    }

}