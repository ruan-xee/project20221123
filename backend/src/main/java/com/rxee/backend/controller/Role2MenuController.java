package com.rxee.backend.controller;

import com.rxee.backend.service.Role2MenuService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/r2m")
public class Role2MenuController {
    @Resource
    private Role2MenuService role2MenuService;
    
    @PostMapping("/{roleId}")
    public ResultVo roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        role2MenuService.setRoleMenu(roleId, menuIds);
        return ResultVo.success();
    }

    @GetMapping("/{roleId}")
    public ResultVo getMenuIds(@PathVariable Integer roleId){
        return ResultVo.success(role2MenuService.getMenuIds(roleId));
    }
}
