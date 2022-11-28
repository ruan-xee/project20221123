package com.rxee.backend.service;

import com.rxee.backend.entity.User;
import com.rxee.backend.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public Integer save(User user){
        if (Objects.nonNull(user.getId())){
            return userMapper.update(user);
        } else {
            return userMapper.insert(user);
        }
    }

    public List<User> queryAll(){
        return userMapper.findAll();
    }

    public Integer delete(User user){
        return userMapper.deleteById(user);
    }

    public List<User> queryByPage(Integer pageNum, Integer pageSize, String username) {
        return userMapper.queryByPage(pageNum, pageSize, username);
    }

    public int queryTotalNum(String username){
        return userMapper.queryCount(username);
    }
}
