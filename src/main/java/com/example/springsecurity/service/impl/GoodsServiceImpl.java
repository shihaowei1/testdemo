package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.GoodsRepository;
import com.example.springsecurity.entity.Goods;
import com.example.springsecurity.entity.MyOrder;
import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.GoodsService;
import com.example.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    SysUserService userService;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    HttpServletRequest request;

    @Override
    @Transactional
    public List<Goods> allGoodsInfo() {
        return goodsRepository.findAll();
    }

    @Override
    @Transactional
    public String addNewGoods(String cata, String goodsName, Long price,
                              Long saled, Long remain, String goodsInfo) {
        Goods goods = new Goods();
        goods.setCatagory(cata);
        goods.setGoods_name(goodsName);
        goods.setPrice(price);
        goods.setSaled(saled);
        goods.setRemain(remain);
        goods.setGoodsInfo(goodsInfo);

        goodsRepository.save(goods);
        return "true";
    }

    @Override
    @Transactional
    public String showInfo(Long goodsId) {
        System.out.println("setNowGoodsId: " + goodsId);
        HttpSession session = request.getSession();
        session.setAttribute("nowGoodsId", goodsId);

        Goods goods = goodsRepository.findByGoods_id(goodsId);
        return goods.getGoodsInfo();
    }


    @Override
    @Transactional
    public void delGoods(Long delGoodsId) {
        goodsRepository.deleteById(delGoodsId);
    }

    @Override
    @Transactional
    public String changeGoodsInfo(Long goodsId, String cata, String goodsName, Long price, Long saled,
                                Long remain, String goodsInfo) {
        Goods goods = goodsRepository.findByGoods_id(goodsId);
        if (cata.length() > 0) {
            goods.setCatagory(cata);
        }
        if (goodsName.length() > 0) {
            goods.setGoods_name(goodsName);
        }
        if (price != null) {
            goods.setPrice(price);
        }
        if (saled != null) {
            goods.setSaled(saled);
        }
        if (remain != null) {
            goods.setRemain(remain);
        }
        if (goodsInfo.length() > 0) {
            goods.setGoodsInfo(goodsInfo);
        }
        goodsRepository.save(goods);
        return "true";
    }

}
