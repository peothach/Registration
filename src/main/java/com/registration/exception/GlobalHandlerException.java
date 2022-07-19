package com.registration.exception;

import com.registration.dto.response.BaseResponseDto;
import com.registration.dto.response.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {

  @ExceptionHandler(value = {
      EmailAlreadyExistsException.class,
      IllegalEmailException.class,
      IllegalSalaryException.class
  })
  public final ResponseEntity<BaseResponseDto<UserResponseDto>> handleEmailAlreadyExistException(Exception exception) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new BaseResponseDto<UserResponseDto>(
                    HttpStatus.BAD_REQUEST.value(),
                    exception.getMessage(),
                    null)
            );
  }
}
