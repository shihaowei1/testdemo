package com.example.springsecurity.entity;

import javax.persistence.*;

@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goods_id;

    private String catagory;
    private String goods_name;
    private Long price;
    private Long saled;
    private Long remain;
    private String goods_info;

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setSaled(Long saled) {
        this.saled = saled;
    }

    public Long getSaled() {
        return saled;
    }

    public void setRemain(Long remain) {
        this.remain = remain;
    }

    public Long getRemain() {
        return remain;
    }

    public void setGoodsInfo(String goods_info) {
        this.goods_info = goods_info;
    }

    public String getGoodsInfo() {
        return goods_info;
    }
}
