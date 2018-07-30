package com.example.springsecurity.service;

import com.example.springsecurity.entity.SysUser;

import java.util.List;

public interface SysUserService {

    SysUser findByUsername(String username);

    SysUser save(SysUser user);

    List<SysUser> getAllUserInfo();

    void changeUserInfo(String userName, String userRank);
}
