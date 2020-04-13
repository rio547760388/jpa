package com.tian.endpoint;

import com.tian.config.annotation.CurrencyFormat;
import com.tian.entity.Result;
import com.tian.entity.User;
//import com.tian.entity.mongo.UserMongo;
import com.tian.params.UserParam;
import com.tian.params.UserQuery;
import com.tian.service.UserService;
import com.tian.service.email.MailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/3
 * 说明：
 */
@RestController
public class UserEndpoint {

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @GetMapping("user")
    public Page<User> users(UserQuery query, Pageable pageable) {
        return userService.findByExample(query, pageable);
    }

    @PostMapping("addUser")
    public User addUser(UserParam param) {
        return userService.addUser(param);
    }

    @GetMapping("findUser")
    public User findUser(String username) {
        return userService.getUserByUsername(username).get();
    }

    @GetMapping("money")
    public Result<Money> getMoney(String currency) {
        Money money = new Money();
        money.setCurrency(currency);
        money.setMoney(100.0);
        return Result.succ(money);
    }

    @Data
    public class Money {

        private String currency;

        @CurrencyFormat(currency = "USD")
        private Double money;
    }

    /*@PostMapping("saveMongo")
    public UserMongo saveMongo(@RequestBody UserParam param) {
        return userService.saveIntoMongo(param);
    }

    @GetMapping("userInMongo")
    public UserMongo find(String username) {
        mailService.receiveEmail();
        return userService.findByUsername(username);
    }*/

}
