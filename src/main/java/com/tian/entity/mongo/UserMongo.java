package com.tian.entity.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/6
 * 说明：
 */
@Data
@EqualsAndHashCode
@Document
@CompoundIndexes({@CompoundIndex(name = "username_age_index", def = "{'username':1,'age':-1}")})
public class UserMongo {

    @Id
    private String id;

    @Indexed(unique = false)
    private String username;

    @Field("password")
    private String password;

    private Integer age;

    private Integer enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime lastLogin;
}
