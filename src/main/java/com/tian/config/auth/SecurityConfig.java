package com.tian.config.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tian.entity.Menu;
import com.tian.entity.Result;
import com.tian.entity.User;
import com.tian.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/6
 * 说明：
 */
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService(userService)).passwordEncoder(new BCryptPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .accessDecisionManager(myAccessDecisionManager())
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(errorHandler())
                .authenticationEntryPoint(authEntryPoint())
        ;

        http.httpBasic().disable()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler((req, res, auth) -> {
                    Optional<User> user = userService.getUserByUsername(auth.getName());
                    if (user.isPresent()) {
                        UserToken ut = new UserToken(
                                user.get().getUsername(),
                                "",
                                user.get()
                                        .getMenus()
                                        .stream()
                                        .map(e -> new SimpleGrantedAuthority(e.getUrl()))
                                        .collect(Collectors.toList()));
                        ut.setUser(user.get());
                        SecurityContextHolder.getContext().setAuthentication(ut);
                    } else {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                    writeJson(res, null);
                })
                .failureHandler(authFailedHandler())
                .permitAll();

        http.logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .permitAll();

    }

    @Bean
    @ConditionalOnBean(value = UserService.class)
    public UserDetailsService userDetailsService(UserService userService) {
        return s -> {
            Optional<User> user = userService.getUserByUsername(s);
            User u = user.orElseThrow(() -> new UsernameNotFoundException(s));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(u.getUsername())
                    .password(u.getPassword()).authorities(user.get()
                            .getMenus().stream()
                            .map(e -> new SimpleGrantedAuthority(e.getUrl()))
                            .collect(Collectors.toList())).build();

        };
    }

    @Bean
    public AccessDecisionManager myAccessDecisionManager() {
        return new UnanimousBased(Arrays.asList(
                myAccessDecisionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter()
        ));
    }

    @Bean
    public AccessDecisionVoter myAccessDecisionVoter() {
        return new AccessDecisionVoter() {
            @Override
            public boolean supports(ConfigAttribute configAttribute) {
                return true;
            }

            @Override
            public boolean supports(Class aClass) {
                return true;
            }

            @Override
            public int vote(Authentication authentication, Object o, Collection collection) {
                String requestUrl = ((FilterInvocation) o).getRequestUrl();
                if (requestUrl.equals("/login") || requestUrl.equals("/logout"))
                    return ACCESS_GRANTED;

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth instanceof UserToken) {
                    UserToken ut = (UserToken) auth;
                    List<Menu> menuList = ut.getUser()
                            .getMenus();

                    for (Menu menu : menuList) {
                        AntPathRequestMatcher matcher = new AntPathRequestMatcher(menu.getUrl());

                        if (matcher.matcher(((FilterInvocation) o).getRequest()).isMatch())
                            return ACCESS_GRANTED;
                    }
                    return ACCESS_DENIED;
                }

                return ACCESS_ABSTAIN;
            }
        };
    }

    public static class UserToken extends UsernamePasswordAuthenticationToken {

        @Setter
        @Getter
        private User user;

        public UserToken(String username, String password, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
        }
    }

    @Bean
    public AuthenticationFailureHandler authFailedHandler() {
        return new AuthFailelHanderImpl();
    }

    @Bean
    public AccessDeniedHandler errorHandler() {
        return new ErrorHandlerImpl();
    }

    @Bean
    public AuthEntryPoint authEntryPoint() {
        return new AuthEntryPoint();
    }

    public class AuthFailelHanderImpl implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException, ServletException {
            writeJson(res, ex);
        }
    }

    public class ErrorHandlerImpl implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException, ServletException {
            writeJson(res, ex);
        }
    }

    public class AuthEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException, ServletException {
            writeJson(res, ex);
        }
    }

    public void writeJson(HttpServletResponse res, RuntimeException ex) {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding("UTF-8");
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            mapper.registerModule(module);

            if (ex == null) {
                mapper.writeValue(res.getOutputStream(), Result.succ());
                return;
            }

            if (ex instanceof AccessDeniedException)
                res.setStatus(HttpStatus.FORBIDDEN.value());
            else if (ex instanceof AuthenticationException)
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
            else
                res.setStatus(HttpStatus.BAD_REQUEST.value());

            mapper.writeValue(res.getOutputStream(), Result.error(ex.getLocalizedMessage()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(
                new BCryptPasswordEncoder().encode("tian1990")
        );
    }
}
