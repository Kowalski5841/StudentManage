package com.homestay.bean;

import lombok.Data;

@Data
public class HomestayVO extends Homestay{
    private Integer price;//最低价格
    private String destination;//目的地
    private String checkInTime;//入住时间
    private String keyword;//关键字
    private Integer num;//房间数


}
