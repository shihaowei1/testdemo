package com.example.springsecurity.security;

import com.example.springsecurity.entity.MyUserDetails;
import com.example.springsecurity.entity.SysRole;
import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.SysRoleService;
import com.example.springsecurity.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//自定义UserDetailsService，获取用户信息
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user;
        user = sysUserService.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }else {
            List<SysRole> roles = sysRoleService.getRoleByUserId(user.getId());
            return new MyUserDetails(user, roles);
        }
    }
}