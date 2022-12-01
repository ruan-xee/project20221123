package com.rxee.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.entity.User;
import com.rxee.backend.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;

    public boolean saveUser(User user){
//        if (Objects.nonNull(user.getId())) {
//            return updateById(user);//save为mybatis-plus提供的方法，表示保存数据
//        } else {
//            return save(user);
//        }
        return saveOrUpdate(user);
    }

    public List<User> queryAll(){
        return list();
    }

    public boolean delUser(Integer id){
        return removeById(id);
    }

//    public Integer save(User user){
//        if (Objects.nonNull(user.getId())){
//            return userMapper.update(user);
//        } else {
//            return userMapper.insert(user);
//        }
//    }
//
//    public List<User> queryAll(){
//        return userMapper.findAll();
//    }

//    public Integer delete(User user){
//        return userMapper.deleteById(user);
//    }

    public List<User> queryByPage(Integer pageNum, Integer pageSize, String username) {
        return userMapper.queryByPage(pageNum, pageSize, username);
    }

    public int queryTotalNum(String username){
        return userMapper.queryCount(username);
    }
}
