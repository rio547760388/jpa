package com.tian.service;

import com.tian.entity.Role;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/11
 * 说明：
 */
public interface UserRoleService {
    @Cacheable(cacheManager = "userRole", key = "#userId")
    List<Role> getRolesByUserId(Long userId);
}
