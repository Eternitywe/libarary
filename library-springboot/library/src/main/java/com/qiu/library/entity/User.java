package com.qiu.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String name;

    private String username;

    private  Integer age;

    private String sex;

    private String phone;

    private String address;

    private Integer account;

    private boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    private Integer isdeleted = 0; //伪删除 0未删除 1删除

}
