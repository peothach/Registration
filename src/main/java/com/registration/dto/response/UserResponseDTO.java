package com.registration.dto.response;

import java.math.BigDecimal;

public class UserResponseDTO {
    private Long id;
    private String email;
    private BigDecimal salary;
    private String userType;

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", userType='" + userType + '\'' +
                '}';
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String email, BigDecimal salary, String userType) {
        this.id = id;
        this.email = email;
        this.salary = salary;
        this.userType = userType;
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
