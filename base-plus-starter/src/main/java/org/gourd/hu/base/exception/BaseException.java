package org.gourd.hu.base.exception;

import lombok.Getter;
import org.gourd.hu.base.exception.enums.IResponseEnum;

/**
 * <p>基础异常类，所有自定义异常类都需要继承本类</p>
 *
 * @author gourd.hu
 * @date 2019/5/2
 */
@Getter
public class BaseException extends RuntimeException {

    /**
     * 返回码
     */
    protected IResponseEnum responseEnum;
    /**
     * 异常消息占位参数
     */
    protected Object[] args;

    public BaseException(IResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.responseEnum = new IResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return msg;
            }
        };
    }

    public BaseException(IResponseEnum responseEnum, Object[] args) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
        this.args = args;
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.responseEnum = responseEnum;
        this.args = args;
    }
}
