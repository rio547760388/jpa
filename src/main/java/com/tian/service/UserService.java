package com.tian.service;

import com.tian.entity.Role;
import com.tian.entity.User;
//import com.tian.entity.mongo.UserMongo;
import com.tian.params.UserParam;
import com.tian.params.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/3
 * 说明：
 */
public interface UserService {

    Page<User> findAll(Pageable pageable);

    User addUser(UserParam userParam);

    Page<User> findByExample(UserQuery userQuery, Pageable pageable);

    /*UserMongo saveIntoMongo(UserParam userParam);

    UserMongo findByUsername(String username);*/

    Optional<User> getUserByUsername(String username);

}
