package com.rxee.backend.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String nickname;
    private String password;
    private String role;
}
