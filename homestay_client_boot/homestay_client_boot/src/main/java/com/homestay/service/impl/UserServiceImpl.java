package com.homestay.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.bean.Dist;
import com.homestay.bean.Homestay;
import com.homestay.bean.Page;
import com.homestay.bean.User;
import com.homestay.mapper.UserMapper;
import com.homestay.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Override
    public User login(User user) {
        return mapper.login(user);
    }

    @Override
    public User queryByName(String username) {
        return mapper.queryByName(username);
    }

    @Override
    public void changepassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        log.info(String.valueOf(user));
        mapper.update(user);
    }

    @Override
    public int add(User user) {
        return mapper.add(user);
    }

    @Override
    public List<Dist> queryDistByName(Dist dist) {
        return mapper.queryDistByName(dist);
    }

    @Override
    public int addTeacher(User user) {
        return mapper.addTeacher(user);
    }

    @Override
    public List<User> queryAll(User user) {
        return mapper.queryAll(user);
    }

    @Override
    public Page<User> queryTeacher(Page page, User user) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<User> list = mapper.queryTeacher(user);
        PageInfo<User> info = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(info.getTotal());
        return page;
    }


}