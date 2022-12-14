package com.rxee.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@TableName("sys_menu")
public class Menu implements Serializable{
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String path;
    private String icon;
    private String description;
    private Integer pid;
    private String pagePath;
    @TableField(exist = false)
    private List<Menu> children;
}
