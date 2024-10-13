package com.qiu.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Borrow {

    private Integer id;

    private String bookName;

    private String bookNo;

    private Integer score;

    private Integer userId;

    private String userNo;

    private String userName;

    private String userPhone;

    private String status;

    private Integer days;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate returnDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    private Integer isdeleted = 0;
}
