package com.tian.repo.mysql;

import com.tian.entity.Menu;
import com.tian.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author ﻿jinzhu.tian@onerway.com
 * @date 2020/4/3
 * 说明：
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    @Query("select distinct m from Menu m, RoleMenu rm, Role r, UserRole ur, User u " +
            "where u.id = ur.userId " +
            "and ur.roleId = r.id " +
            "and r.id = rm.roleId " +
            "and rm.menuId = m.id " +
            "and u.id = ?#{[0].id}")
    List<Menu> findMenusByUserId(User user);
}