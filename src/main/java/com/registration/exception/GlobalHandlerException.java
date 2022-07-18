package com.registration.exception;

import com.registration.dto.response.BaseResponseDto;
import com.registration.dto.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<BaseResponseDto<UserResponseDTO>> handleEmailAlreadyExistException(EmailAlreadyExistsException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponseDto<UserResponseDTO>(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        new UserResponseDTO())
                );
    }

    @ExceptionHandler(IllegalSalaryException.class)
    public final ResponseEntity<BaseResponseDto<UserResponseDTO>> handleIllegalSalaryException(IllegalSalaryException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponseDto<UserResponseDTO>(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        new UserResponseDTO())
                );
    }
}
