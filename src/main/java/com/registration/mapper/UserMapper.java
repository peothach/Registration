package com.registration.mapper;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDto;
import com.registration.entity.UserEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  private final PasswordEncoder passwordEncoder;

  public UserMapper(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public UserEntity mapDtoToEntity(UserRequestDto userRequestDto) {
    UserEntity userEntity = new UserEntity();
    userEntity.setCreateAt(LocalDate.now());
    userEntity.setEmail(userRequestDto.getEmail());
    userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
    userEntity.setSalary(userRequestDto.getSalary());
    userEntity.setCreateAt(LocalDate.now());
    return userEntity;
  }

  public UserResponseDto mapEntityToDto(UserEntity userEntity) {
    UserResponseDto userResponseDto = new UserResponseDto();
    userResponseDto.setId(userEntity.getId());
    userResponseDto.setEmail(userEntity.getEmail());
    userResponseDto.setSalary(BigDecimal.valueOf(userEntity.getSalary()));
    userResponseDto.setUserType(userEntity.getUserType().getValue());
    userResponseDto.setCreateAt(userEntity.getCreateAt());
    return userResponseDto;

  }
}
