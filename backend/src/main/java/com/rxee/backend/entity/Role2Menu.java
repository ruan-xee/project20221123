package com.rxee.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@TableName("sys_role2menu")
@NoArgsConstructor
@AllArgsConstructor
public class Role2Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleId;
    private Integer menuId;
}
