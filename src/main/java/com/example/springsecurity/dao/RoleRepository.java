package com.example.springsecurity.dao;

import com.example.springsecurity.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<SysRole, Long> {

   List<SysRole> getRoleByUserId(Long userId);
}
