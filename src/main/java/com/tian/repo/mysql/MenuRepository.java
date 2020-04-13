package com.tian.repo.mysql;

import com.tian.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/3
 * 说明：
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

}