package com.homestay.bean;


import lombok.Data;

@Data
public class Homestay {
    private Integer id;  //
    private String name;    //民宿名称
    private String area;    //周边核心地区
    private String address; //地址
    private Integer starLevel;  //星级
    private Double score;   //评分
    private String tag; //标签
    private String pic; //封面
    private String rooms;   //客房数
    private String introduce;   //介绍
    private Integer uid; //用户id
    private String studentNumber;
    private String password;
}
