package com.rxee.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rxee.backend.entity.Dict;

import java.util.List;

public interface IDictService extends IService<Dict> {
    List<Dict> queryIcons();
}
