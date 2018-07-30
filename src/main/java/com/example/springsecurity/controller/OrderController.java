package com.example.springsecurity.controller;

import com.example.springsecurity.entity.MyOrder;
import com.example.springsecurity.entity.Saled;
import com.example.springsecurity.service.MyOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrderController {

    @Resource
    MyOrderService myOrderService;


    @RequestMapping(value = "/allUserOrder", method = RequestMethod.POST)
    public List<MyOrder> allUserOrder(@RequestParam("userId")Long userId){
        return myOrderService.allUserOrder(userId);
    }

    @RequestMapping(value = "/orderInfo", method = RequestMethod.POST)
    public List<Saled> orderInfo(@RequestParam("orderId") Long orderId){
        return myOrderService.orderInfo(orderId);
    }
}
