package com.rxee.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxee.backend.dto.LoginDto;
import com.rxee.backend.dto.RegisterDto;
import com.rxee.backend.entity.User;

public interface ILoginService extends IService<User> {

    User loginInto(LoginDto loginDto);

    boolean isExistUser(RegisterDto registerDto);

    boolean registerUser(RegisterDto registerDto);
}
