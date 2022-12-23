package com.rxee.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxee.backend.entity.Role;

import java.util.List;

public interface IRoleService extends IService<Role> {
    List<Role> queryAll();

    Role queryById(Integer id);

    int addRole(Role role);

    int delRole(Integer id);

    int delRoles(List<Integer> ids);

    int updateRole(Role role);
}
