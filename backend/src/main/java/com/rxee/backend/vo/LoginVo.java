package com.rxee.backend.vo;

import com.rxee.backend.entity.Menu;
import com.rxee.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginVo {
    private User user;
    private List<Menu> menus;
    private String token;
}
