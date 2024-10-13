package com.qiu.library.common.request;

import lombok.Data;

@Data
public class changePasswordRequest {
    private Integer id;

    private String username;

    private String password;

    private String newPassword;
}
