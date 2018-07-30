package com.example.springsecurity.entity;


import javax.persistence.*;

@Entity
@Table(name = "saled")
public class Saled {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
//
//    @Column(name = "order_id")
//    private Long order_id;

    @Column(name = "goods_id")
    private Long goods_id;

    @Column(name = "goods_name")
    private String goods_name;

    @Column(name = "price")
    private Long price;

    @Column(name = "amount")
    private Long amount;

//    @Column(name = "user_id")
//    private Long user_id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "order_id")
    private MyOrder SetOrder;

    public MyOrder getSetOrder() {
        return SetOrder;
    }

    public void setSetOrder(MyOrder setOrder) {
        SetOrder = setOrder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public void setOrder_id(Long order_id) {
//        this.order_id = order_id;
//    }
//
//    public Long getOrder_id() {
//        return order_id;
//    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getGoods_id() {
        return goods_id;
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

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

//    public void setUser_id(Long user_id) {
//        this.user_id = user_id;
//    }
//
//    public Long getUser_id() {
//        return user_id;
//    }
}
