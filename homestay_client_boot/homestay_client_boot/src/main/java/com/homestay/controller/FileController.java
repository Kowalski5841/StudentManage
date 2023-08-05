package com.homestay.controller;

import com.homestay.bean.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.static-access-path}")
    private  String staticAccessPath;

    @PostMapping("/upload")
    public ResultVo upload(MultipartFile file){
        //1.获取源文件名
        String fileName = file.getOriginalFilename();
        //2.截取后缀名  1.png
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //3.封装新文件名
        String newName = UUID.randomUUID().toString().replaceAll("-","") + suffix;
        try {
            file.transferTo(new File(uploadPath,newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVo.success("上传成功！",staticAccessPath +newName);
    }

}
