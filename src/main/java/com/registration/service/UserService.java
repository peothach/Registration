package com.registration.service;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO save(UserRequestDto userRequestDto);

    void saveMillionRecord();
}
