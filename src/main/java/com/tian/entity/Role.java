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
@Table(name = "role")
@Data
@EqualsAndHashCode
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column
    private String description;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @OneToMany()
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private List<RoleMenu> roleMenus;
}
