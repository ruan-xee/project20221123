package com.rxee.backend.controller;

import com.rxee.backend.common.Constants;
import com.rxee.backend.dto.LoginDto;
import com.rxee.backend.dto.RegisterDto;
import com.rxee.backend.entity.User;
import com.rxee.backend.exception.ServiceException;
import com.rxee.backend.service.ILoginService;
import com.rxee.backend.utils.TokenUtils;
import com.rxee.backend.vo.LoginVo;
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
        String token = TokenUtils.getToken(user.getId().toString(), user.getPassword());
        return ResultVo.success(new LoginVo(user, token));
    }

    @PostMapping("/regist")
    public ResultVo regist(@RequestBody RegisterDto registerDto){
        if (loginService.isExistUser(registerDto)){
            return ResultVo.fail(Constants.CODE_600, "用户名已存在，请重新输入!");
        }
        if (loginService.registerUser(registerDto)){
            return ResultVo.success();
        } else {
            return ResultVo.fail(Constants.CODE_500, "系统错误，请重试！");
        }
    }
}
