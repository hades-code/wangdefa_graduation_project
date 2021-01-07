package org.lhq.entity.vo;

import lombok.Data;
import org.lhq.common.ResultCode;

/**
 * @program: wangdefa_graduation_project
 * @description: 响应实体
 * @author: Wang defa
 * @create: 2020-09-13 00:45
 */
@Data
public class ResultVO<T> {

    private int resultCode;

    private String message;

    private T data;

    public ResultVO() {
        this.resultCode = ResultCode.SUCCESS.code;
        this.message = "请求成功";
    }
    public ResultVO(int resultCode, T data){
        this.resultCode = resultCode;
        this.data = data;
    }
    public ResultVO(T data){
        this.resultCode = ResultCode.SUCCESS.getCode();
        this.message = "请求成功";
        this.data = data;
    }
    public static ResultVO success(){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setResultCode(ResultCode.SUCCESS);
        return resultVO;
    }
    public static<T> ResultVO<T> success(T data){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(data);
        resultVO.setResultCode(ResultCode.SUCCESS);
        return resultVO;
    }
    public static<T> ResultVO<T> error(ResultCode resultCode, T data){
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(data);
        resultVO.setMessage(resultCode.getMessage());
        resultVO.setResultCode(resultCode);
        return resultVO;
    }





    public int getResultCode() {
        return resultCode;
    }

    public ResultVO<T> setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode.code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultVO<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultVO<T> setData(T data) {
        this.data = data;
        return this;
    }
}
