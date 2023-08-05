package com.homestay.bean;


import lombok.Data;

@Data
public class Dist {
    private Integer id;
    private Integer pid;//父级id
    private Integer level;//级别 省0 市1 区2
    private String name;

}
