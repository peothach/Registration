package com.registration.service.impl;

import com.registration.dto.request.UserRequestDto;
import com.registration.dto.response.UserResponseDTO;
import com.registration.entity.UserEntity;
import com.registration.enums.UserType;
import com.registration.exception.EmailAlreadyExistsException;
import com.registration.exception.IllegalSalaryException;
import com.registration.repository.UserRepository;
import com.registration.ruleengine.Rule;
import com.registration.ruleengine.RuleEngine;
import com.registration.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Value("${message.exception.email-already-exists}")
    private String emailAlreadyExitsMessage;

    @Value("${message.exception.illegal-salary}")
    private String illegalSalaryMessage;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepo = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDTO save(UserRequestDto userRequestDto) {
        // Check email already exists or not
        Optional<UserEntity> userByEmail = userRepo.findUserEntityByEmail(userRequestDto.getEmail());
        if (userByEmail.isPresent()) throw new EmailAlreadyExistsException(emailAlreadyExitsMessage);
        // Mapper dto to entity
        UserEntity user = this.modelMapper.map(userRequestDto, UserEntity.class);
        Map<UserType, Rule<UserType>> rules = RuleEngine.createRules(userRequestDto.getSalary());
        UserType userType = Stream.of(UserType.values())
                .filter(memberType -> rules.get(memberType).isApplicable())
                .map(memberType -> rules.get(memberType).applyProcess())
                .findFirst()
                .orElseThrow(() -> new IllegalSalaryException(illegalSalaryMessage));
        user.setUserType(userType);

        return this.modelMapper.map(userRepo.save(user), UserResponseDTO.class);
    }

    @Override
    public void saveMillionRecord() {
        int corePoolSize = 10;
        int maximumPoolSize = 20;
        int queueCapacity = 1000000;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, // Số corePoolSize
                maximumPoolSize, // số maximumPoolSize
                10, // thời gian một thread được sống nếu không làm gì
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity));

        long numberOfRecord = 10000000;
        for (int i = 0; i < numberOfRecord; i++) {
            UserEntity user = new UserEntity();
            user.setEmail("John" + i + "@gmail.com");
            user.setPassword("password");
            double salary = new Random().nextInt(15000);
            user.setSalary(new Random().nextInt(15000));
            Map<UserType, Rule<UserType>> rules = RuleEngine.createRules(salary);
            UserType userType = Stream.of(UserType.values())
                    .filter(memberType -> rules.get(memberType).isApplicable())
                    .map(memberType -> rules.get(memberType).applyProcess())
                    .findFirst().orElseThrow();
            user.setUserType(userType);
            executor.execute(() -> userRepo.save(user));
        }

    }
}
