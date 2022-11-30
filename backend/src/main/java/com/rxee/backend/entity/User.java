package com.rxee.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@TableName(value = "sys_user")
public class User {
    @TableId(type = IdType.AUTO)//指定主键
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String address;
    private String phone;
    @TableField(value = "create_time")//指定数据库中的字段名称
    private String createTime;

}
