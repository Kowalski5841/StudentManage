package com.homestay.mapper;

import com.homestay.bean.Homestay;
import com.homestay.bean.HomestayVO;

import java.util.List;

public interface HomestayMapper {
     int add(Homestay homestay) ;

     List<Homestay> select(Homestay homestay);

     Homestay queryById(Integer id);

     Homestay queryByStudentNumber(String snum);

     int del(Integer id);

     int update(Homestay homestay);

     List<HomestayVO> selectHomestay(HomestayVO homestayVO);

     int addReview(Homestay homestay);
}
