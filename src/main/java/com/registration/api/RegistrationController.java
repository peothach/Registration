package com.registration.api;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.BaseResponseDto;
import com.registration.dto.response.UserResponseDto;
import com.registration.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

  private final UserService userService;
  @Value("${message.success}")
  private String successMessage;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponseDto<UserResponseDto>> registerUser(@RequestBody UserRequestDto userRequestDto) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new BaseResponseDto<UserResponseDto>(
                    HttpStatus.CREATED.value(),
                    successMessage,
                    userService.save(userRequestDto))
            );
  }
}
