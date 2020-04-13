package com.tian.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/6
 * 说明：
 */
@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "com.tian.repo.mysql")
public class JpaConfig {
}
