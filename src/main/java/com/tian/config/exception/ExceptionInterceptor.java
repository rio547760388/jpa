package com.tian.config.exception;

import com.tian.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/11
 * 说明：
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class ExceptionInterceptor {

    @ExceptionHandler(Exception.class)
    public Result parameterException(Throwable e) {
        return Result.error(e.getMessage());
    }
}
