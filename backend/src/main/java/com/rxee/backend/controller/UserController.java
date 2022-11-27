package com.rxee.backend.controller;

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

    @GetMapping("/index")
    public List<User> index(){
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
}
