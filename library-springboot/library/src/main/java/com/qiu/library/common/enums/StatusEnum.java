package com.qiu.library.common.enums;


import lombok.Getter;

@Getter
public enum StatusEnum {
    SUCCESS_CODE("成功", 200),
    ERROR_CODE("失败", 400),
    SERVICE_ERROR_CODE("服务器错误", 500),
    NOT_FOUND("找不到", 404),
    DISPLAY_USER_CODE("当前用户处于禁用状态,请联系管理员!", 10001),
    MORE_USERNAME_CODE("用户名已存在", 10002),
    NOTFOUND_USER_CODE("用户不存在", 10003),
    NOTFOUND_BOOK_CODE("图书不存在", 10004),
    NOT_USER_ACCOUNT_CODE("用户积分不足", 10005),
    NOT_BOOK_NUM_CODE("图书数量不足", 10006);

    public static final String RETURNED = "已归还";

    StatusEnum(String desc, Integer code) {
        this.code = code;
        this.desc = desc;
    }

    public static StatusEnum getByCode(int codeVal){
        for (StatusEnum resultCodeEnum: StatusEnum.values()){
            if (resultCodeEnum.code==codeVal){
                return resultCodeEnum;
            }
        }
        return null;
    }

    private Integer code;

    private String desc;

}
