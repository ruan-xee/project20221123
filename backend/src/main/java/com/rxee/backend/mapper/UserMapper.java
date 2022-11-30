package com.rxee.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rxee.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> findAll();

    int insert(User user);

    int update(User user);

    int deleteById(User user);

    List<User> queryByPage(Integer pageNum, Integer pageSize, String username);

    int queryCount(String username);
}
