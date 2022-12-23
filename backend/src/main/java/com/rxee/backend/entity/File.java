package com.rxee.backend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_file")
public class File implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private Long Size;
    private String url;
    private boolean delflag = true;
    private boolean enable = true;
    private String md5;

}
