package com.qiu.library.common.dto;

import com.qiu.library.entity.User;
import lombok.Data;

@Data
public class UserDTO extends User {

    private Integer score;
}
