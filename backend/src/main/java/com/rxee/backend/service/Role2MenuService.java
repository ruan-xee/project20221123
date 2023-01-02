package com.rxee.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxee.backend.entity.Role2Menu;

import java.util.List;

public interface Role2MenuService extends IService<Role2Menu> {
    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getMenuIds(Integer roleId);
}
