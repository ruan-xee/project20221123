package com.rxee.backend.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxee.backend.common.Constants;
import com.rxee.backend.entity.User;
import com.rxee.backend.service.IUserService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rxee
 * @since 2022-12-06
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/query")
    public ResultVo query(){
        return ResultVo.success(userService.queryAll());
    }

    @PostMapping("/add")
    public ResultVo addUser(@RequestBody User user){
        return userService.saveUser(user) ? ResultVo.success():ResultVo.fail(Constants.CODE_500, "添加失败！");
    }

    @DeleteMapping("/del/{id}")
    public ResultVo delete(@PathVariable Integer id){
        return userService.delUser(id) ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "删除失败！");
    }

    @PostMapping("/del/batch")
    public ResultVo batchDelete(@RequestBody List<Integer> ids){
        return userService.delUsers(ids) ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "批量删除失败！");
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
    public ResultVo queryByPage(@RequestParam Integer pageSize,
                                   @RequestParam Integer pageNum,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "") String address){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {userQueryWrapper.like("username", username);}
//                        .and(w->w.like("email", email))
//                        .and(w->w.like("address", address));
        if (!"".equals(address)) {userQueryWrapper.like("address", address);}
        if (!"".equals(email)) {userQueryWrapper.like("email", email);}
        userQueryWrapper.orderByDesc("id");
        IPage<User> page = new Page<>(pageNum, pageSize);
        IPage<User> users = userService.page(page, userQueryWrapper);
        return ResultVo.success(users);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<User> list = userService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题别名
//        writer.addHeaderAlias("username", "用户名");
//        writer.addHeaderAlias("password", "密码");
//        writer.addHeaderAlias("nickname","昵称");
//        writer.addHeaderAlias("email","邮箱");
//        writer.addHeaderAlias("phone","手机号");
//        writer.addHeaderAlias("address","地址");
//        writer.addHeaderAlias("createTime","创建时间");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * 导入接口
     */
    @PostMapping("/import")
    public ResultVo imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);

        /*
        //方式二：可以忽略表头的中文，直接读取表的内容，并指定对应列与属性
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for(List<Object> row:list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setAddress(row.get(4).toString());
            user.setPortrait(row.get(5).toString());
            users.add(user);
        }
         */
        return userService.saveBatch(list) ? ResultVo.success():ResultVo.fail(Constants.CODE_500, "导入失败！");
    }
}

