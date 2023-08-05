package com.homestay.service;

import com.homestay.bean.Homestay;
import com.homestay.bean.HomestayVO;
import com.homestay.bean.Page;

public interface HomestayService {
    int add(Homestay homestay);

    Page<Homestay> select(Page page, Homestay homestay);

    Homestay my(String snum);

    int del(Integer id);

    int update(Homestay homestay);

    Page<HomestayVO> selectHomestay(Page page, HomestayVO homestayVO);

    int addReview(Homestay homestay);
}
