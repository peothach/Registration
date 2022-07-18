package com.registration.dto.request;

public class UserRequestDTO {

    private String email;
    private String password;
    private double salary;

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salary=" + salary +
                '}';
    }

    public UserRequestDTO() {
    }

    public UserRequestDTO(String email, String password, double salary) {
        this.email = email;
        this.password = password;
        this.salary = salary;
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
