package com.example.springsecurity.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "finishedorder")
public class FinishedOrderInfo {

    @Id
    private String out_trade_no;
    private String trade_no;
    private String total_amount;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}
