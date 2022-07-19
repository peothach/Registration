package com.registration.service;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDto;

public interface UserService {
  UserResponseDto save(UserRequestDto userRequestDto);
}
