package com.tian.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/4
 * 说明：
 */
@Entity
@Table(name = "menu")
@Data
@EqualsAndHashCode
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name")
    private String menuName;

    @Column
    private String url;

    @Column
    private Integer sort;

    @Column(name = "super_id", columnDefinition = "integer default 0")
    private Long superId;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
