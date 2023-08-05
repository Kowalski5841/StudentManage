package com.homestay.controller;

import com.homestay.bean.*;
import com.homestay.service.HomestayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/homestay")
@CrossOrigin

public class HomestayController {

    @Resource
    private HomestayService service;

    @PostMapping
    public ResultVo add(@RequestBody Homestay homestay){
        int i = service.add(homestay);
        if(i>0) return ResultVo.success("添加成功！",null);
        return ResultVo.error("添加失败！");

    }
    @GetMapping
    public ResultVo select(Page page,Homestay homestay){
        Page<Homestay> p = service.select(page,homestay);
        return ResultVo.success("查询成功",p);

    }

    @GetMapping("/my")
    public ResultVo select(@RequestParam String username) {
        Homestay homestay = service.my(username);
        return ResultVo.success("查询成功", homestay);

    }

    @DeleteMapping
    public ResultVo del(Integer id){
        if(id==null||id==0) return ResultVo.error("id不能为空");
        int i = service.del(id);
        if(i>0) return ResultVo.success("删除成功！",null);
        return ResultVo.error("删除失败，请稍后重试！");
    }

    @PutMapping
    public ResultVo update(@RequestBody Homestay homestay){
        int i = service.update(homestay);
        if(i>0) return ResultVo.success("编辑成功！",null);
        return ResultVo.error("编辑失败");
    }

    @GetMapping("/selectHomestay")
    public ResultVo selectHomestay(Page page,HomestayVO homestayVO){
        Page<HomestayVO> p=service.selectHomestay(page,homestayVO);
        return ResultVo.success("查询成功！",p);


    }

    @PutMapping("/addReview")
    public ResultVo addReview(@RequestBody Homestay homestay){
        int i = service.addReview(homestay);
        if(i>0) return ResultVo.success("编辑成功！",null);
        return ResultVo.error("编辑失败！");
    }

}

