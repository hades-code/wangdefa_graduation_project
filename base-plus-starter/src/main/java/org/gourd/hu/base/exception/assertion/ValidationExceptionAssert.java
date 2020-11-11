package org.gourd.hu.base.exception.assertion;


import org.gourd.hu.base.exception.BaseException;
import org.gourd.hu.base.exception.ValidationException;
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
public interface ValidationExceptionAssert extends IResponseEnum, BaseAssert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ValidationException(this, args, msg);
    }

    @Override
    default BaseException newException(String message,Object... args) {
        String msg = MessageFormat.format(message, args);
        return new ValidationException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ValidationException(this, args, msg, t);
    }

}
