package com.registration.dto.response;

public class BaseResponseDto<T> {
  private int code;
  private String message;
  private T data;

  public BaseResponseDto() {
  }

  public BaseResponseDto(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public BaseResponseDto(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  @Override
  public String toString() {
    return "BaseResponseDto{"
            + "code=" + code
            + ", message='" + message + '\''
            + ", data=" + data
            + '}';
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
