package com.homestay.bean;

import lombok.Data;

@Data //生成setter getter 方法
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;//昵称
    private Integer type;
}
