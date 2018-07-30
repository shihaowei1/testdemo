package com.example.springsecurity.service;

import com.example.springsecurity.entity.SysRole;

import java.util.List;

public interface SysRoleService {

    List<SysRole> getRoleByUserId(Long userId);

    SysRole save(SysRole role);
}