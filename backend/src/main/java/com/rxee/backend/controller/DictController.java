package com.rxee.backend.controller;

import com.rxee.backend.entity.Dict;
import com.rxee.backend.service.IDictService;
import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Resource
    IDictService dictService;

    @GetMapping("/icons")
    public ResultVo getIcons(){
        List<Dict> icons = dictService.queryIcons();
        return ResultVo.success(icons);
    }

}
