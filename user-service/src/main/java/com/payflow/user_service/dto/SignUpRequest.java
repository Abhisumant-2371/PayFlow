package com.payflow.user_service.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignUpRequest {
    private String name;
    private String password;
    private String email;
    private String adminKey;

    SignUpRequest(String name, String password, String email, String adminKey) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.adminKey = adminKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

}
