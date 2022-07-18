package com.registration.mapper;

import com.registration.dto.request.UserRequestDTO;
import com.registration.dto.response.UserResponseDTO;
import com.registration.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class UserMapper {

    public UserEntity mapDtoToEntity(UserRequestDTO userRequestDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setCreateAt(LocalDate.now());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setSalary(userRequestDTO.getSalary());
        return userEntity;
    }

    public UserResponseDTO mapEntityToDTO(UserEntity userEntity){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setSalary(BigDecimal.valueOf(userEntity.getSalary()));
        userResponseDTO.setUserType(userEntity.getUserType().getValue());
        return userResponseDTO;

    }
}
