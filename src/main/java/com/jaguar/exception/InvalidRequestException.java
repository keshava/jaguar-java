package com.jaguar.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends JaguarException {
  private static final long serialVersionUID = 2L;

  private final String param;

  public InvalidRequestException(
      String message,
      String param,
      String requestId,
      String code,
      Integer statusCode,
      Throwable e) {
    super(message, requestId, code, statusCode, e);
    this.param = param;
  }
}
