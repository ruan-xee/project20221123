package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.entity.Role;
import com.rxee.backend.mapper.RoleMapper;
import com.rxee.backend.service.IRoleService;
import com.rxee.backend.vo.RoleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>  implements IRoleService {
    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Role> queryAll() {
        return roleMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Role queryById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public int addRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int delRole(Integer id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public int delRoles(List<Integer> ids) {
        return roleMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateById(role);
    }

    @Override
    public Integer queryRoleIdByRole(String flag) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("flag", flag);
        Role one = roleMapper.selectOne(wrapper);
        return one.getId();
    }

    @Override
    public List<RoleVo> getRoleList() {
        ArrayList<RoleVo> roleVos = new ArrayList<>();
        for (Role role: roleMapper.selectList(new QueryWrapper<>())) {
            RoleVo roleVo = new RoleVo();
            roleVo.setName(role.getName());
            roleVo.setFlag(role.getFlag());
            roleVos.add(roleVo);
        }
        return roleVos;
    }
}
