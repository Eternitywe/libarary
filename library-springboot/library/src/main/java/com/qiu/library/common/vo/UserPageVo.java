package com.qiu.library.common.vo;

import com.qiu.library.entity.User;
import lombok.Data;

@Data
public class UserPageVo extends User {

    private Integer pageNum = 1; //默认页码

    private Integer pageSize = 5; //默认每页显示条数
}
