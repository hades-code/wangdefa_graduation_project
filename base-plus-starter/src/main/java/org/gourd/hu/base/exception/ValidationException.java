package org.gourd.hu.base.exception;


import org.gourd.hu.base.exception.enums.IResponseEnum;

/**
 * <p>校验异常</p>
 * <p>调用接口时，参数格式不合法可以抛出该异常</p>
 *
 * @author gourd.hu
 * @date 2019/5/2
 */
public class ValidationException extends  BaseException {

    public ValidationException(IResponseEnum responseEnum) {
        super(responseEnum);
    }


    public ValidationException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public ValidationException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
