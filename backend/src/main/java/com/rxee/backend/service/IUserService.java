package com.rxee.backend.service;

import com.rxee.backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rxee
 * @since 2022-12-06
 */
public interface IUserService extends IService<User> {

    List<User> queryAll();

    boolean saveUser(User user);

    boolean delUser(Integer id);

    boolean delUsers(List<Integer> ids);
}
