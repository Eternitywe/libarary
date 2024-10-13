package com.qiu.library.common.vo;

import com.qiu.library.entity.Category;
import lombok.Data;

@Data
public class CategoryPageVo extends Category {

    private String pname;//父级分类名称

    private Integer pageNum = 1; //默认页码

    private Integer pageSize = 5; //默认每页显示条数
}
