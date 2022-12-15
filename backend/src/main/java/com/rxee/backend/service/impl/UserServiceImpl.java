package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxee.backend.entity.User;
import com.rxee.backend.mapper.UserMapper;
import com.rxee.backend.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rxee
 * @since 2022-12-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean saveUser(User user) {
        return saveOrUpdate(user);
    }

    @Override
    public boolean delUser(Integer id) {
        return removeById(id);
    }

    @Override
    public boolean delUsers(List<Integer> ids) {
        return removeByIds(ids);
    }

}
