package com.qiu.library.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{

    private Integer code;

    public ServiceException(String message,Throwable cause) {
        super(message,cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
