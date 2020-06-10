package com.xxx.yyy.springbootguide.exception.assertion;

import com.xxx.yyy.springbootguide.exception.constant.IResponseEnum;
import com.xxx.yyy.springbootguide.exception.ArgumentException;
import com.xxx.yyy.springbootguide.exception.BaseException;

import java.text.MessageFormat;

/**
 * @author: maoyan
 * @date: 2020/5/14 19:56:27
 * @description:
 */
public interface CommonExceptionAssert extends IResponseEnum, Assert {

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
