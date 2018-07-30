package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.MyOrderRepository;
import com.example.springsecurity.entity.MyOrder;
import com.example.springsecurity.entity.Saled;
import com.example.springsecurity.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    MyOrderRepository myOrderRepository;

    @Override
    @Transactional
    public List<MyOrder> allUserOrder(Long userId){
        return myOrderRepository.findByUser_id(userId);
    }

    @Override
    @Transactional
    public List<Saled> orderInfo(Long orderId){

        return myOrderRepository.findById(orderId).get().getSaledList();
    }
}
