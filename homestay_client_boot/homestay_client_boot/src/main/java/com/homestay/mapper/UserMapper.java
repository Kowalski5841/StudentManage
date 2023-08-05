package com.homestay.mapper;

import com.homestay.bean.Dist;
import com.homestay.bean.User;

import java.util.List;

public interface UserMapper {
    User login(User user);

    User queryByName(String username);

    int add(User user);

    int delByStudentNumber(String snum);

    List<Dist> queryDistByName(Dist dist);

    int addTeacher(User user);

    void update(User user);

    List<User> queryAll(User user);

    List<User> queryTeacher(User user);
}
