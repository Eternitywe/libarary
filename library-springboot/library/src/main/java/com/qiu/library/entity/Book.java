package com.qiu.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Book {

    private Integer id;

    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishDate;

    private String author;

    private String publisher;

    private Integer categoryid;

    private String bookNo;

    private String cover;

    private Integer score;

    private Integer nums;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    private Integer isdeleted = 0;
}
