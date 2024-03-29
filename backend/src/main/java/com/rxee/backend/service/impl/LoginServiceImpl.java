package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.common.Constants;
import com.rxee.backend.dto.LoginDto;
import com.rxee.backend.dto.RegisterDto;
import com.rxee.backend.entity.User;
import com.rxee.backend.exception.ServiceException;
import com.rxee.backend.mapper.UserMapper;
import com.rxee.backend.service.ILoginService;
import com.rxee.backend.vo.RoleVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Override
    public User loginInto(LoginDto loginDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginDto.getUsername()).eq("password", loginDto.getPassword());
        List<User> list = list(queryWrapper);//使用list查询多个是为了防止数据库中含有多条同名同密的数据（脏数据）
        if (list.size() != 1){
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误！请重试！");
        } else return list.get(0);
    }

    @Override
    public boolean isExistUser(RegisterDto registerDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", registerDto.getUsername());
        List<User> list = list(queryWrapper);
        return list.size() > 0;
    }

    @Override
    public boolean registerUser(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setNickname(registerDto.getNickname());
        user.setPassword(registerDto.getPassword());
        user.setRole(registerDto.getRole());
        return save(user);
    }
}
