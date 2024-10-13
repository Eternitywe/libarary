package com.qiu.library.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BookDTO {
    private Integer id;

    private String name;

    private String description;

    private Date publishDate;

    private String author;

    private String publisher;

    private String categoryName;

    private Integer categoryid;

    private String bookNo;

    private String cover;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    private Integer isdeleted = 0;
}
