package com.rxee.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxee.backend.common.Constants;
import com.rxee.backend.entity.Dict;
import com.rxee.backend.mapper.DictMapper;
import com.rxee.backend.service.IDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Resource
    DictMapper dictMapper;

    @Override
    public List<Dict> queryIcons() {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("type", Constants.DICT_TYPE_ICON);
        return dictMapper.selectList(wrapper);
    }
}
