package com.example.springsecurity.controller;

import com.example.springsecurity.entity.MyOrder;
import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.MyOrderService;
import com.example.springsecurity.service.SysUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    SysUserService userService;

    @Resource
    MyOrderService myOrderService;

    @RequestMapping(value = "/myUserOrder", method = RequestMethod.POST)
    public List<MyOrder> myUserOrder(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        SysUser user = userService.findByUsername(username);
        Long userId = user.getId();

        return myOrderService.allUserOrder(userId);
    }
}
