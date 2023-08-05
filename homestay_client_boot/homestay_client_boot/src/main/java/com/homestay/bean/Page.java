package com.homestay.bean;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer pageNum;//当前页
    private Integer pageSize;//每页记录数
    private long total;//总记录数
    private List<T> rows;//列表数据
}
