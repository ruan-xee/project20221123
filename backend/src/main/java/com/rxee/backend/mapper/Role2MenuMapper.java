package com.rxee.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rxee.backend.entity.Role2Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface Role2MenuMapper extends BaseMapper<Role2Menu> {
    @Select("select menu_id from sys_role2menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
