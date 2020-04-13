package com.tian.config;

import com.tian.entity.mongo.UserMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/6
 * 说明：
 */
//@Configuration(proxyBeanMethods = false)
//@EnableMongoRepositories(basePackages = "com.tian.repo.mongo")
public class MongoConfig {
    /*@Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoMappingContext mongoMappingContext;

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {

        IndexOperations indexOps = mongoTemplate.indexOps(UserMongo.class);

        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
        resolver.resolveIndexFor(UserMongo.class).forEach(indexOps::ensureIndex);
    }*/
}
