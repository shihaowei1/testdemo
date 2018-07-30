package com.example.springsecurity.dao;

import com.example.springsecurity.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select goods from Goods goods")
    List<Goods> findAll();

    @Query("select goods from Goods goods where goods.goods_id=?1")
    Goods findByGoods_id(Long goods_id);
}
