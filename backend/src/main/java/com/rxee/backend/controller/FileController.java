package com.rxee.backend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.rxee.backend.vo.ResultVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${file.upload.path}")
    private String uploadPath;


    @SneakyThrows
    @PostMapping("/upload")
    public ResultVo uploadFile(@RequestParam MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //存储到磁盘
        File parentFile = new File(uploadPath);
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        String uuid = IdUtil.fastSimpleUUID();
        File uploadFile = new File(uploadPath + uuid + StrUtil.DOT + type);
        //把获取到的文件存储到磁盘目录中
        file.transferTo(uploadFile);
        return new ResultVo();
    }
}
