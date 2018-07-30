package com.example.springsecurity.controller;

import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CustomerController {

    @Resource
    SysUserService userService;


    @RequestMapping(value = "/getAllUserInfo", method = RequestMethod.POST)
    public List<SysUser> getAllUserInfo(){
        return userService.getAllUserInfo();
    }

    @RequestMapping(value = "/changeUserInfo", method = RequestMethod.POST)
    public String changeUserInfo(@RequestParam("userName") String userName,
                               @RequestParam("userRank") String userRank){
        if (userRank.length() > 0) {
            userService.changeUserInfo(userName, userRank);
            return "true";
        }
        return "false";
    }
}
