package org.gourd.hu.base.exception.assertion;


import org.gourd.hu.base.exception.ArgumentException;
import org.gourd.hu.base.exception.BaseException;
import org.gourd.hu.base.exception.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 * <pre>
 *
 * </pre>
 *
 * @author gourd.hu
 * @date 2019/5/2
 */
public interface ArgumentExceptionAssert extends IResponseEnum, BaseAssert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(String message,Object... args) {
        String msg = MessageFormat.format(message, args);
        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ArgumentException(this, args, msg, t);
    }

}
