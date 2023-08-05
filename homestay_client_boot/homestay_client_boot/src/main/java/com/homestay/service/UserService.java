package com.homestay.service;

import com.homestay.bean.Dist;
import com.homestay.bean.Homestay;
import com.homestay.bean.Page;
import com.homestay.bean.User;

import java.util.List;

public interface UserService {
    User login(User user);

    User queryByName(String username);

    void changepassword(String username, String password);

    int add(User user);

    List<Dist> queryDistByName(Dist dist);

    int addTeacher(User user);

    List<User> queryAll(User user);

    Page<User> queryTeacher(Page page, User user);
}
