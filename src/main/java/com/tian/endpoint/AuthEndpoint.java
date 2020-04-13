package com.tian.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/8
 * 说明：
 */
@RestController
@Slf4j
public class AuthEndpoint {

    @PostMapping("login")
    public ResponseEntity login(@RequestBody Login login) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword().toCharArray());
        SecurityContextHolder.getContext().setAuthentication(token);
        return ResponseEntity.ok("登录成功");
    }

    @GetMapping("/index")
    public ResponseEntity succUrl() {
        return ResponseEntity.ok("首页");
    }

    @RequestMapping("/error")
    public Object error(HttpServletRequest request) {
        return request.getParameterMap();
    }

    @Data
    static class Login {
        private String username;

        private String password;
    }
}
