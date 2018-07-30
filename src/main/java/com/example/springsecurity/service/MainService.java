package com.example.springsecurity.service;

import com.example.springsecurity.entity.SysUser;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MainService {
    String register(String username, String password, String role);

    String loginError();

    View loginSuccess();

    SysUser info();

}
