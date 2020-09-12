package org.lhq.gp.product.entity;

/**
 * @program: wangdefa_graduation_project
 * @description: 响应实体
 * @author: Wang defa
 * @create: 2020-09-13 00:45
 */
public class ResponseEntity<T> {
  private ResultCode resultCode;
  private String message;
  private T data;

  public ResponseEntity() {
    this.resultCode = ResultCode.SUCCESS;
    this.message = "请求成功,处理中";
  }

  @Override
  public String toString() {
    return "ResponseEntity{"
        + "resultCode="
        + resultCode
        + ", message='"
        + message
        + '\''
        + ", data="
        + data
        + '}';
  }

  public ResultCode getResultCode() {
    return resultCode;
  }

  public ResponseEntity<T> setResultCode(ResultCode resultCode) {
    this.resultCode = resultCode;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public ResponseEntity<T> setMessage(String message) {
    this.message = message;
    return this;
  }

  public T getData() {
    return data;
  }

  public ResponseEntity<T> setData(T data) {
    this.data = data;
    return this;
  }
}
