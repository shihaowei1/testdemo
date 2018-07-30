package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.RoleRepository;
import com.example.springsecurity.entity.SysRole;
import com.example.springsecurity.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public List<SysRole> getRoleByUserId(Long userId) {
        return roleRepository.getRoleByUserId(userId);
    }

    @Override
    @Transactional
    public SysRole save(SysRole role) {
        return roleRepository.save(role);
    }
}
