package com.example.springsecurity.service;

import com.example.springsecurity.entity.Goods;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GoodsService {

    List<Goods> allGoodsInfo();

    String addNewGoods(String cata, String goodsName, Long price, Long saled,
                       Long remain, String goodsInfo);

    String showInfo(Long goodsId);

    void delGoods(Long delGoodsId);

    String changeGoodsInfo(Long goodsId, String cata, String goodsName, Long price, Long saled,
                         Long remain, String goodsInfo);
}
