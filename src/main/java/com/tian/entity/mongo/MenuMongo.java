package com.tian.entity.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/4
 * 说明：
 */
@Data
@Document
@EqualsAndHashCode
public class MenuMongo {

    @Id
    private String id;

    private String menuName;

    private String url;

    private Integer sort;

    private Long superId;

    private LocalDateTime createTime;
}
