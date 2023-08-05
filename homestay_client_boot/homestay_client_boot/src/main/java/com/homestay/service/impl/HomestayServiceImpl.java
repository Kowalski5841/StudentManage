package com.homestay.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.bean.Homestay;
import com.homestay.bean.HomestayVO;
import com.homestay.bean.Page;
import com.homestay.bean.User;
import com.homestay.mapper.UserMapper;
import com.homestay.service.HomestayService;
import com.homestay.mapper.HomestayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class HomestayServiceImpl implements HomestayService {
    @Resource
    private HomestayMapper mapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public int add(Homestay homestay) {
        User user = new User();
        user.setUsername(homestay.getStudentNumber());
        user.setPassword(homestay.getPassword());
        user.setNickname(homestay.getName());
        user.setType(1);
        userMapper.add(user);
        log.info(String.valueOf(homestay));
        return mapper.add(homestay);
    }

    @Override
    public int update(Homestay homestay) {
        return mapper.update(homestay);
    }


    @Override
    public Page<Homestay> select(Page page, Homestay homestay) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Homestay> list = mapper.select(homestay);
        PageInfo<Homestay> info = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(info.getTotal());
        return page;
    }

    @Override
    public Homestay my(String snum) {
        return mapper.queryByStudentNumber(snum);
    }

    @Override
    public int del(Integer homestayId) {
        Homestay homestay = mapper.queryById(homestayId);
        if (homestay == null) {
            return 0;
        }
        String snum = homestay.getStudentNumber();
        userMapper.delByStudentNumber(snum);
        return mapper.del(homestayId);
    }


    @Override
    public Page<HomestayVO> selectHomestay(Page page, HomestayVO homestayVO) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<HomestayVO> list = mapper.selectHomestay(homestayVO);
        PageInfo<HomestayVO> info = new PageInfo<>(list);
        page.setRows(list);
        page.setTotal(info.getTotal());
        return page;
    }

    @Override
    public int addReview(Homestay homestay) {
        return mapper.addReview(homestay);
    }
}

