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
        this.message = "请求成功";
    }
    public Result(int resultCode,T data){
        this.resultCode = resultCode;
        this.data = data;
    }
    public Result(T data){
        this.resultCode = ResultCode.SUCCESS.getCode();
        this.message = "请求成功";
        this.data = data;
    }
    public static Result success(){
        Result<Object> result = new Result<>();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    public static<T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setData(data);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    public static<T> Result<T> error(ResultCode resultCode,T data){
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMessage(resultCode.getMessage());
        result.setResultCode(resultCode);
        return result;
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
