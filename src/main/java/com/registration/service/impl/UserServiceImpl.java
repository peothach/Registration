package com.registration.service.impl;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDto;
import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import com.registration.exception.EmailAlreadyExistsException;
import com.registration.exception.IllegalEmailException;
import com.registration.exception.IllegalSalaryException;
import com.registration.mapper.UserMapper;
import com.registration.repository.UserRepository;
import com.registration.ruleengine.Rule;
import com.registration.ruleengine.RuleEngine;
import com.registration.service.UserService;
import com.registration.utils.ValidationUtils;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepo;
  private final UserMapper userMapper;
  @Value("${message.exception.email-already-exists}")
  private String emailAlreadyExitsMessage;
  @Value("${message.exception.illegal-salary}")
  private String illegalSalaryMessage;
  @Value("${message.exception.illegal-email}")
  private String illegalEmailMessage;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
    this.userRepo = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public UserResponseDto save(UserRequestDto userRequestDto) {
    // Check email valid or not
    if (!ValidationUtils.validateEmail(userRequestDto.getEmail())) {
      throw new IllegalEmailException(illegalEmailMessage);
    }
    // Check email already exists or not
    Optional<UserEntity> userByEmail = userRepo.findUserEntityByEmail(userRequestDto.getEmail());

    if (userByEmail.isPresent()) {
      throw new EmailAlreadyExistsException(emailAlreadyExitsMessage);
    }

    // Mapper dto to entity
    UserEntity user = this.userMapper.mapDtoToEntity(userRequestDto);
    Map<UserType, Rule<UserType>> rules = RuleEngine.createRules(userRequestDto.getSalary());
    UserType userType = Stream.of(UserType.values())
        .filter(memberType -> rules.get(memberType).isApplicable())
        .map(memberType -> rules.get(memberType).applyProcess())
        .findFirst()
        .orElseThrow(() -> new IllegalSalaryException(illegalSalaryMessage));
    user.setUserType(userType);

    return this.userMapper.mapEntityToDto(userRepo.save(user));
  }
}
