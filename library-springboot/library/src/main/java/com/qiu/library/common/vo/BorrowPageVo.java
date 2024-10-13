package com.qiu.library.common.vo;

import com.qiu.library.entity.Borrow;
import lombok.Data;

@Data
public class BorrowPageVo extends Borrow {

    private String note;

    private Integer pageNum = 1; //默认页码

    private Integer pageSize = 5; //默认每页显示条数
}
