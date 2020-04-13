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
@Table(name = "role_menu")
@Data
@EqualsAndHashCode
public class RoleMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @OneToMany()
    @JoinColumn(name = "id", referencedColumnName = "menu_id")
    private List<Menu> menus;
}
