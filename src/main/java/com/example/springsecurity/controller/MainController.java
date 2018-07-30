package com.example.springsecurity.controller;

import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.MainService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;

@RestController
public class MainController {

    @Resource
    MainService mainService;

    @PostMapping(value = "/register")
    public String save(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("role") String role) {

        return mainService.register(username, password, role);
    }

    @RequestMapping("/login/error")
    public String loginError() {
        return mainService.loginError();
    }

    @RequestMapping(value = "/login-success", method = RequestMethod.POST)
    public View jump() {
        return mainService.loginSuccess();
    }

    //获取当前登录用户的信息
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public SysUser info() {
        return mainService.info();
    }

}
