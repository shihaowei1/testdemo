package com.example.springsecurity.service.impl;

import com.example.springsecurity.entity.SysRole;
import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.MainService;
import com.example.springsecurity.service.SysRoleService;
import com.example.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Set;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    SysUserService userService;

    @Autowired
    SysRoleService roleService;

    @Autowired
    HttpServletRequest request;


    @Override
    @Transactional
    public String register(String username, String password, String role){
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);

        //判断用户名是否存在
        if (userService.findByUsername(user.getUsername()) != null) {
            return "用户名已存在";
        }

        userService.save(user); //保存注册的用户
        SysRole role1 = new SysRole();
        role1.setRole(role);
        role1.setUserId(user.getId());
        roleService.save(role1);
        return "注册成功";
    }

    @Override
    @Transactional
    public String loginError(){
        AuthenticationException e = (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        String message = null;
        message = e.getMessage();
        return message;
    }

    @Override
    @Transactional
    public View loginSuccess(){
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            return new RedirectView("/admin.html");
        }
        if (roles.contains("ROLE_USER")) {
            return new RedirectView("/user.html");
        }
        return null;
    }

    @Override
    @Transactional
    public SysUser info(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        SysUser user = userService.findByUsername(username);

        System.out.println("session timeout: " + request.getSession().getMaxInactiveInterval());

        return user;
    }

}
