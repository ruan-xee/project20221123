package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.entity.Menu;
import com.rxee.backend.mapper.MenuMapper;
import com.rxee.backend.service.IMenuService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>  implements IMenuService {
    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> queryMenus(QueryWrapper<Menu> queryWrapper) {
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentNode = menus.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu: parentNode) {
            menu.setChildren(menus.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNode;
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
