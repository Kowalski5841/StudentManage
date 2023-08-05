package com.homestay.controller;

import com.homestay.bean.*;
import com.homestay.service.UserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController //@Controller 实例化对象 @ResponseBody 设置返回数据为JSON格式
@RequestMapping("/user")

/**
 *
 * 协议     ip或域名         端口号
 * http    192.168.20.1    8080
 * https   www.baidu.com   80
 * 跨域问题
 * http://localhost:8080
 * https://localhost:8080
 *
 * */

@CrossOrigin //解决跨域
public class UserController {
    @Resource//自动注入
    private UserService service;


    @GetMapping("/login")
    public ResultVo login(User user){
        User vo = service.login(user);
        if(vo==null) return ResultVo.error("账号不存在！");
        String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(vo.getPassword().equals(pwd))
            return ResultVo.success("登录成功！",vo);

        return ResultVo.error("密码错误！");
    }

    @GetMapping("/queryByName")
    public ResultVo queryByName(String username){
        User user = service.queryByName(username);
        if(user!=null) return ResultVo.error("用户名已存在！");

        return ResultVo.success("该用户名可用！",null);
    }

    @PostMapping("/changepassword")
    public ResultVo changepassword(@RequestBody User user){
        service.changepassword(user.getUsername(), user.getPassword());
        return ResultVo.success("该用户名可用！",null);
    }

    @PostMapping("/add")
    //接受请求参数为JSON格式
    public ResultVo add(@RequestBody User user){
        int i = service.add(user);
        if(i>0) return ResultVo.success("注册成功！",null);

        return ResultVo.error("注册失败，请稍后重试！");
    }

    @GetMapping("/queryDistByName")
    public ResultVo queryDistByName(Dist dist){
        List<Dist> list = service.queryDistByName(dist);
        return ResultVo.success("查询成功",list);
    }
    @PostMapping("/addTeacher")
    public ResultVo addTeacher(@RequestBody User user){
        int i = service.addTeacher(user);
        if(i>0) return ResultVo.success("添加成功！",null);

        return ResultVo.error("添加失败！");
    }

    @GetMapping("/queryAll")
    public ResultVo queryAll( User user){
        List<User> list = service.queryAll(user);
        return ResultVo.success("查询成功！",list);
    }
    @GetMapping("/queryTeacher")
    public ResultVo queryTeacher(Page page, User user){
        Page<User> list = service.queryTeacher(page, user);
        return ResultVo.success("查询成功",list);
    }


}
