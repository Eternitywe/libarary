package com.qiu.library.common.vo;

import com.qiu.library.entity.Admin;
import lombok.Data;

@Data
public class AdminPageVo extends Admin {

    private Integer pageNum = 1; //默认页码

    private Integer pageSize = 5; //默认每页显示条数
}
