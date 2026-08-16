package com.example.demo;

public class LoginResponse {

    private String token;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String token,
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            String role) {

        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }
}