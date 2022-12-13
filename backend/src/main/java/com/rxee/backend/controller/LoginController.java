package com.rxee.backend.controller;

import com.rxee.backend.common.Constants;
import com.rxee.backend.dto.LoginDto;
import com.rxee.backend.entity.User;
import com.rxee.backend.exception.ServiceException;
import com.rxee.backend.service.ILoginService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    ILoginService loginService;

    @PostMapping("/into")
    public ResultVo loginInto(@RequestBody LoginDto loginDto){
        User user = loginService.loginInto(loginDto);
        return ResultVo.success(user);
    }
}
