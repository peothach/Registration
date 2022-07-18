package com.registration.converter;

import com.registration.enums.UserType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType) {
        return userType.getValue();
    }

    @Override
    public UserType convertToEntityAttribute(String value) {
        return UserType.fromValue(value);
    }
}
