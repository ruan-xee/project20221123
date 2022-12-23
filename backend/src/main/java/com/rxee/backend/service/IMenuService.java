package com.rxee.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxee.backend.entity.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    List<Menu> queryAll();

    Menu queryById(Integer id);

    int addMenu(Menu menu);

    int delMenu(Integer id);

    int delMenus(List<Integer> ids);

    int updateMenu(Menu menu);
}
