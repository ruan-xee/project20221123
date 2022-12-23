package com.rxee.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxee.backend.common.Constants;
import com.rxee.backend.entity.Role;
import com.rxee.backend.entity.User;
import com.rxee.backend.service.IRoleService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    IRoleService roleService;

    @PostMapping("/query")
    public ResultVo query(){
        return ResultVo.success(roleService.queryAll());
    }

    @PostMapping("/queryById")
    public ResultVo queryById(Integer id){
        Role role = roleService.queryById(id);
        if (Objects.isNull(role)){
            return ResultVo.fail(Constants.CODE_500, "查询个人信息失败！");
        } else {
            return ResultVo.success(role);
        }
    }

    @PostMapping("/add")
    public ResultVo addRole(@RequestBody Role role){
        return roleService.addRole(role) == 1 ? ResultVo.success():ResultVo.fail(Constants.CODE_500, "添加失败！");
    }

    @PostMapping("/update")
    public ResultVo updateRole(@RequestBody Role role){
        return roleService.updateRole(role) == 1 ? ResultVo.success():ResultVo.fail(Constants.CODE_500, "修改失败！");
    }

    @DeleteMapping("/del/{id}")
    public ResultVo delete(@PathVariable Integer id){
        return roleService.delRole(id) == 1 ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "删除失败！");
    }

    @PostMapping("/del/batch")
    public ResultVo batchDelete(@RequestBody List<Integer> ids){
        return roleService.delRoles(ids) == ids.size() ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "批量删除失败！");
    }

    //    分页查询-mybatis plus方式
    @GetMapping("/page")
    public ResultVo queryByPage(@RequestParam Integer pageSize,
                                @RequestParam Integer pageNum,
                                @RequestParam(defaultValue = "") String name){
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {roleQueryWrapper.like("name", name);}
        roleQueryWrapper.orderByDesc("id");
        IPage<Role> page = new Page<>(pageNum, pageSize);
        IPage<Role> roles = roleService.page(page, roleQueryWrapper);
        return ResultVo.success(roles);
    }
}
