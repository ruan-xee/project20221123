package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.entity.Role2Menu;
import com.rxee.backend.mapper.Role2MenuMapper;
import com.rxee.backend.service.Role2MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Role2MenuServiceImpl extends ServiceImpl<Role2MenuMapper, Role2Menu> implements Role2MenuService {
    @Resource
    private Role2MenuMapper role2MenuMapper;

    @Override
    @Transactional
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<Role2Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        //先删除该roleId的所有绑定关系
        role2MenuMapper.delete(wrapper);
        //再添加前端传递过来的菜单id数组
        for(Integer menuId : menuIds){
            role2MenuMapper.insert(new Role2Menu(roleId, menuId));
        }

    }

    @Override
    public List<Integer> getMenuIds(Integer roleId) {
        return role2MenuMapper.selectByRoleId(roleId);
    }
}
