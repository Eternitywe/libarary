package com.qiu.library.common;

import com.qiu.library.common.enums.StatusEnum;
import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setCode(StatusEnum.SUCCESS_CODE.getCode());
        return result;
    }
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(StatusEnum.SUCCESS_CODE.getCode());
        result.setMsg(StatusEnum.SUCCESS_CODE.getDesc());
        result.setData(data);
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setCode(StatusEnum.ERROR_CODE.getCode());
        result.setMsg(StatusEnum.ERROR_CODE.getDesc());
        return result;
    }
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(StatusEnum.ERROR_CODE.getCode());
        result.setMsg(msg);
        return result;
    }

    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
