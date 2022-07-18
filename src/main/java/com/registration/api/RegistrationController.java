package com.registration.api;

import com.registration.dto.request.UserRequestDTO;
import com.registration.dto.response.BaseResponseDto;
import com.registration.dto.response.UserResponseDTO;
import com.registration.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    @Value("${message.success}")
    private String successMessage;
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponseDto<UserResponseDTO>> registerUser(@RequestBody UserRequestDTO userRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseDto<UserResponseDTO>(
                        HttpStatus.CREATED.value(),
                        successMessage,
                        userService.save(userRequestDto))
                );
    }
}
