package com.tian.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/13
 * 说明：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;

    private String msg;

    private LocalDateTime time;

    private T data;

    public static Result succ() {
        return get(200, "success");
    }

    public static Result succ(Object data) {
        return get(200, "success", data);
    }

    public static Result error(int code, String msg) {
        return get(500, msg, null);
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result get(int code, String msg) {
        return get(code, msg, null);
    }

    public static Result get(int code, String msg, Object data) {
        return new Result(code, msg, LocalDateTime.now(), data);
    }

}
