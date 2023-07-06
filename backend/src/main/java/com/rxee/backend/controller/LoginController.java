package com.rxee.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rxee.backend.common.Constants;
import com.rxee.backend.dto.LoginDto;
import com.rxee.backend.dto.RegisterDto;
import com.rxee.backend.entity.Menu;
import com.rxee.backend.entity.User;
import com.rxee.backend.exception.ServiceException;
import com.rxee.backend.mapper.RoleMapper;
import com.rxee.backend.service.ILoginService;
import com.rxee.backend.service.IMenuService;
import com.rxee.backend.service.IRoleService;
import com.rxee.backend.service.Role2MenuService;
import com.rxee.backend.utils.TokenUtils;
import com.rxee.backend.vo.LoginVo;
import com.rxee.backend.vo.ResultVo;
import com.rxee.backend.vo.RoleVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private ILoginService loginService;

    @Resource
    private IRoleService roleService;

    @Resource
    private Role2MenuService role2MenuService;

    @Resource
    private IMenuService menuService;

    @PostMapping("/into")
    public ResultVo loginInto(@RequestBody LoginDto loginDto){
        //验证是否存在该登录请求的用户
        User user = loginService.loginInto(loginDto);
        //获取用户角色
        String role = user.getRole();

        List<Menu> menus = getMenus(role);


        String token = TokenUtils.getToken(user.getId().toString(), user.getPassword());
        return ResultVo.success(new LoginVo(user, menus, token));
    }

    @PostMapping("/regist")
    public ResultVo regist(@RequestBody RegisterDto registerDto){
        if (loginService.isExistUser(registerDto)){
            return ResultVo.fail(Constants.CODE_600, "用户名已存在，请重新输入!");
        }
        if (loginService.registerUser(registerDto)){
            return ResultVo.success();
        } else {
            return ResultVo.fail(Constants.CODE_500, "系统错误，请重试！");
        }
    }

    @PostMapping("/getRoleList")
    public ResultVo getRoleList(){
        List<RoleVo> roleList = roleService.getRoleList();
        if (roleList.size() != 0) {
            return ResultVo.success(roleList);
        } else {
            return ResultVo.fail(Constants.CODE_500, "获取角色失败，请重试！");
        }
    }


    private List<Menu> getMenus(String flag){
        //获取角色对应id
        Integer roleId = roleService.queryRoleIdByRole(flag);
        //获取该角色权限下的菜单
        List<Integer> menuIds = role2MenuService.getMenuIds(roleId);
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        List<Menu> menus = menuService.queryMenus(wrapper);

        List<Menu> roleMenus = new ArrayList<>();

        for (Menu menu: menus) {
            if (menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            if(Objects.nonNull(children)) {children.removeIf(child -> !menuIds.contains(child.getId()));}
        }

        return roleMenus;
    }
}
