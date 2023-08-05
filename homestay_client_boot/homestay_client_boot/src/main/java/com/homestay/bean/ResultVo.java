package com.homestay.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//生成全参的构造方法
@NoArgsConstructor//生成无参的构造方法
public class ResultVo {
    private Integer code;//状态码:给前台判断是否添加成功
    private Object data;//响应数据
    private String msg;//相应信息

    public static  ResultVo success(String msg,Object data){
        return new ResultVo(200,data,msg);
    }
    public static ResultVo error(String msg){
        return new ResultVo(503,null,msg);
    }
}
