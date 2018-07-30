package com.example.springsecurity.service;

import com.example.springsecurity.entity.MyOrder;
import com.example.springsecurity.entity.Saled;

import java.util.List;

public interface MyOrderService {

    List<MyOrder> allUserOrder(Long userId);

    List<Saled> orderInfo(Long orderId);
}
