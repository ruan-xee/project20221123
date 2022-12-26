package com.rxee.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxee.backend.common.Constants;
import com.rxee.backend.entity.Menu;
import com.rxee.backend.service.IMenuService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    IMenuService menuService;

    @GetMapping("/query")
    public ResultVo queryAll(@RequestParam(defaultValue = "") String name){
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {menuQueryWrapper.like("name", name);}
        //查询所有数据
        List<Menu> menus = menuService.queryAll(menuQueryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentNode = menus.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu: parentNode) {
            menu.setChildren(menus.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return ResultVo.success(parentNode);
    }

    @PostMapping("/queryById")
    public ResultVo queryById(Integer id){
        Menu menu = menuService.queryById(id);
        if (Objects.isNull(menu)){
            return ResultVo.fail(Constants.CODE_500, "查询个人信息失败！");
        } else {
            return ResultVo.success(menu);
        }
    }

    @PostMapping("/add")
    public ResultVo addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu) == 1 ? ResultVo.success():ResultVo.fail(Constants.CODE_500, "添加失败！");
    }

    @PostMapping("/update")
    public ResultVo updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu) == 1 ? ResultVo.success():ResultVo.fail(Constants.CODE_500, "修改失败！");
    }

    @DeleteMapping("/del/{id}")
    public ResultVo delete(@PathVariable Integer id){
        return menuService.delMenu(id) == 1 ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "删除失败！");
    }

    @PostMapping("/del/batch")
    public ResultVo batchDelete(@RequestBody List<Integer> ids){
        return menuService.delMenus(ids) == ids.size() ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "批量删除失败！");
    }

    //    分页查询-mybatis plus方式
    @GetMapping("/page")
    public ResultVo queryByPage(@RequestParam Integer pageSize,
                                @RequestParam Integer pageNum,
                                @RequestParam(defaultValue = "") String name){
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {menuQueryWrapper.like("name", name);}
        menuQueryWrapper.orderByDesc("id");
        IPage<Menu> page = new Page<>(pageNum, pageSize);
        IPage<Menu> menus = menuService.page(page, menuQueryWrapper);
        return ResultVo.success(menus);
    }
}
