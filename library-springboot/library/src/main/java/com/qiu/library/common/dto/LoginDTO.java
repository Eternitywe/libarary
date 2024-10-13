package com.qiu.library.common.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;

    private String username;

    private String phone;

    private String email;

    private String token;
}
