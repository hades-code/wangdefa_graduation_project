package org.lhq.gp.product.entity;

public enum ResultCode {
  /** 成功响应 */
  SUCCESS(200),
  /** 失败响应 */
  FAIL(500);

  private int code;

  ResultCode(int code) {
    this.code = code;
  }
}
