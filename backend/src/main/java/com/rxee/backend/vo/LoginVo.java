package com.rxee.backend.vo;

import com.rxee.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVo {
    private User user;
    private String token;
}
