package com.qiu.library.common.vo;

import com.qiu.library.entity.Borrow;
import com.qiu.library.entity.Retur;
import lombok.Data;

@Data
public class ReturPageVo extends Retur {

    private String note;

    private Integer pageNum = 1; //默认页码

    private Integer pageSize = 5; //默认每页显示条数
}
