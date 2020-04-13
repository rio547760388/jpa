package com.tian.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/13
 * 说明：
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyFormat {
    String currency() default "";
}
