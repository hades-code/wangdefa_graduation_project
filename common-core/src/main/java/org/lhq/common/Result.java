package org.lhq.common;

/**
 * @program: wangdefa_graduation_project
 * @description: 响应实体
 * @author: Wang defa
 * @create: 2020-09-13 00:45
 */
public class Result<T> {

    private int resultCode;

    private String message;

    private T data;

    public Result() {
        this.resultCode = ResultCode.SUCCESS.code;
        this.message = "请求成功,处理中";
    }



    @Override
    public String toString() {
        return "CustomizeResponseEntity{" + "resultCode=" + resultCode + ", message='" + message + '\'' + ", data="
            + data + '}';
    }

    public int getResultCode() {
        return resultCode;
    }

    public Result<T> setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode.code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
