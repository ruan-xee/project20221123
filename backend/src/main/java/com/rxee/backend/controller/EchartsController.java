package com.rxee.backend.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.rxee.backend.entity.User;
import com.rxee.backend.service.IUserService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    IUserService userService;

    @GetMapping("/example")
    public ResultVo get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return ResultVo.success(map);
    }

    @GetMapping("/members")
    public ResultVo members(){
        List<User> users = userService.queryAll();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user: users) {
            Date time = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(time);
            switch (quarter){
                case Q1: q1++;break;
                case Q2: q2++;break;
                case Q3: q3++;break;
                case Q4: q4++;break;
                default: break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("第一季度", "第二季度", "第三季度", "第四季度"));
        map.put("y", CollUtil.newArrayList(q1,q2,q3,q4));
        return ResultVo.success(map);
    }
}
