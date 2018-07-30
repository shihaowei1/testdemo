package com.example.springsecurity.dao;

import com.example.springsecurity.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<SysUser, Long>{
    SysUser findByUsername(String username);

    @Query("select user from SysUser user")
    List<SysUser> findAll();

}
