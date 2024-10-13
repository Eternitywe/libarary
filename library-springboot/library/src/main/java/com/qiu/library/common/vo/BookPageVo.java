package com.qiu.library.common.vo;

import com.qiu.library.entity.Book;
import lombok.Data;

@Data
public class BookPageVo extends Book {

    private String categoryName;

    private Integer pageNum = 1; //默认页码

    private Integer pageSize = 2; //默认每页显示条数
}
