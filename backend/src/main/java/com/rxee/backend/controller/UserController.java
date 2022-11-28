package com.rxee.backend.controller;

import com.rxee.backend.entity.User;
import com.rxee.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public Integer addUser(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping("/del")
    public Integer delUser(@RequestBody User user){
        return userService.delete(user);
    }

    @GetMapping("/page")
    public Map queryByPage(@RequestParam Integer pageSize, @RequestParam Integer pageNum, @RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;
        int totalNum = userService.queryTotalNum(username);
        Map<String, Object> res = new HashMap<>();
        List<User> users = userService.queryByPage(pageNum, pageSize, username);
        res.put("total", totalNum);
        res.put("users", users);
        return res;
    }
}
