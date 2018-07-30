package com.example.springsecurity.dao;

import com.example.springsecurity.entity.FinishedOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinishedOrderInfoRepository extends JpaRepository<FinishedOrderInfo, String> {

    @Query("select info from FinishedOrderInfo info where info.out_trade_no=?1")
    FinishedOrderInfo findByOut_trade_no(String out_trade_no);
}
