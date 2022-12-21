package com.rxee.backend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxee.backend.common.Constants;
import com.rxee.backend.mapper.FileMapper;
import com.rxee.backend.vo.ResultVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String uploadPath;

    @Resource
    private FileMapper fileMapper;

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
        //获取文件的md5，通过对比md5避免重复上传相同内容的文件
        String md5 = SecureUtil.md5(uploadFile);
        com.rxee.backend.entity.File selectOne = getFileByMd5(md5);
        //获取文件的url
        String url;
        if (Objects.nonNull(selectOne)){
            //文件已经存在，所以删除重复文件
            uploadFile.delete();
            url = selectOne.getUrl();
        } else {
            url = "http://localhost:8088/file/" + uuid + StrUtil.DOT + type;
        }

        //存储数据库
        com.rxee.backend.entity.File filemodel = new com.rxee.backend.entity.File();
        filemodel.setSize(size/1024);
        filemodel.setType(type);
        filemodel.setName(originalFilename);
        filemodel.setUrl(url);
        filemodel.setMd5(md5);
        int insert = fileMapper.insert(filemodel);
        if (insert == 1) {
            return ResultVo.success(filemodel.getUrl());
        } else {
            return ResultVo.fail(Constants.CODE_600, "上传文件失败！");
        }
    }

    @SneakyThrows
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response){
        File file = new File(uploadPath+fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(file));
        os.flush();
        os.close();
    }

    private com.rxee.backend.entity.File getFileByMd5(String md5){
        QueryWrapper<com.rxee.backend.entity.File> qw = new QueryWrapper<>();
        qw.eq("md5", md5);
        List<com.rxee.backend.entity.File> files = fileMapper.selectList(qw);
        return files.isEmpty() ? null : files.get(0);
    }

    //    分页查询-mybatis plus方式
    @GetMapping("/page")
    public ResultVo queryByPage(@RequestParam Integer pageSize,
                                @RequestParam Integer pageNum,
                                @RequestParam(defaultValue = "") String name){
        QueryWrapper<com.rxee.backend.entity.File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("delflag", 1);
        if (!"".equals(name)) {fileQueryWrapper.like("name", name);}
        fileQueryWrapper.orderByDesc("id");
        IPage<com.rxee.backend.entity.File> page = new Page<>(pageNum, pageSize);
        IPage<com.rxee.backend.entity.File> files = fileMapper.selectPage(page, fileQueryWrapper);
        return ResultVo.success(files);
    }

    @PostMapping("/del")
    public ResultVo delete(@RequestBody Integer id){
        com.rxee.backend.entity.File file = fileMapper.selectById(id);
        file.setDelflag(false);
        return fileMapper.updateById(file) == 1 ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "删除失败！");
    }

    @PostMapping("/del/batch")
    public ResultVo batchDelete(@RequestBody List<Integer> ids){
        List<com.rxee.backend.entity.File> files = fileMapper.selectBatchIds(ids);
        for (com.rxee.backend.entity.File file: files) {
            file.setDelflag(false);
            fileMapper.updateById(file);
        }
        return ResultVo.success();
    }

    @PostMapping("/update")
    public ResultVo updateFile(@RequestBody com.rxee.backend.entity.File file){
        int i = fileMapper.updateById(file);
        return i == 1 ? ResultVo.success() : ResultVo.fail(Constants.CODE_500, "更新失败！");
    }
}
