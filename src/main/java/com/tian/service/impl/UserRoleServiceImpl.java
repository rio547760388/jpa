package com.tian.service.impl;

import com.tian.entity.Role;
import com.tian.entity.UserRole;
import com.tian.repo.mysql.UserRoleRepository;
import com.tian.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/11
 * 说明：
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Cacheable(cacheManager = "userRole", key = "#userId")
    @Override
    public List<Role> getRolesByUserId(Long userId) {
        /*UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        List<UserRole> userRoles = userRoleRepository.findAll(Example.of(userRole),
                Sort.by(Sort.Order.desc("id")));
        if (userRoles.size() != 0)
            return roleRepository.findAllById(userRoles.stream()
                    .map(UserRole::getRoleId)
                    .collect(Collectors.toList()));
        else*/
            return Collections.EMPTY_LIST;
    }
}
