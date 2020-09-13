package org.lhq.gp.product.entity;

public enum ResultCode {
  /** 成功响应 */
  SUCCESS(200,"响应成功"),
  /** 失败响应 */
  FAIL(500,"处理失败");

  public int code;
  private String message;

  ResultCode(int code,String message) {
    this.code = code;
    this.message = message;
  }



  @Override
  public String toString() {
    return "ResultCode{" +
            "code=" + code +
            ", message='" + message + '\'' +
            '}';
  }
}