package com.registration.enums;

public enum UserType {
  SILVER("Silver"), GOLD("Gold"), PLATINUM("Platinum");
  private final String value;

  UserType(String value) {
    this.value = value;
  }

  public static UserType fromValue(String value) {
    for (UserType type : UserType.values()) {
      if (type.getValue().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }

  public String getValue() {
    return value;
  }
}
