package com.tian.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/4
 * 说明：
 */
@Entity
@Table(name = "user_role")
@Data
@EqualsAndHashCode
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @OneToMany()
    @JoinColumn(name = "id", referencedColumnName = "role_id")
    private List<Role> roles;
}
