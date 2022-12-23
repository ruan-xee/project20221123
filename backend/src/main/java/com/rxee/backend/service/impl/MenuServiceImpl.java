package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.entity.Menu;
import com.rxee.backend.mapper.MenuMapper;
import com.rxee.backend.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>  implements IMenuService {
    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> queryAll() {
        return menuMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Menu queryById(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public int addMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int delMenu(Integer id) {
        return menuMapper.deleteById(id);
    }

    @Override
    public int delMenus(List<Integer> ids) {
        return menuMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateById(menu);
    }
}
