package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.dto.LoginDto;
import com.rxee.backend.entity.User;
import com.rxee.backend.mapper.UserMapper;
import com.rxee.backend.service.ILoginService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    @Override
    public User loginInto(LoginDto loginDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginDto.getUsername()).eq("password", loginDto.getPassword());
        List<User> list = list(queryWrapper);//使用list查询多个是为了防止数据库中含有多条同名同密的数据（脏数据）
        if (list.size() != 1){
            return null;
        } else return list.get(0);
    }
}
