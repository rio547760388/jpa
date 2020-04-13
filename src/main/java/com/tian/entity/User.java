package com.tian.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/3
 * 说明：
 */
@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column
    private Integer enabled;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @OneToMany()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<UserRole> userRoles;

    @Transient
    private List<Menu> menus;
}
