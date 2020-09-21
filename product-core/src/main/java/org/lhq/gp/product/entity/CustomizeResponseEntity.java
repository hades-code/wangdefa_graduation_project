package org.lhq.gp.product.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @program: wangdefa_graduation_project
 * @description: 响应实体
 * @author: Wang defa
 * @create: 2020-09-13 00:45
 */
public class CustomizeResponseEntity<T> extends ResponseEntity<T> {
  //private int code
  private int resultCode;
  private String message;
  private T data;


  public CustomizeResponseEntity() {
    super(HttpStatus.OK);
    this.resultCode = ResultCode.SUCCESS.code;
    this.message = "请求成功,处理中";
  }

  public CustomizeResponseEntity(HttpStatus httpStatus) {
    super(httpStatus);
  }
  public CustomizeResponseEntity(HttpStatus httpStatus,T data){
    super(data,httpStatus);
  }

  @Override
  public String toString() {
    return "CustomizeResponseEntity{" +
            "resultCode=" + resultCode +
            ", message='" + message + '\'' +
            ", data=" + data +
            '}';
  }

  public int getResultCode() {
    return resultCode;
  }

  public CustomizeResponseEntity<T> setResultCode(ResultCode resultCode) {
    this.resultCode = resultCode.code;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public CustomizeResponseEntity<T> setMessage(String message) {
    this.message = message;
    return this;
  }

  public T getData() {
    return data;
  }

  public CustomizeResponseEntity<T> setData(T data) {
    this.data = data;
    return this;
  }
}
