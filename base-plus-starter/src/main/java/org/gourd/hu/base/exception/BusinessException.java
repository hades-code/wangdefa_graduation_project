package org.gourd.hu.base.exception;


import org.gourd.hu.base.exception.enums.IResponseEnum;

/**
 * <p>业务异常</p>
 * <p>业务处理时，出现异常，可以抛出该异常</p>
 *
 * @author gourd.hu
 * @date 2019/5/2
 */
public class BusinessException extends  BaseException {

    public BusinessException(IResponseEnum responseEnum) {
        super(responseEnum);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args) {
        super(responseEnum, args);
    }
    public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}