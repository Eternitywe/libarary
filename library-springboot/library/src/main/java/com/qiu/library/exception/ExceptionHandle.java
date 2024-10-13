package com.qiu.library.exception;

import cn.hutool.core.util.StrUtil;
import com.qiu.library.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//全局异常处理器
@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统错误",e);
        return Result.error("系统错误"+e.getMessage());
    }

    //自定义业务异常处理
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e) {
        log.error("业务异常",e);
        Integer code = e.getCode();
        if (StrUtil.isNotBlank(code.toString())){
            return Result.error(code,e.getMessage());
        }
        return Result.error(e.getMessage());
    }
}
