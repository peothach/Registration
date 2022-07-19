package com.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public class UserResponseDto {
  private Long id;
  private String email;
  private BigDecimal salary;
  private String userType;
  @JsonProperty(value = "create_at")
  private LocalDate createAt;

  public UserResponseDto() {
  }

  public UserResponseDto(Long id, String email, BigDecimal salary, String userType, LocalDate createAt) {
    this.id = id;
    this.email = email;
    this.salary = salary;
    this.userType = userType;
    this.createAt = createAt;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public LocalDate getCreateAt() {
    return createAt;
  }

  public void setCreateAt(LocalDate createAt) {
    this.createAt = createAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }
}
