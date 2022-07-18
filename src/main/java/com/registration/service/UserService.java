package com.registration.service;

import com.registration.dto.request.UserRequestDTO;
import com.registration.dto.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO save(UserRequestDTO userRequestDto);
}
