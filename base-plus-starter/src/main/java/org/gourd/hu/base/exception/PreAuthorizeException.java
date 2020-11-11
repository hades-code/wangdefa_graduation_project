package org.gourd.hu.base.exception;


import org.gourd.hu.base.exception.enums.IResponseEnum;

/**
 * <p>权限异常</p>
 * <p>权限校验时，出现异常，可以抛出该异常</p>
 *
 * @author gourd.hu
 * @date 2020/10/27
 */
public class PreAuthorizeException extends  BaseException {

    public PreAuthorizeException(IResponseEnum responseEnum) {
        super(responseEnum);
    }

    public PreAuthorizeException(IResponseEnum responseEnum, Object[] args) {
        super(responseEnum, args);
    }
    public PreAuthorizeException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public PreAuthorizeException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}