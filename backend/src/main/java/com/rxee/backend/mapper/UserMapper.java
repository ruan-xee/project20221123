package com.rxee.backend.mapper;

import com.rxee.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll();

    int insert(User user);

    int update(User user);

    int deleteById(User user);

    List<User> queryByPage(Integer pageNum, Integer pageSize, String username);

    int queryCount(String username);
}
