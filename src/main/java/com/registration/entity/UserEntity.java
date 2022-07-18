package com.registration.entity;

import com.registration.converter.UserTypeConverter;
import com.registration.enums.UserType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_email", nullable = false, length = 255, unique = true)
    private String email;
    @Column(name = "user_password", nullable = false, length = 255)
    private String password;
    @Column(name = "user_salary", nullable = false, length = 255)
    private double salary;
    @Convert(converter = UserTypeConverter.class)
    @Column(name = "user_type", nullable = false, length = 255)
    private UserType userType;

    @Column(name = "create_at", nullable = false, length = 255)
    private LocalDate createAt;

    public UserEntity() {
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public UserEntity(Long id, String email, String password, double salary, UserType userType, LocalDate createAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.userType = userType;
        this.createAt = createAt;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserEntity(String email, String password, double salary, UserType userType) {
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
