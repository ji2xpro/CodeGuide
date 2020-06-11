package com.xxx.yyy.unifiedexceptionhandling.exception.assertion;

import com.xxx.yyy.unifiedexceptionhandling.constant.IResponseEnum;
import com.xxx.yyy.unifiedexceptionhandling.exception.ArgumentException;
import com.xxx.yyy.unifiedexceptionhandling.exception.BaseException;

import java.text.MessageFormat;

/**
 * @author: maoyan
 * @date: 2020/5/14 19:34:08
 * @description:
 */
public interface ArgumentExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg, t);
    }

}