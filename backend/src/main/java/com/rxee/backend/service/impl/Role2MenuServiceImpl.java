package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.entity.Menu;
import com.rxee.backend.entity.Role2Menu;
import com.rxee.backend.mapper.Role2MenuMapper;
import com.rxee.backend.service.IMenuService;
import com.rxee.backend.service.Role2MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class Role2MenuServiceImpl extends ServiceImpl<Role2MenuMapper, Role2Menu> implements Role2MenuService {
    @Resource
    private Role2MenuMapper role2MenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    @Transactional
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<Role2Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        //先删除该roleId的所有绑定关系
        role2MenuMapper.delete(wrapper);

        List<Integer> tempPids = new ArrayList<>();
        //再添加前端传递过来的菜单id数组
        for(Integer menuId : menuIds){
            Menu menu = menuService.getById(menuId);
            if (menu.getPid() != null && !menuIds.contains(menu.getPid()) && !tempPids.contains(menu.getPid())){//表示为二级菜单 并且传过来的menuId数组中没有它的父级
                // 那么我们就得补上这个父级id
                role2MenuMapper.insert(new Role2Menu(roleId, menu.getPid()));
                tempPids.add(menu.getPid());
            }
            role2MenuMapper.insert(new Role2Menu(roleId, menuId));
        }
    }

    @Override
    public List<Integer> getMenuIds(Integer roleId) {
        return role2MenuMapper.selectByRoleId(roleId);
    }
}
