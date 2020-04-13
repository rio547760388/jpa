package com.tian.service.impl;

import com.tian.entity.User;
import com.tian.params.UserParam;
import com.tian.params.UserQuery;
import com.tian.repo.mysql.RoleRepository;
import com.tian.repo.mysql.UserRepository;
import com.tian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Optional;

//import com.tian.entity.mongo.UserMongo;
//import com.tian.repo.mongo.UserMongoRepository;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/3
 * 说明：
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    //@Autowired
    //UserMongoRepository userMongoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        Specification specification = new Specification<User>() {
            @Override
            public Specification<User> and(Specification<User> other) {
                return null;
            }

            @Override
            public Specification<User> or(Specification<User> other) {
                return null;
            }

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        userRepository.findOne(specification);
        return userRepository.findAll(pageable);
    }

    @Override
    public User addUser(UserParam userParam) {
        User user = new User();
        user.setUsername(userParam.getUsername());
        user.setPassword(userParam.getPassword());
        user.setEnabled(1);
        user.setCreateTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public Page<User> findByExample(UserQuery userQuery, Pageable pageable) {
        User user = new User();

        user.setId(userQuery.getUserId());
        user.setUsername(userQuery.getUsername());

        return userRepository.findAll(Example.of(user), pageable);
    }

    /*@Override
    public UserMongo saveIntoMongo(UserParam userParam) {
        UserMongo user = new UserMongo();
        user.setUsername(userParam.getUsername());
        user.setPassword(userParam.getPassword());
        user.setEnabled(1);
        user.setCreateTime(LocalDateTime.now());

        return userMongoRepository.save(user);
    }

    @Override
    public UserMongo findByUsername(String username) {
        return userMongoRepository.findByUsername(username);
    }*/

    @Cacheable(value = "manager", key = "#username")
    @Override
    public Optional<User> getUserByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            user.get().setMenus(userRepository.findMenusByUserId(user.get()));
        }
        return user;
    }


}
