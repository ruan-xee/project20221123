package com.rxee.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxee.backend.entity.User;
import com.rxee.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/query")
    public List<User> query(){
        return userService.queryAll();
    }

    @PostMapping("/add")
    public boolean addUser(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/del")
    public boolean delUser(@RequestBody User user){
        return userService.delete(user);
    }

//    @GetMapping("/page")
//    public Map queryByPage(@RequestParam Integer pageSize, @RequestParam Integer pageNum, @RequestParam String username){
//        pageNum = (pageNum - 1) * pageSize;
//        int totalNum = userService.queryTotalNum(username);
//        Map<String, Object> res = new HashMap<>();
//        List<User> users = userService.queryByPage(pageNum, pageSize, username);
//        res.put("total", totalNum);
//        res.put("users", users);
//        return res;
//    }

//    分页查询-mybatis plus方式
    @GetMapping("/page")
    public IPage<User> queryByPage(@RequestParam Integer pageSize,
                                   @RequestParam Integer pageNum,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "") String address){
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {userQueryWrapper.like("username", username);}
//                        .and(w->w.like("email", email))
//                        .and(w->w.like("address", address));
        if (!"".equals(address)) {userQueryWrapper.like("address", address);}
        if (!"".equals(email)) {userQueryWrapper.like("email", email);}
        return userService.page(page, userQueryWrapper);
    }
}
